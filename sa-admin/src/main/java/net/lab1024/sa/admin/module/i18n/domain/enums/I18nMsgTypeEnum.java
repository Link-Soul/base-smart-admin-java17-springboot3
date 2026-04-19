package net.lab1024.sa.admin.module.i18n.domain.enums;

import lombok.Getter;

/**
 * 国际化消息类型枚举
 *
 * @author FangCheng
 * @since 2024/12/27 16:52
 **/
@Getter
public enum I18nMsgTypeEnum {

    FRONT("F", "前端"),

    BACK("B", "后端");

    /**
     * 编码
     */
    private final String code;
    /**
     * 描述
     */
    private final String desc;

    I18nMsgTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
