package org.architect.wxs.validation.constraints;

import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

/**
 * {@link}
 *
 * @author wangxiaoshuai on 2020/2/23
 * @javadoc ：
 */
public class ValidCardNumberConstraintValidator implements ConstraintValidator<ValidCardNum,String> {
    @Override
    public void initialize(ValidCardNum constraintAnnotation) {

    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        String[] parts = StringUtils.delimitedListToStringArray(s,"-");
        if(parts.length!=2){
            return false;
        }

        String prefix = parts[0];
        String suffix = parts[1];

        boolean isValidPrefix = Objects.equals(prefix,"GUPAO");
        boolean isValidSuffix = org.apache.commons.lang3.StringUtils.isNumeric(suffix);
        return isValidPrefix&&isValidSuffix;
    }
}
