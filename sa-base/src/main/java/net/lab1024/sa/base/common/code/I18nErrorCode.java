package net.lab1024.sa.base.common.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 订单错误码
 *
 * @author ZhouBinBin
 * @since 2026/4/2 15:09
 */
@Getter
@AllArgsConstructor
public enum I18nErrorCode implements ErrorCode {

    I18N_INFO_KEY_EXIST(40001, "msgKey已存在"),

    ;

    private final int code;

    private final String msg;

    private final String level;

    I18nErrorCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
        this.level = LEVEL_USER;
    }

}
