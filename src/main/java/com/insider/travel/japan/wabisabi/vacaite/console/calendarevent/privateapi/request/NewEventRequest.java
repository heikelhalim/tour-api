package com.insider.travel.japan.wabisabi.vacaite.console.calendarevent.privateapi.request;

import java.util.Date;

public class NewEventRequest {
	
	
	
	/*accountId;
	 * 
	 */
	
	private String id;
	private String accountId; 
	private String eventTitle;
	private String eventNote;
	private String eventLocation;
	private Date startDate;
	private Date endDate;
	private Date startTime;
	private Date endTime;
	
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
	
    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
	
    public String getEventTitle() {
        return eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }
	
    public String getEventNote() {
        return eventNote;
    }

    public void setEventNote(String eventNote) {
        this.eventNote = eventNote;
    }
    
	public String getEventLocation() {
		return eventLocation;
	}

	public void setEventLocation(String eventLocation) {
		this.eventLocation = eventLocation;
	}
    
	
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	public Date getStartTime() {
		return startTime;
	}

	public void setStarttime(Date startTime) {
		this.startTime = startTime;
	}
	
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	
	
	
}
