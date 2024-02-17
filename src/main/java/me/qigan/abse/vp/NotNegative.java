package me.qigan.abse.vp;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.CLASS;

@Retention(CLASS)
@Target({ElementType.FIELD, ElementType.PARAMETER})
public @interface NotNegative {

}
