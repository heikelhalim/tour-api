package com.insider.travel.japan.wabisabi.vacaite.console.account.exception;

/**
 *
 * @author JapanTravelInsider
 */
public interface LogMessage {
    
    String getMessageCode();
    
    String getMessage(String... replacements);
    
}
