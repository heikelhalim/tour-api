package com.insider.travel.japan.wabisabi.vacaite.console.account.config;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author JapanTravelInsider
 */
@javax.ws.rs.ApplicationPath("api")
public class ApplicationConfig extends Application {
    
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(com.insider.travel.japan.wabisabi.vacaite.console.account.exception.ValidationExceptionHandler.class);
        resources.add(com.insider.travel.japan.wabisabi.vacaite.console.account.privateapi.AccountResource.class);
        resources.add(com.insider.travel.japan.wabisabi.vacaite.console.calendarevent.privateapi.CalendarEventController.class);
        
        
    }
    
}
