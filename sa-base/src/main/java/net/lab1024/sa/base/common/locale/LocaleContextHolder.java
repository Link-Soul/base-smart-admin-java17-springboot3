package net.lab1024.sa.base.common.locale;

import java.util.Locale;

/**
 * 语言上下文持有者
 *
 * @author Link
 * @since 2025/01/02 11:13
 **/
public class LocaleContextHolder {
    /**
     * 当前线程的语言环境
     */
    private static final ThreadLocal<Locale> localeThreadLocal = new ThreadLocal<>();

    /**
     * 设置当前线程的语言环境
     *
     * @param locale 语言环境
     */
    public static void setLocale(Locale locale) {
        localeThreadLocal.set(locale);
    }

    /**
     * 获取当前线程的语言环境
     *
     * @return 语言环境
     */
    public static Locale getLocale() {
        Locale locale = localeThreadLocal.get();
        return locale == null || locale == Locale.CHINA ? Locale.CHINESE : locale;
    }

    /**
     * 清除当前线程的语言环境
     */
    public static void clearLocale() {
        localeThreadLocal.remove();
    }
}
