package com.insider.travel.japan.wabisabi.vacaite.console.calendarevent.privateapi.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.owasp.esapi.errors.EncryptionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.insider.travel.japan.wabisabi.common.utils.TokenUtil;
import com.insider.travel.japan.wabisabi.serverlib.domain.dao.CalendarEventDao;
import com.insider.travel.japan.wabisabi.serverlib.domain.model.CalendarEvent;

public class CalendarEventService {
	
    private static final Logger LOG = LoggerFactory.getLogger(CalendarEventService.class);

    @Inject 
    private CalendarEventDao calendarEventDao;
            
    //Set parameter to add new Event
    public CalendarEvent createEvent ( 
    		String id, 
    		String accountid, 
    		String eventtitle, 
    		Date startdate,
    		Date enddate,
    		Date starttime,
    		Date endtime,
    		String eventnote,
    		String eventlocation) throws EncryptionException {
 	
    	
    	
    	Timestamp tsStartdate=new Timestamp(startdate.getTime());
    	Timestamp tsEnddate=new Timestamp(enddate.getTime());
    	Timestamp tsStarttime=new Timestamp(starttime.getTime());
    	Timestamp tsEndtime=new Timestamp(endtime.getTime());
    	
    	CalendarEvent calendarEvent = new CalendarEvent();
    	calendarEvent.setId(id);
    	calendarEvent.setAccountId(accountid);
    	calendarEvent.setEventTitle(eventtitle);
    	calendarEvent.setStartdate(tsStartdate);
    	calendarEvent.setEnddate(tsEnddate);
    	calendarEvent.setStarttime(tsStarttime);
    	calendarEvent.setEndtime(tsEndtime);
    	calendarEvent.setEventNote(eventnote);
    	calendarEvent.setEventLocation(eventlocation);
    	CalendarEvent insertNewEvent = calendarEventDao.create(calendarEvent);
    	
    	return insertNewEvent; 
    	
    	
    }
    
    //Set parameter to update Event
    public CalendarEvent updateEvent (
    		String id,
    		String eventtitle, 
    		Date startdate,
    		Date enddate,
    		Date starttime,
    		Date endtime,
    		String eventnote,
    		String eventlocation) throws EncryptionException {
    	
    	Timestamp tsStartdate=new Timestamp(startdate.getTime());
    	Timestamp tsEnddate=new Timestamp(enddate.getTime());
    	Timestamp tsStarttime=new Timestamp(starttime.getTime());
    	Timestamp tsEndtime=new Timestamp(endtime.getTime());
    	
    	CalendarEvent calendarEvent = calendarEventDao.findEventById(id);
    	calendarEvent.setEventTitle(eventtitle);
    	calendarEvent.setStartdate(tsStartdate);
    	calendarEvent.setEnddate(tsEnddate);
    	calendarEvent.setStarttime(tsStarttime);
    	calendarEvent.setEndtime(tsEndtime);
    	calendarEvent.setEventNote(eventnote);
    	calendarEvent.setEventLocation(eventlocation);    	
    	CalendarEvent updateEvent = calendarEventDao.edit(calendarEvent);
    	
    	return updateEvent;
    	
    }
    
    //Get all events based on accountId
    public List<CalendarEvent> findAllEvents(String accountId) {
        
    	List<CalendarEvent> viewAllEvents = calendarEventDao.findEventsByAccountId(accountId);
    	
    	
        if (viewAllEvents.isEmpty()) {
            return null;
        }
        return viewAllEvents;
    }
    
    //Delete event based on id
    public void deleteEvent(String id)
    {
    	CalendarEvent calendarEvent = calendarEventDao.findEventById(id);
    	calendarEventDao.remove(calendarEvent);
    	
    }
    
    public static String tokenString() {
        return (TokenUtil.getToken()).substring(0, 32);
    }


}
