package net.lab1024.sa.base.common.annoation.export;


import java.lang.annotation.*;


/**
 * 动态模板导出核心注解
 * 作用与实体中的字段上
 * 用于声明字段关联的其他表信息。
 *
 * @author Link
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ConnectionTable {
    // 关联的目标表名（实体类名或表名）
    String targetTable();
    // 目标表中关联的字段名
    String targetField();
    // 关联类型，默认左连接
    JoinType joinType() default JoinType.LEFT;

    enum JoinType {
        LEFT, INNER, RIGHT
    }
}