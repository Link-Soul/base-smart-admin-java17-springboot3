package net.lab1024.sa.admin.module.orderinfo.domain.form;

import net.lab1024.sa.base.common.domain.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 测试用订单表 分页查询表单
 *
 * @Author Link
 * @Date 2026-04-09 14:30:50
 * @Copyright  
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class OrderInfoQueryForm extends PageParam {

    @Schema(description = "客户查询")
    private String customer_name;

    @Schema(description = "备注查询")
    private String remark;

    @Schema(description = "创建时间")
    private LocalDate createTimeBegin;

    @Schema(description = "创建时间")
    private LocalDate createTimeEnd;

}
