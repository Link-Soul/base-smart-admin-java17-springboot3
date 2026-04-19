package net.lab1024.sa.base.common.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

/**
 * BaseEntity
 *
 * @author wenbin
 * @version V1.0
 * @date 2020年3月18日
 */
@Data
public class BaseEntity {

    @TableId
    private String id;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;
}
