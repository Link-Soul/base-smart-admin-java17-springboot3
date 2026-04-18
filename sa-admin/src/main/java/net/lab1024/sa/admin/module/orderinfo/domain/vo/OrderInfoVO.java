package net.lab1024.sa.admin.module.orderinfo.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 测试用订单表 列表VO
 *
 * @Author Link
 * @Date 2026-04-09 14:30:50
 * @Copyright  
 */

@Data
public class OrderInfoVO {


    @Schema(description = "订单号")
    private Integer id;

    @Schema(description = "客户名称")
    private String customerName;

    @Schema(description = "总金额")
    private BigDecimal totalAmount;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

}
