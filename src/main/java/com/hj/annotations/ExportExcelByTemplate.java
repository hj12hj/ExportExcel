package com.hj.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 通过模板导出excel
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ExportExcelByTemplate {
    /**
     * 模板文件路径
     */
    String templatePath();

    String name() default "excel.xls";

}
