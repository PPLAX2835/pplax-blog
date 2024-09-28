package xyz.pplax.pplaxblog.commons.validator.constraint;

import xyz.pplax.pplaxblog.commons.utils.StringUtils;
import xyz.pplax.pplaxblog.commons.validator.annotion.Regex;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

/**
 * 通过正则判断是否合法【校验器】
 */
public class RegexValidator implements ConstraintValidator<Regex, String> {

    private Regex constraintAnnotation;

    @Override
    public void initialize(Regex constraintAnnotation) {
        this.constraintAnnotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        String regex = constraintAnnotation.value();
        return StringUtils.isNotEmpty(value) && Pattern.matches(regex, value);
    }


}
