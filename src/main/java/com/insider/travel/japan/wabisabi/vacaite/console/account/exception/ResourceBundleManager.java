package com.insider.travel.japan.wabisabi.vacaite.console.account.exception;

import java.util.Locale;
import java.util.ResourceBundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author JapanTravelInsider
 */
public final class ResourceBundleManager {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ResourceBundleManager.class);

    private ResourceBundleManager() { }

    public static ResourceBundle getResourceBundle(String baseName, ResourceBundle resourceBundle) {
        if (null == resourceBundle || resourceBundle.getLocale() != Locale.getDefault()) {
            LOGGER.trace("Got a resource bundle using the specified base name [" + baseName + "] and locale [" + Locale.getDefault() + "].");
            resourceBundle = ResourceBundle.getBundle(baseName, Locale.getDefault());
        }
        return resourceBundle; 

    }
    
}
