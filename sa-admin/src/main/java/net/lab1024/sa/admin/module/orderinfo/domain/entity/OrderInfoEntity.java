package net.lab1024.sa.admin.module.orderinfo.domain.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;
import net.lab1024.sa.admin.module.orderproductinfo.domain.entity.OrderProductInfoEntity;
import net.lab1024.sa.base.common.annoation.export.ConnectionTable;
import net.lab1024.sa.base.common.annoation.export.Exportable;

/**
 * 测试用订单表 实体类
 *
 * @Author Link
 * @Date 2026-04-09 14:30:50
 * @Copyright  
 */

@Data
@TableName("order_info")
@Exportable(name = "测试用订单")
public class OrderInfoEntity {

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 客户名称
     */
    private String customerName;

    /**
     * 总金额
     */
    private BigDecimal totalAmount;

    /**
     * 备注
     */
    private String remark;

    /**
     * 
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     * 
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    // 在 OrderInfoEntity 中添加虚拟字段，声明是可以通过 order_id 关联到 order_product_info 表的
    @ConnectionTable(targetTable = "order_product_info", targetField = "order_id")
    // 标记非数据库字段
    @TableField(exist = false)
    private List<OrderProductInfoEntity> products;

}
