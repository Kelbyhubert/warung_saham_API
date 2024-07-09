package com.warungsaham.warungsahamappapi.validation.contraints.date;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;


@Constraint(validatedBy = DateFormatValidator.class)
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DateFormat {
    String pattern() default "yyyy-MM-dd";
    String message() default "Invalid Date Format";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
