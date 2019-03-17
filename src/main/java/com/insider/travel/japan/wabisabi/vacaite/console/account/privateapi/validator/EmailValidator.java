package com.insider.travel.japan.wabisabi.vacaite.console.account.privateapi.validator;

import com.insider.travel.japan.wabisabi.vacaite.console.account.privateapi.validator.constraints.Email;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.apache.commons.validator.GenericValidator;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author JapanTravelInsider
 */
public class EmailValidator implements ConstraintValidator<Email, String> {
    
    private int maxSize;
    private boolean required;
    
    @Override
    public void initialize(Email annotation)    {
        this.maxSize = annotation.maxSize();
        this.required = annotation.required();
    }
    
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        //checking if the flag is true
        if (required && StringUtils.isEmpty(value))
        {
            setMessageTemplate(context, "Insert your email address");
            return false;
        }
        if (value != null)
        {
            //checking for string size
            if (value.length() > maxSize)
            {
                setMessageTemplate(context, "Your email address is too long");
                return false;
            }
            //checking for email format
            if (!GenericValidator.isEmail(value))
            {
                setMessageTemplate(context, "Insert correct email address please");
                return false;
            }
        }
        return true;
    }

    private void setMessageTemplate(ConstraintValidatorContext context, String template) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(template).addConstraintViolation();
    }
    
}
