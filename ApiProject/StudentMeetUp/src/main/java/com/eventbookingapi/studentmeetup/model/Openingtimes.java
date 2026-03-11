package com.eventbookingapi.studentmeetup.model;

public class Openingtimes {
    private String doorsopen;
    private String doorsclose;
    private String lastentry;


    // Getter Methods

    public String getDoorsopen() {
        return doorsopen;
    }

    public String getDoorsclose() {
        return doorsclose;
    }

    public String getLastentry() {
        return lastentry;
    }

    // Setter Methods

    public void setDoorsopen(String doorsopen) {
        this.doorsopen = doorsopen;
    }

    public void setDoorsclose(String doorsclose) {
        this.doorsclose = doorsclose;
    }

    public void setLastentry(String lastentry) {
        this.lastentry = lastentry;
    }
}

