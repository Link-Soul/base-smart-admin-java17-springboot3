package net.lab1024.sa.base.common.annoation.export;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 动态模板导出核心注解
 * 作用与实体中的字段上
 * 用于字段级别的额外描述，比如默认别名。
 *
 * @author Link
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExportField {
    String alias() default "";

    int order() default 0;
}
