package com.insider.travel.japan.wabisabi.vacaite.console.calendarevent.privateapi;

import java.net.URI;
import java.text.ParseException;
import java.util.List;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.owasp.esapi.errors.EncryptionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.insider.travel.japan.wabisabi.serverlib.domain.model.CalendarEvent;
import com.insider.travel.japan.wabisabi.vacaite.console.calendarevent.privateapi.request.NewEventRequest;
import com.insider.travel.japan.wabisabi.vacaite.console.calendarevent.privateapi.service.CalendarEventService;

/**
*
* @author Heikel
*/

@Path("calendar")
public class CalendarEventController {
	 
	
	private static final Logger LOG = LoggerFactory.getLogger(CalendarEventController.class);
	
	
	//@Inject
	private CalendarEventService calendarEventService;
	
	
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
	public Response createEvent(@Valid @NotNull NewEventRequest request, @Context UriInfo uriInfo) throws EncryptionException
	{
		
    	String id = calendarEventService.tokenString();
    	    	
    	CalendarEvent calendarEvent = calendarEventService.createEvent(
    			id, 
    			request.getAccountId(),
    			request.getEventTitle(),
    			request.getStartDate(),
    			request.getEndDate(),
    			request.getStartTime(),
    			request.getEndTime(),
    			request.getEventLocation(), 
    			request.getEventNote()
    		);
    			
    			
        URI uri = uriInfo.getRequestUriBuilder().path(calendarEvent.getId()).build();
        
        //LOG.info(SiteLogMessage.EVENT_REGISTERED.getMessage());
		
        return Response.created(uri).build();
	}
 
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response updateEvent(@Valid @NotNull NewEventRequest request, UriInfo uriInfo) throws EncryptionException, ParseException {

    	calendarEventService.updateEvent(
    			request.getId(), 
    			request.getEventTitle(),
    			request.getStartDate(),
    			request.getEndDate(),
    			request.getStartTime(),
    			request.getEndTime(),
    			request.getEventLocation(), 
    			request.getEventNote()
    			);
    	
        //LOG.info(SiteLogMessage.EVENT_UPDATED.getMessage());

    	
    	return Response.ok().build();
    }    
    
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response viewEventsByAccount(@Valid @NotNull NewEventRequest request) throws ParseException {
        
//        int intStartIndex = Integer.parseInt(startIndex);
//        int intMaxResults = Integer.parseInt(maxResults);
//        int intTotalSize = calendarEventService.countAll();
        
        //List<CalendarEvent> guideList = accountService.findAllGuide(intStartIndex, intMaxResults);
        List<CalendarEvent> accountEventsList = calendarEventService.findAllEvents(request.getAccountId());
        
        if (accountEventsList == null) {
            return Response.noContent().build();
        }
        
        
        return Response.ok(accountEventsList, MediaType.APPLICATION_JSON).build();
        
   
    }
            
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response deleteEvent(@Valid @NotNull NewEventRequest request, UriInfo uriInfo) throws EncryptionException, ParseException {

    	calendarEventService.deleteEvent(request.getId()); 
    	
    	
        //LOG.info(SiteLogMessage.EVENT_DELETED.getMessage());

    	
    	return Response.ok().build();
    }    
    
}
