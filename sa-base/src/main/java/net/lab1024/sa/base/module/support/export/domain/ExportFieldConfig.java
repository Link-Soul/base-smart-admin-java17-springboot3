package net.lab1024.sa.base.module.support.export.domain;

import lombok.Data;

/**
 *@Description 导出字段配置
 *@author ZhouBinBin
 *@since 2026/4/10 08:54
 **/
@Data
public class ExportFieldConfig {
    // 字段路径，如 "orderNo" 或 "products.productName"
    private String fieldName;
    // 导出列标题
    private String alias;
    // 排序
    private Integer order;
    // 是否循环字段（属于一对多列表）
    private Boolean isLoopField = false;
    // 循环分组标识
    private String loopGroup;
    // 最大循环次数（不设置则按实际数量）
    private Integer maxLoopCount;
}
