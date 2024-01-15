package xyz.pplax.pplaxblog.base.validator.constraint;

import xyz.pplax.pplaxblog.base.global.Constants;
import xyz.pplax.pplaxblog.base.validator.annotion.IdsValid;
import xyz.pplax.pplaxblog.utils.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class IdsValidator implements ConstraintValidator<IdsValid, List<String>> {

    @Override
    public void initialize(IdsValid constraintAnnotation) {

    }

    @Override
    public boolean isValid(List<String> uids, ConstraintValidatorContext context) {
        if (uids == null || uids.isEmpty()) {
            return false;
        }

        for (String uid : uids) {
            if (!isValidUid(uid)) {
                return false;
            }
        }

        return true;
    }

    private boolean isValidUid(String uid) {
        return uid != null && !StringUtils.isBlank(uid) && !StringUtils.isEmpty(uid.trim()) && uid.length() == Constants.THIRTY_TWO;
    }
}