package com.hj.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * easy poi 通过列表导出excel参数和 easy poi一样
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ExportExcel {
    String name() default "excel.xls";
    String title() default "";
    short headerHeight() default 0;
}
