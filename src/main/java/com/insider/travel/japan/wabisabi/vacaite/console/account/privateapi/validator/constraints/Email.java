package com.insider.travel.japan.wabisabi.vacaite.console.account.privateapi.validator.constraints;

import com.insider.travel.japan.wabisabi.vacaite.console.account.privateapi.validator.EmailValidator;
import java.lang.annotation.Documented;
import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

/**
 *
 * @author JapanTravelInsider
 */
@Target({ METHOD, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = EmailValidator.class)
@Documented
public @interface Email {
    
    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    String message() default "{com.vacaite.constraints.format.mail.message}";

    String name();

    boolean required() default false;

    int maxSize() default 256;
    
}
