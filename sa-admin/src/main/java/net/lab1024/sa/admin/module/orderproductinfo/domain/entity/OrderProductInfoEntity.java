package net.lab1024.sa.admin.module.orderproductinfo.domain.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;
import net.lab1024.sa.base.common.annoation.export.Exportable;

/**
 * 订单商品 实体类
 *
 * @Author Link
 * @Date 2026-04-10 08:42:51
 * @Copyright  
 */

@Data
@TableName("order_product_info")
@Exportable(name = "订单商品")
public class OrderProductInfoEntity {

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 对应订单表主键
     */
    private String orderId;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 商品编码
     */
    private String productCode;

    /**
     * 商品单价
     */
    private BigDecimal price;

    /**
     * 购买数量
     */
    private Integer quantity;

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

}
