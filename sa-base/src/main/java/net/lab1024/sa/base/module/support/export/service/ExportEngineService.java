package net.lab1024.sa.base.module.support.export.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import lombok.RequiredArgsConstructor;
import net.lab1024.sa.base.module.support.export.domain.ExportFieldConfig;
import net.lab1024.sa.base.module.support.export.domain.ExportTemplate;
import net.lab1024.sa.base.module.support.export.domain.TableJoinConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;


/**
 * 通用模板导出核心引擎
 *
 * @author ZhouBinBin
 * @since 2026/4/10 10:20
 */
@Service
@RequiredArgsConstructor
public class ExportEngineService {

    private final ApplicationContext applicationContext;

    /**
     * 根据模板ID执行导出数据准备
     */
    public List<Map<String, Object>> prepareExportData(Long templateId, Map<String, Object> queryParams) {
        // 1. 从数据库获取模板（此处简化，假设已获取模板对象）
        ExportTemplate template = getTemplateById(templateId);

        // 2. 查询根表数据
        List<?> rootDataList = queryRootData(template, queryParams);

        // 3. 填充关联数据（一对多）
        fillAssociationData(rootDataList, template);

        // 4. 按模板字段配置拍平数据，处理循环
        List<Map<String, Object>> flatData = flattenData(rootDataList, template);

        return flatData;
    }

    /**
     * 查询根表数据
     */
    private List<?> queryRootData(ExportTemplate template, Map<String, Object> queryParams) {
        try {
            Class<?> rootClass = Class.forName(template.getRootEntityClass());
            BaseMapper<?> mapper = getMapperByEntityClass(rootClass);
            QueryWrapper<?> wrapper = new QueryWrapper<>();
            // 可扩展动态查询条件，此处简单示例
            return mapper.selectList((Wrapper) wrapper);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("根实体类不存在", e);
        }
    }

    /**
     * 根据实体类获取对应的 MyBatis-Plus Mapper
     */
    private BaseMapper<?> getMapperByEntityClass(Class<?> entityClass) {
        // 约定 Mapper 接口名 = 实体类名 + "Mapper"，且位于同一包下
        String mapperClassName = entityClass.getName() + "Mapper";
        try {
            Class<?> mapperClass = Class.forName(mapperClassName);
            return (BaseMapper<?>) applicationContext.getBean(mapperClass);
        } catch (Exception e) {
            throw new RuntimeException("无法获取 Mapper: " + mapperClassName, e);
        }
    }

    /**
     * 填充关联数据（仅处理一对多）
     */
    private void fillAssociationData(List<?> rootDataList, ExportTemplate template) {
        if (template.getJoins() == null || template.getJoins().isEmpty()) {
            return;
        }
        for (TableJoinConfig join : template.getJoins()) {
            // 获取关联实体类及Mapper
            Class<?> targetClass;
            try {
                targetClass = Class.forName(join.getTargetTable());
            } catch (ClassNotFoundException e) {
                continue;
            }
            BaseMapper<?> targetMapper = getMapperByEntityClass(targetClass);

            // 收集根表关联字段值
            Set<Object> sourceIds = rootDataList.stream().map(root -> getFieldValue(root, join.getSourceField())).filter(Objects::nonNull).collect(Collectors.toSet());

            if (sourceIds.isEmpty()) {
                continue;
            }

            // 批量查询关联数据
            QueryWrapper<?> wrapper = new QueryWrapper<>();
            wrapper.in(join.getTargetField(), sourceIds);
            List<?> targetDataList = targetMapper.selectList((Wrapper) wrapper);

            // 按关联字段分组
            Map<Object, List<Object>> grouped = targetDataList.stream().collect(Collectors.groupingBy(item -> getFieldValue(item, join.getTargetField())));

            // 将分组数据设置到根对象的属性中
            for (Object root : rootDataList) {
                Object sourceValue = getFieldValue(root, join.getSourceField());
                List<Object> children = grouped.getOrDefault(sourceValue, Collections.emptyList());
                setFieldValue(root, join.getPropertyName(), children);
            }
        }
    }

