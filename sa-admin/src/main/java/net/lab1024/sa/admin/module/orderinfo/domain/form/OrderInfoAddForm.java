package net.lab1024.sa.admin.module.orderinfo.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.Data;

/**
 * 测试用订单表 新建表单
 *
 * @Author Link
 * @Date 2026-04-09 14:30:50
 * @Copyright  
 */

@Data
public class OrderInfoAddForm {

    @Schema(description = "客户名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "客户名称 不能为空")
    private String customerName;

    @Schema(description = "总金额", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "总金额 不能为空")
    private BigDecimal totalAmount;

    @Schema(description = "备注")
    private String remark;

}