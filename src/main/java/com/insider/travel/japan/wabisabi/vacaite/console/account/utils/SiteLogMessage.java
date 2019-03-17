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
public enum SiteLogMessage implements LogMessage {

    ERROR_SYSTEM_ERROR("E600001"),
    ERROR_VALIDATION_EXCEPTION("E600002"),
    ACCOUNT_REGISTERED("I600003"),
    PASSWORD_UPDATED("I600004"),
    ERROR_PASSWORD_INCORRECT("E600005"),
    ERROR_ACCOUNT_NOT_EXIST("E610006"),
    INFO_ACCOUNT_FOUND("I610007"),
    INFO_INVALIDATED_THE_SESSION("I610008"),
    INFO_SESSION_ID_UPDATED("I610009"),
    INFO_FORCIBLY_SESSION_ID_UPDATED("I610010"),
    INFO_LOGGED_IN("I610011"),
    INFO_LOGGED_OUT("I610012"),
    ERROR_NOT_LOGGED_IN("E610013"),
    WARN_IPADDRESS_INCORRECT_LENGTH("W610014"),
    WARN_IPADDRESS_INCORRECT("W610015");
    
    private static final String BASE_NAME = "LogMessages";
    private ResourceBundle resourceBundle;
    private final String messageCode;
    
    private SiteLogMessage(String messageCode) {
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