    /**
     * 拍平数据，处理循环字段
     */
    private List<Map<String, Object>> flattenData(List<?> rootDataList, ExportTemplate template) {
        List<Map<String, Object>> result = new ArrayList<>();

        // 分离循环字段和非循环字段
        List<ExportFieldConfig> normalFields = template.getFields().stream().filter(f -> !f.getIsLoopField()).collect(Collectors.toList());

        Map<String, List<ExportFieldConfig>> loopGroupMap = template.getFields().stream().filter(ExportFieldConfig::getIsLoopField).collect(Collectors.groupingBy(f -> f.getLoopGroup() == null ? "default" : f.getLoopGroup()));

        for (Object root : rootDataList) {
            // 先准备非循环字段的值（每条根记录都一样）
            Map<String, Object> baseRow = new LinkedHashMap<>();
            for (ExportFieldConfig field : normalFields) {
                Object value = getFieldValueByPath(root, field.getFieldName());
                baseRow.put(field.getAlias(), value);
            }

            // 处理循环字段：需要根据嵌套列表展开多行
            if (loopGroupMap.isEmpty()) {
                // 无循环字段，直接加一行
                result.add(baseRow);
            } else {
                // 取第一个循环组，假设只支持一个一对多组（复杂场景可扩展）
                String groupName = loopGroupMap.keySet().iterator().next();
                List<ExportFieldConfig> loopFields = loopGroupMap.get(groupName);

                // 获取嵌套列表对象，字段路径如 "products"
                String listPropertyPath = loopFields.get(0).getFieldName().split("\\.")[0];
                List<?> nestedList = (List<?>) getFieldValueByPath(root, listPropertyPath);
                if (nestedList == null || nestedList.isEmpty()) {
                    // 没有子项，也输出一行（仅基础字段）
                    result.add(new LinkedHashMap<>(baseRow));
                } else {
                    int maxCount = loopFields.stream().mapToInt(f -> f.getMaxLoopCount() != null ? f.getMaxLoopCount() : nestedList.size()).max().orElse(nestedList.size());
                    for (int i = 0; i < Math.min(maxCount, nestedList.size()); i++) {
                        Map<String, Object> row = new LinkedHashMap<>(baseRow);
                        Object child = nestedList.get(i);
                        for (ExportFieldConfig field : loopFields) {
                            String childFieldPath = field.getFieldName().substring(field.getFieldName().indexOf('.') + 1);
                            Object childValue = getFieldValueByPath(child, childFieldPath);
                            // 动态生成列标题，可加序号
                            String alias = field.getAlias() + (i + 1);
                            row.put(alias, childValue);
                        }
                        result.add(row);
                    }
                }
            }
        }
        return result;
    }

    // ---------- 反射工具方法 ----------
    private Object getFieldValue(Object obj, String fieldName) {
        Field field = ReflectionUtils.findField(obj.getClass(), fieldName);
        if (field != null) {
            ReflectionUtils.makeAccessible(field);
            return ReflectionUtils.getField(field, obj);
        }
        return null;
    }

    private void setFieldValue(Object obj, String fieldName, Object value) {
        Field field = ReflectionUtils.findField(obj.getClass(), fieldName);
        if (field != null) {
            ReflectionUtils.makeAccessible(field);
            ReflectionUtils.setField(field, obj, value);
        }
    }

    private Object getFieldValueByPath(Object obj, String path) {
        String[] parts = path.split("\\.");
        Object current = obj;
        for (String part : parts) {
            if (current == null) {
                return null;
            }
            current = getFieldValue(current, part);
        }
        return current;
    }

    // 模拟获取模板
    private ExportTemplate getTemplateById(Long id) {
        // 实际从数据库查询，此处返回模拟对象
        String templateJson = """
                {
                  "id": "",
                  "name": "订单及商品导出模板",
                  "rootEntityClass": "com.example.entity.OrderInfoEntity",
                  "fields": [
                    {
                      "fieldName": "orderNo",
                      "alias": "订单号",
                      "order": 1
                    },
                    {
                      "fieldName": "customerName",
                      "alias": "客户名称",
                      "order": 2
                    },
                    {
                      "fieldName": "products.productName",
                      "alias": "商品名称",
                      "order": 3,
                      "isLoopField": true,
                      "loopGroup": "productGroup",
                      "maxLoopCount": 3
                    },
                    {
                      "fieldName": "products.price",
                      "alias": "商品单价",
                      "order": 4,
                      "isLoopField": true,
                      "loopGroup": "productGroup"
                    },
                    {
                      "fieldName": "products.quantity",
                      "alias": "数量",
                      "order": 5,
                      "isLoopField": true,
                      "loopGroup": "productGroup"
                    }
                  ],
                  "joins": [
                    {
                      "sourceField": "id",
                      "targetTable": "order_product_info",
                      "targetField": "order_id",
                      "joinType": "LEFT",
                      "propertyName": "products"
                    }
                  ]
                }""";
        return JSONObject.toJavaObject(JSONObject.parseObject(templateJson), ExportTemplate.class);
    }
}