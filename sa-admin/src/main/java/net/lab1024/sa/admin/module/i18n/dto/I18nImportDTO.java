package net.lab1024.sa.admin.module.i18n.dto;

import cn.idev.excel.annotation.ExcelProperty;
import lombok.Data;


/**
 * 国际化表
 *
 * @author BaiHongQing
 * @since 2025/7/17 8:41
 */
@Data
public class I18nImportDTO {

	/**
	 * 国际化key
	 */
//	@Excel(name = "excel.i18n.msgKey")
	@ExcelProperty("国际化key")
	private String msgKey;

	/**
	 * 中文
	 */
//	@Excel(name = "excel.i18n.msgCn")
	@ExcelProperty("中文")
	private String msgCn;

	/**
	 * 韩文
	 */
//	@Excel(name = "excel.i18n.msgKr")
	@ExcelProperty("韩文")
	private String msgKr;

	/**
	 * 英文
	 */
//	@Excel(name = "excel.i18n.msgEn")
	@ExcelProperty("英文")
	private String msgEn;

	/**
	 * 日文
	 */
//	@Excel(name = "excel.i18n.msgJp")
	@ExcelProperty("日文")
	private String msgJp;

	/**
	 * 消息类型：F前端/B后端
	 */
//	@Excel(name = "excel.i18n.msgType")
	@ExcelProperty("消息类型")
	private String msgType;

	/**
	 * 错误码
	 */
//	@Excel(name = "excel.i18n.errorCode")
	@ExcelProperty("错误码")
	private String errorCode;
}
