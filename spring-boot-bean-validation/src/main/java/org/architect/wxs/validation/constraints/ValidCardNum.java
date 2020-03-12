package org.architect.wxs.validation.constraints;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * {@link}
 *
 * @author wangxiaoshuai on 2020/2/23
 * @javadoc ：合法卡号的校验
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = {ValidCardNumberConstraintValidator.class}
)
public @interface ValidCardNum {
    String message();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
