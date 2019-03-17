package com.insider.travel.japan.wabisabi.vacaite.console.account.response;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElementRef;

/**
 *
 * @author JapanTravelInsider
 */
public class AdminListingsResponse {
    
    private int totalSize;
    private int fetchSize;
    private int startIndex;
    private int maxResults;
    
    @XmlElementRef(name="admin-listings")
    private List<AdminListingsLog> responses = new ArrayList<>();

    public List<AdminListingsLog> getResponses() {
        return responses;
    }

    public void setResponses(List<AdminListingsLog> responses) {
        this.responses = responses;
    }

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    public int getFetchSize() {
        return fetchSize;
    }

    public void setFetchSize(int fetchSize) {
        this.fetchSize = fetchSize;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public int getMaxResults() {
        return maxResults;
    }

    public void setMaxResults(int maxResults) {
        this.maxResults = maxResults;
    }
    
}
