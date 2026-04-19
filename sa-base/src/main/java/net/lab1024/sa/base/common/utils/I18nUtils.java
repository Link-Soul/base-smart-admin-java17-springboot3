package net.lab1024.sa.base.common.utils;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import net.lab1024.sa.base.common.locale.LocaleContextHolder;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.StaticMessageSource;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class I18nUtils {


    private static ConcurrentHashMap<String, String> excelMap = new ConcurrentHashMap<>();

    /**
     * 取国际化信息
     *
     * @param messageKey     messageKey
     * @param args           args
     * @param defaultMessage defaultMessage
     * @return java.lang.String
     * @author Link
     * @since 2025/01/02 14:16
     */
    public static String getLocaleMessage(String messageKey, Object[] args, String defaultMessage) {
        String message = null;
        StaticMessageSource staticMessageSource = SpringUtil.getBean(StaticMessageSource.class);
        try {
            // 按照国际化配置获取异常信息，找不到则取中文信息
            message = staticMessageSource.getMessage(messageKey, args, LocaleContextHolder.getLocale());
        } catch (NoSuchMessageException ex) {
            try {
                message = staticMessageSource.getMessage(messageKey, args, Locale.CHINESE);
            } catch (NoSuchMessageException ex1) {
                log.error("NoSuchMessageException,code码:{}", messageKey, ex1);
            }
        }
        return StrUtil.isNotBlank(message) ? message : defaultMessage;
    }

    /**
     * 取国际化信息
     *
     * @param messageKey messageKey
     * @return java.lang.String
     * @author Link
     * @since 2025/01/16 13:37
     */
    public static String getLocaleMessage(String messageKey) {
        String message = null;
        StaticMessageSource staticMessageSource = SpringUtil.getBean(StaticMessageSource.class);
        try {
            // 按照国际化配置获取异常信息
            message = staticMessageSource.getMessage(messageKey, null, LocaleContextHolder.getLocale());
        } catch (NoSuchMessageException ex) {
            log.error("NoSuchMessageException,code码:{}", messageKey, ex);
        }
        return message;
    }


    public static String getLocaleMessage(String messageKey, Object[] args) {
        String message = null;
        StaticMessageSource staticMessageSource = SpringUtil.getBean(StaticMessageSource.class);
        try {
            // 按照国际化配置获取异常信息，找不到则取中文信息
            message = staticMessageSource.getMessage(messageKey, args, LocaleContextHolder.getLocale());
        } catch (NoSuchMessageException ex) {
            try {
                message = staticMessageSource.getMessage(messageKey, args, Locale.CHINESE);
            } catch (NoSuchMessageException ex1) {
                log.error("NoSuchMessageException,code码:{}", messageKey, ex1);
            }
        }
        return message;
    }

}
