package net.lab1024.sa.admin.module.orderproductinfo.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 订单商品 列表VO
 *
 * @Author Link
 * @Date 2026-04-10 08:42:51
 * @Copyright  
 */

@Data
public class OrderProductInfoVO {


    @Schema(description = "主键ID")
    private Long id;

    @Schema(description = "对应订单表主键")
    private String orderId;

    @Schema(description = "商品名称")
    private String productName;

    @Schema(description = "商品编码")
    private String productCode;

    @Schema(description = "商品单价")
    private BigDecimal price;

    @Schema(description = "购买数量")
    private Integer quantity;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

}
