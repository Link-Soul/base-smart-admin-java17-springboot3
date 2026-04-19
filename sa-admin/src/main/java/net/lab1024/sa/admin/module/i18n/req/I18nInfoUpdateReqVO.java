package net.lab1024.sa.admin.module.i18n.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.io.Serializable;

/**
 * 国际化表
 *
 * @author BaiHongQing
 * @since 2025/7/17 8:41
 */
@Data
public class I18nInfoUpdateReqVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 中文
	 */
	@Schema(description = "中文")
	@NotEmpty
	private String msgCn;

	/**`
	 * 韩文
	 */
	@Schema(description = "韩文")
	@NotEmpty
	private String msgKr;

	/**
	 * 英文
	 */
	@Schema(description = "英文")
	@NotEmpty
	private String msgEn;

	/**
	 * 日文
	 */
	@Schema(description = "日文")
	@NotEmpty
	private String msgJp;
}
