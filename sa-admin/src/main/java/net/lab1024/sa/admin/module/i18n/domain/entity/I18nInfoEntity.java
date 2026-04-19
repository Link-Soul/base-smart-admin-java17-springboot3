package net.lab1024.sa.admin.module.i18n.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.lab1024.sa.base.common.domain.BaseEntity;

import java.io.Serial;
import java.io.Serializable;

/**
 * 国际化表
 *
 * @author BaiHongQing
 * @since 2025-07-16 16:57:43
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("i18n_info")
public class I18nInfoEntity extends BaseEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 国际化key
     */
    private String msgKey;

    /**
     * 中文
     */
    private String msgCn;

    /**
     * 韩文
     */
    private String msgKr;

    /**
     * 英文
     */
    private String msgEn;

    /**
     * 日文
     */
    private String msgJp;

    /**
     * 消息类型：F前端/B后端
     */
    private String msgType;

    /**
     * 错误码
     */
    private Integer errorCode;


}
