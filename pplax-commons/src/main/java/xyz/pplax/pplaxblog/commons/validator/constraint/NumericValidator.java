package xyz.pplax.pplaxblog.commons.validator.constraint;

import xyz.pplax.pplaxblog.commons.utils.StringUtils;
import xyz.pplax.pplaxblog.commons.validator.annotion.Numeric;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 判断是否为数字【校验器】
 */
public class NumericValidator implements ConstraintValidator<Numeric, String> {

    private Numeric constraintAnnotation;

    @Override
    public void initialize(Numeric constraintAnnotation) {
        this.constraintAnnotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || StringUtils.isBlank(value)) {
            return !constraintAnnotation.required();
        }
        if (!StringUtils.isNumeric(value)) {
            return !constraintAnnotation.required();
        }
        return true;
    }
}
