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
public enum OrderErrorCode implements ErrorCode {

    /**
     * 业务错误
     */
    BUSINESS_HANDING(40001, "呃~ 业务繁忙，请稍后重试"),

    ;

    private final int code;

    private final String msg;

    private final String level;

    OrderErrorCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
        this.level = LEVEL_BUSINESS;
    }

}
