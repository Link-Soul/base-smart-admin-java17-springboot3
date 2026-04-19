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
     * @author FangCheng
     * @since 2025/01/02 14:16
     * @param messageKey messageKey
     * @param args args
     * @param defaultMessage defaultMessage
     * @return java.lang.String
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
     * @author FangCheng
     * @since 2025/01/16 13:37
     * @param messageKey messageKey
     * @return java.lang.String
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

    /**
     * 缓存class的Excel注解属性
     *
     * @author FangCheng
     * @since 2025/01/21 08:42
     * @param cs cs
     */
    public static <T> void cacheExcelClass(Class<T> cs) {
        Field[] fields = cs.getDeclaredFields();
        for (Field field : fields) {
            Excel annotation = field.getAnnotation(Excel.class);
            if (annotation != null) {
                excelMap.put(field.getName(), annotation.name());
            }
        }
    }

    /**
     * 还原class的Excel注解属性
     *
     * @author FangCheng
     * @since 2025/01/21 08:42
     * @param cs cs
     */
    public static <T> void reSetExcelClass(Class<T> cs) {
        try {
            Field[] fields = cs.getDeclaredFields();
            for (Field field : fields) {
                // 获取字段上的注解
                Excel anoExcel = field.getAnnotation(Excel.class);
                if (anoExcel != null) {
                    // 获取代理处理器
                    InvocationHandler invocationHandler = Proxy.getInvocationHandler(anoExcel);
                    // 获取私有 memberValues 属性
                    Field f = invocationHandler.getClass().getDeclaredField("memberValues");
                    f.setAccessible(true);
                    // 获取实例的属性map
                    Map<String, Object> memberValues = (Map<String, Object>) f.get(invocationHandler);
                    // 获取属性值
                    String excelValue = excelMap.get(field.getName());

                    if (StrUtil.isNotBlank(excelValue)) {
                        memberValues.put("name", excelValue);
                    }
                }
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 改写class的Excel属性
     *
     * @author FangCheng
     * @since 2025/01/21 08:42
     * @param pojoClass pojoClass
     * @return java.lang.Class<T>
     */
    public static <T> Class<T> chooseLang(Class<T> pojoClass) {
        try {
            //获取实体类中全部字段
            Field[] fields = pojoClass.getDeclaredFields();

            for (Field field : fields) {
                // 获取字段上的注解
                Excel anoExcel = field.getAnnotation(Excel.class);
                if (anoExcel != null) {
                    // 获取代理处理器
                    InvocationHandler invocationHandler = Proxy.getInvocationHandler(anoExcel);
                    // 获取私有 memberValues 属性
                    Field f = invocationHandler.getClass().getDeclaredField("memberValues");
                    f.setAccessible(true);
                    // 获取实例的属性map
                    Map<String, Object> memberValues = (Map<String, Object>) f.get(invocationHandler);
                    // 获取属性值
                    String excelValue = (String) memberValues.get("name");

                    if (StrUtil.isNotBlank(excelValue)) {
                        //根据配置的语言标识，重新新设置属性值
                        String value = I18nUtils.getLocaleMessage(excelValue);
                        if (StrUtil.isNotBlank(value)) {
                            memberValues.put("name", value);
                        } else {
                            memberValues.put("name", excelValue);
                        }
                    }
                }
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            log.error(e.getMessage(), e);
        }

        return pojoClass;
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
