package net.lab1024.sa.base.common.annoation.export;

import java.lang.annotation.*;

/**
 * 动态模板导出核心注解
 * 用于标记实体类，声明该类可被导出配置识别。
 *
 * @author Link
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Exportable {
    // 可配置显示名称，默认类名
    String name() default "";
}
