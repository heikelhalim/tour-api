package com.insider.travel.japan.wabisabi.vacaite.console.account.privateapi.validator;

import com.insider.travel.japan.wabisabi.vacaite.console.account.privateapi.validator.constraints.Range;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.apache.commons.validator.GenericValidator;

/**
 *
 * @author JapanTravelInsider
 */
public class RangeValidator implements ConstraintValidator<Range, String> {
    
    private boolean required;
    private final String PATTERN = "[0-9]+";
    private int min;
    private int max;

    @Override
    public void initialize(Range annotation) {
        this.min = annotation.min();
        this.max = annotation.max();
        this.required = annotation.required();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (required && value == null) {
            setMessageTemplate(context, "{com.vacaite.constraints.required.message}");
            return false;
        }
        if (value != null) {
            if (!value.matches(PATTERN)) {
                setMessageTemplate(context, "{com.vacaite.constraints.range.message}");
                return false;
            }
            int intValue;
            try {
                intValue = Integer.parseInt(value);
            } catch (NumberFormatException e) {
                setMessageTemplate(context, "{com.vacaite.constraints.range.message}");
                return false;
            }
            return GenericValidator.isInRange(intValue, min, max);
        }
        return true;
    }
    
    private void setMessageTemplate(ConstraintValidatorContext context, String template) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(template).addConstraintViolation();
    }
    
}
