package xyz.pplax.pplaxblog.commons.validator.constraint;

import xyz.pplax.pplaxblog.commons.utils.StringUtils;
import xyz.pplax.pplaxblog.commons.validator.annotion.Range;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 字符串范围约束，限制长度【校验器】
 */
public class RangValidator implements ConstraintValidator<Range, String> {

    private Range constraintAnnotation;

    @Override
    public void initialize(Range constraintAnnotation) {
        this.constraintAnnotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if (null == value || StringUtils.isBlank(value)) {
            return (!constraintAnnotation.required()) || constraintAnnotation.min() == 0;
        }
        return (!constraintAnnotation.required()) || value.length() >= constraintAnnotation.min() && value.length() <= constraintAnnotation.max();
    }
}
