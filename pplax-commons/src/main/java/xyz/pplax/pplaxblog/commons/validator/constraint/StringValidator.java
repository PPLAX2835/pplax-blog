package xyz.pplax.pplaxblog.commons.validator.constraint;

import xyz.pplax.pplaxblog.commons.utils.StringUtils;
import xyz.pplax.pplaxblog.commons.validator.annotion.NotBlank;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 判断是否为空字符串【校验器】
 */
public class StringValidator implements ConstraintValidator<NotBlank, String> {

    private NotBlank constraintAnnotation;

    @Override
    public void initialize(NotBlank constraintAnnotation) {
        this.constraintAnnotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || StringUtils.isBlank(value) || StringUtils.isEmpty(value.trim())) {
            return !constraintAnnotation.required();
        }
        return true;
    }
}
