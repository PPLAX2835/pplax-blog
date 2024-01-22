package xyz.pplax.pplaxblog.commons.base.validator.constraint;

import xyz.pplax.pplaxblog.utils.StringUtils;
import xyz.pplax.pplaxblog.commons.base.global.Constants;
import xyz.pplax.pplaxblog.commons.base.validator.annotion.IdValid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * ID校验器，主要判断是否为空，并且长度是否为32
 */
public class IdValidator implements ConstraintValidator<IdValid, String> {


    @Override
    public void initialize(IdValid constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value != null && !StringUtils.isBlank(value) && !StringUtils.isEmpty(value.trim()) && value.length() == Constants.THIRTY_TWO;
    }
}
