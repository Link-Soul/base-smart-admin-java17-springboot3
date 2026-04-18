package net.lab1024.sa.base.module.support.export.domain;

import lombok.Data;

import java.util.List;

/**
 *@Description 导出模板配置
 *@author ZhouBinBin
 *@since 2026/4/10 08:54
 **/
@Data
public class ExportTemplate {

    private Long id;
    private String name;
    // 根实体全限定类名
    private String rootEntityClass;
    // 字段配置
    private List<ExportFieldConfig> fields;
    // 关联配置（可解析注解自动生成）
    private List<TableJoinConfig> joins;
    /*
    示例数据结构如下
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
}
     */
}

