package net.lab1024.sa.admin.module.i18n.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 国际化表
 *
 * @author Link
 * @since 2025-07-16 16:57:43
 */
@Data
public class I18nInfoRespVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @Schema(description = "主键ID")
    private String id;

    /**
     * 国际化key
     */
    @Schema(description = "国际化key")
    private String msgKey;

    /**
     * 中文
     */
    @Schema(description = "中文")
    private String msgCn;

    /**
     * 韩文
     */
    @Schema(description = "韩文")
    private String msgKr;

    /**
     * 英文
     */
    @Schema(description = "英文")
    private String msgEn;

    /**
     * 日文
     */
    @Schema(description = "日文")
    private String msgJp;

    /**
     * 错误码
     */
    @Schema(description = "错误码")
    private String errorCode;

    /**
     * 消息类型：F前端/B后端
     */
    @Schema(description = "消息类型：F前端/B后端")
    private String msgType;

}

