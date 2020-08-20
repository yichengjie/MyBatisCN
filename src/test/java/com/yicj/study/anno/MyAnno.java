package com.yicj.study.anno;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.PARAMETER})
public @interface MyAnno {
    String name() default "";
    String value() default "";
}
