package xyz.pplax.pplaxblog.commons.validator.annotion;


import xyz.pplax.pplaxblog.commons.validator.constraint.RegexValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

@Target({TYPE, ANNOTATION_TYPE, FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {RegexValidator.class})
public @interface Regex {

    boolean required() default true;

    String value() default "";

    String message() default "Illegal String";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
