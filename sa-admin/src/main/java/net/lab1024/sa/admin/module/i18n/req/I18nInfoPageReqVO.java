package net.lab1024.sa.admin.module.i18n.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.lab1024.sa.base.common.domain.PageParam;

import java.io.Serial;
import java.io.Serializable;

/**
 * 国际化表
 *
 * @author FangCheng
 * @since 2025-07-16 16:57:43
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class I18nInfoPageReqVO extends PageParam implements Serializable {
	@Serial
    private static final long serialVersionUID = 1L;

	/**
	 * 国际化内容
	 */
	@Schema(description = "国际化内容")
	private String msg;

	/**
	 * 国际化Key
	 */
	@Schema(description = "国际化Key")
	private String msgKey;

	/**
	 * 消息类型：F前端/B后端
	 */
	@Schema(description = "消息类型：F前端/B后端")
	private String msgType;
}
