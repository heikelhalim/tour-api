package com.insider.travel.japan.wabisabi.vacaite.console.account.privateapi.validator.constraints;

import com.insider.travel.japan.wabisabi.vacaite.console.account.privateapi.validator.RangeValidator;
import java.lang.annotation.Documented;
import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

/**
 *
 * @author JapanTravelInsider
 */
@Target({ METHOD, FIELD, ANNOTATION_TYPE, PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = RangeValidator.class)
@Documented
public @interface Range {
    
    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    String message() default "{com.vacaite.constraints.range.message}";

    String name();

    boolean required() default false;

    int max();

    int min();
    
}
