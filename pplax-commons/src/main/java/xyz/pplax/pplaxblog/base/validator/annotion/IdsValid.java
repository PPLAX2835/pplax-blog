package xyz.pplax.pplaxblog.base.validator.annotion;

import xyz.pplax.pplaxblog.base.validator.constraint.IdsValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

@Target({TYPE, ANNOTATION_TYPE, FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {IdsValidator.class})
public @interface IdsValid {

    boolean required() default true;

    String message() default "Invalid uids in the list";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
