package com.insider.travel.japan.wabisabi.vacaite.console.account.exception;

import com.insider.travel.japan.wabisabi.vacaite.console.account.response.ErrorResponse;
import com.insider.travel.japan.wabisabi.vacaite.console.account.utils.SiteErrorMessage;
import com.insider.travel.japan.wabisabi.vacaite.console.account.utils.SiteLogMessage;
import javax.validation.ValidationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author JapanTravelInsider
 */
@Provider
public class ValidationExceptionHandler implements ExceptionMapper<ValidationException> {
    
    private static final Logger LOG = LoggerFactory.getLogger(ValidationExceptionHandler.class);
    
    @Override
    public Response toResponse (ValidationException exception)
    {
        LOG.error(SiteLogMessage.ERROR_VALIDATION_EXCEPTION.getMessage(), exception.getMessage());
        
        String validationErrorMessage;
        try {
            if (exception.getClass() == this.getClass().getClassLoader().loadClass("javax.validation.ValidationException")) {
                validationErrorMessage = exception.getMessage();
            } else {
                String[] restEasyErrors = exception.toString().split("\r");
                validationErrorMessage = restEasyErrors[2].substring(1, restEasyErrors[2].length() - 1);
            }
        } catch (ClassNotFoundException ex) {
            LOG.error(SiteLogMessage.ERROR_VALIDATION_EXCEPTION.getMessage(), ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorResponse(SiteErrorMessage.SYSTEM_ERROR.getMessageCode(), SiteErrorMessage.SYSTEM_ERROR.getMessage())).build();
        }
        LOG.error(validationErrorMessage);
        
        if (validationErrorMessage.equals(SiteErrorMessage.ACCOUNT_EXISTED_ERROR.getMessage())) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorResponse(SiteErrorMessage.ACCOUNT_EXISTED_ERROR.getMessageCode(), validationErrorMessage)).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorResponse(SiteErrorMessage.REQUEST_ERROR.getMessageCode(), validationErrorMessage)).build();
        }
    }
    
}
