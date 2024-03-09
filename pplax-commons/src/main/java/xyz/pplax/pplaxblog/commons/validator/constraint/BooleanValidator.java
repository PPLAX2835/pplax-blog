package xyz.pplax.pplaxblog.commons.validator.constraint;

import xyz.pplax.pplaxblog.commons.validator.annotion.BooleanNotNULL;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 判断Boolean类型是否为空【校验器】
 */
public class BooleanValidator implements ConstraintValidator<BooleanNotNULL, Boolean> {

    private BooleanNotNULL constraintAnnotation;

    @Override
    public void initialize(BooleanNotNULL constraintAnnotation) {
        this.constraintAnnotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(Boolean value, ConstraintValidatorContext context) {
        if (value == null) {
            return !constraintAnnotation.required();
        }
        return true;
    }
}
