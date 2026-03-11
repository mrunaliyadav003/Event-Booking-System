package com.eventbookingapi.studentmeetup.model;

import java.util.ArrayList;

public class SkiddleData
{
    private float error;
    private String totalcount;
    private float pagecount;
    ArrayList<SkiddleEvent> results = new ArrayList<SkiddleEvent>();

    // Getter Methods

    public float getError() {
        return error;
    }

    public String getTotalcount() {
        return totalcount;
    }

    public float getPagecount() {
        return pagecount;
    }

    // Setter Methods

    public void setError(float error) {
        this.error = error;
    }

    public void setTotalcount(String totalcount) {
        this.totalcount = totalcount;
    }

    public void setPagecount(float pagecount) {
        this.pagecount = pagecount;
    }
}