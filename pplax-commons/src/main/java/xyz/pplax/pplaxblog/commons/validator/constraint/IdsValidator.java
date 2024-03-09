package xyz.pplax.pplaxblog.commons.validator.constraint;

import xyz.pplax.pplaxblog.commons.constants.CharacterConstants;
import xyz.pplax.pplaxblog.commons.validator.annotion.IdsValid;
import xyz.pplax.pplaxblog.commons.utils.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class IdsValidator implements ConstraintValidator<IdsValid, List<String>> {

    private IdsValid constraintAnnotation;

    @Override
    public void initialize(IdsValid constraintAnnotation) {
        this.constraintAnnotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(List<String> uids, ConstraintValidatorContext context) {
        if (uids == null || uids.isEmpty()) {
            return !constraintAnnotation.required();
        }

        for (String uid : uids) {
            if (!isValidUid(uid)) {
                return !constraintAnnotation.required();
            }
        }

        return true;
    }

    private boolean isValidUid(String uid) {
        return uid != null && !StringUtils.isBlank(uid) && !StringUtils.isEmpty(uid.trim()) && uid.length() == CharacterConstants.NUM_32;
    }
}