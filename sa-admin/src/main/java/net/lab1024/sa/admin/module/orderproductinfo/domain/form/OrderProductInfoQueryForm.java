package net.lab1024.sa.admin.module.orderproductinfo.domain.form;

import net.lab1024.sa.base.common.domain.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 订单商品 分页查询表单
 *
 * @Author Link
 * @Date 2026-04-10 08:42:51
 * @Copyright  
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class OrderProductInfoQueryForm extends PageParam {

    @Schema(description = "对应订单表主键")
    private String orderId;

    @Schema(description = "商品名称")
    private String productName;

}
