package com.insider.travel.japan.wabisabi.vacaite.console.account.privateapi.validator;

import com.insider.travel.japan.wabisabi.vacaite.console.account.privateapi.validator.constraints.NewPassword;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author JapanTravelInsider
 */
public class NewPasswordValidator implements ConstraintValidator<NewPassword, String> {
    
    private final String PATTERN = "[a-zA-Z0-9._\\-+\\\"()=@:;{}\\[\\]]*";
    private int maxSize;
    private int minSize;
    private boolean required;
    private String message;

    @Override
    public void initialize(NewPassword annotation) {
        this.maxSize = annotation.maxSize();
        this.minSize = annotation.minSize();
        this.required = annotation.required();
        this.message = annotation.message();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (required && StringUtils.isEmpty(value)) {
            setMessageTemplate(context, "Insert your password");
            return false;
        }
        if (value != null) {
            //checking if the password is blank
            if (value.length() == 0) {
                setMessageTemplate(context, "Insert your password");
                return false;
            }
            //checking for string size
            if (value.length() > maxSize) {
                setMessageTemplate(context, "Your password is too long");
                return false;
            }
            if (value.length() < minSize) {
                setMessageTemplate(context, "Insert your password");
                return false;
            }
            //checking for password pattern
            if (!value.matches(PATTERN)) {
                setMessageTemplate(context, "Include numbers, alphabets and symbols(._-+=@:;) in your password");
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
