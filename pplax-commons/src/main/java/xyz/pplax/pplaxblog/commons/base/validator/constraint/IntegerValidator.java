package xyz.pplax.pplaxblog.commons.base.validator.constraint;

import xyz.pplax.pplaxblog.commons.base.validator.annotion.IntegerNotNull;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 判断Integer是否为空【校验器】
 */
public class IntegerValidator implements ConstraintValidator<IntegerNotNull, Integer> {

    @Override
    public void initialize(IntegerNotNull constraintAnnotation) {

    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        return true;
    }
}
