package xyz.pplax.pplaxblog.commons.validator.constraint;

import xyz.pplax.pplaxblog.commons.validator.annotion.IntegerNotNull;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 判断Integer是否为空【校验器】
 */
public class IntegerValidator implements ConstraintValidator<IntegerNotNull, Integer> {

    private IntegerNotNull constraintAnnotation;

    @Override
    public void initialize(IntegerNotNull constraintAnnotation) {
        this.constraintAnnotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        if (value == null) {
            return !constraintAnnotation.required();
        }
        return true;
    }
}
