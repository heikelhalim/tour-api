package com.insider.travel.japan.wabisabi.vacaite.console.account.utils;

import com.insider.travel.japan.wabisabi.vacaite.console.account.exception.LogMessage;
import com.insider.travel.japan.wabisabi.vacaite.console.account.exception.ResourceBundleManager;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.ResourceBundle;

/**
 *
 * @author JapanTravelInsider
 */
public enum SiteErrorMessage implements LogMessage {
    
    REQUEST_ERROR("E500001"),
    SYSTEM_ERROR("E500002"),
    ACCOUNT_EXISTED_ERROR("E500003"),
    ACCOUNT_NOT_EXISTED("E500004"),
    ERROR_PASSWORD_HAS_EMAIL_IDENTITY("E500005"),
    SAME_PASSWORD_ERROR("E500006"),
    SAME_4PASSWORD_ERROR("E500007"),
    ERROR_LOGIN("E510008");
    
    private static final String BASE_NAME = "ErrorMessages";
    private ResourceBundle resourceBundle;
    private final String messageCode;
    
    private SiteErrorMessage(String messageCode) {
        this.messageCode = messageCode;
    }
    
    @Override
    public String getMessageCode() {
        return messageCode;
    }
    
    @Override
    public String getMessage(String... replacements) {
        resourceBundle = ResourceBundleManager.getResourceBundle(BASE_NAME, resourceBundle);
        return MessageFormat.format(resourceBundle.getString(messageCode), Arrays.toString(replacements));
    }
    
}
