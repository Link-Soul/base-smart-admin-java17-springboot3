package net.lab1024.sa.admin.module.i18n.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 国际化表
 *
 * @author BaiHongQing
 * @since 2025/7/17 8:41
 */
@Data
public class I18nInfoReqVO implements Serializable {
	@Serial
    private static final long serialVersionUID = 1L;

	@Schema(description = "国际化key")
	@NotEmpty
	private String msgKey;

	@Schema(description = "中文")
	@NotEmpty
	private String msgCn;

	@Schema(description = "韩文")
	@NotEmpty
	private String msgKr;

	@Schema(description = "英文")
	@NotEmpty
	private String msgEn;

	@Schema(description = "日文")
	@NotEmpty
	private String msgJp;

	@Schema(description = "消息类型：F前端/B后端")
	@NotEmpty
	private String msgType;

	@Schema(description = "错误码")
	private String errorCode;
}
