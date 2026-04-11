package net.lab1024.sa.admin.module.orderproductinfo.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.Data;

/**
 * 订单商品 新建表单
 *
 * @Author Link
 * @Date 2026-04-10 08:42:51
 * @Copyright  
 */

@Data
public class OrderProductInfoAddForm {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "主键ID 不能为空")
    private Long id;

    @Schema(description = "对应订单表主键", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "对应订单表主键 不能为空")
    private String orderId;

    @Schema(description = "商品名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "商品名称 不能为空")
    private String productName;

    @Schema(description = "商品编码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "商品编码 不能为空")
    private String productCode;

    @Schema(description = "商品单价", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "商品单价 不能为空")
    private BigDecimal price;

    @Schema(description = "购买数量", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "购买数量 不能为空")
    private Integer quantity;

}