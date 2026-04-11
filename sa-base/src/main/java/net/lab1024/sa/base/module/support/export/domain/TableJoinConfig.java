package net.lab1024.sa.base.module.support.export.domain;

import lombok.Data;

/**
 *@Description 表关联配置
 *@author ZhouBinBin
 *@since 2026/4/10 08:55
 **/
@Data
public class TableJoinConfig {
    // 根表或上一级表的关联字段
    private String sourceField;
    // 关联表名（实体类名）
    private String targetTable;
    // 关联表字段
    private String targetField;
    // LEFT / INNER
    private String joinType;
    // 在根对象中的嵌套属性名
    private String propertyName;
}
