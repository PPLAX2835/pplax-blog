package xyz.pplax.pplaxblog.commons.validator.constraint;

import xyz.pplax.pplaxblog.commons.validator.annotion.LongNotNull;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 判断Long是否为空【校验器】
 */
public class LongValidator implements ConstraintValidator<LongNotNull, Long> {

    private LongNotNull constraintAnnotation;

    @Override
    public void initialize(LongNotNull constraintAnnotation) {
        this.constraintAnnotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        if (value == null) {
            return !constraintAnnotation.required();
        }
        return true;
    }
}
