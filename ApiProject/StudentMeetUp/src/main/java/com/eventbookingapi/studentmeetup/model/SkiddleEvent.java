package com.eventbookingapi.studentmeetup.model;

import java.util.ArrayList;

public class SkiddleEvent  {
    private String id;
    private String listingid;
    private boolean isSBT;
    private String uniquelistingidentifier;
    private boolean hascollapsedresults;
    private float countcollapsedresults;
    private String EventCode;
    private String eventname;
    private String cancelled;
    private String cancellationDate;
    private String cancellationType;
    private String cancellationReason;
    private String rescheduledDate;
    Venue VenueObject;
    private String imageurl;
    private String largeimageurl;
    private String xlargeimageurl;
    private String xlargeimageurlWebP;
    private String link;
    private String date;
    private String startdate;
    private String enddate;
    private String description;
    Openingtimes OpeningtimesObject;
    private String minage;
    private String imgoing = null;
    private float goingtos;
    private String goingtocount;
    private boolean tickets;
    Ticketpricing TicketpricingObject;
    private String entryprice;
    private String eventvisibility;
    private String ticketUrl;
    private boolean hotSeller;
    Rep RepObject;
    private String headerHex;
    private String currency;
    ArrayList< Object > healthAndSafety = new ArrayList < Object > ();


    // Getter Methods

    public String getId() {
        return id;
    }

    public String getListingid() {
        return listingid;
    }

    public boolean getIsSBT() {
        return isSBT;
    }

    public String getUniquelistingidentifier() {
        return uniquelistingidentifier;
    }

    public boolean getHascollapsedresults() {
        return hascollapsedresults;
    }

    public float getCountcollapsedresults() {
        return countcollapsedresults;
    }

    public String getEventCode() {
        return EventCode;
    }

    public String getEventname() {
        return eventname;
    }

    public String getCancelled() {
        return cancelled;
    }

    public String getCancellationDate() {
        return cancellationDate;
    }

    public String getCancellationType() {
        return cancellationType;
    }

    public String getCancellationReason() {
        return cancellationReason;
    }

    public String getRescheduledDate() {
        return rescheduledDate;
    }

    public Venue getVenue() {
        return VenueObject;
    }

    public String getImageurl() {
        return imageurl;
    }

    public String getLargeimageurl() {
        return largeimageurl;
    }

    public String getXlargeimageurl() {
        return xlargeimageurl;
    }

    public String getXlargeimageurlWebP() {
        return xlargeimageurlWebP;
    }

    public String getLink() {
        return link;
    }

    public String getDate() {
        return date;
    }

    public String getStartdate() {
        return startdate;
    }

    public String getEnddate() {
        return enddate;
    }

    public String getDescription() {
        return description;
    }

    public Openingtimes getOpeningtimes() {
        return OpeningtimesObject;
    }

    public String getMinage() {
        return minage;
    }

    public String getImgoing() {
        return imgoing;
    }

    public float getGoingtos() {
        return goingtos;
    }

    public String getGoingtocount() {
        return goingtocount;
    }

    public boolean getTickets() {
        return tickets;
    }

    public Ticketpricing getTicketpricing() {
        return TicketpricingObject;
    }

    public String getEntryprice() {
        return entryprice;
    }

    public String getEventvisibility() {
        return eventvisibility;
    }

    public String getTicketUrl() {
        return ticketUrl;
    }

    public boolean getHotSeller() {
        return hotSeller;
    }

    public Rep getRep() {
        return RepObject;
    }

    public String getHeaderHex() {
        return headerHex;
    }

    public String getCurrency() {
        return currency;
    }

    // Setter Methods

    public void setId(String id) {
        this.id = id;
    }

    public void setListingid(String listingid) {
        this.listingid = listingid;
    }

    public void setIsSBT(boolean isSBT) {
        this.isSBT = isSBT;
    }

    public void setUniquelistingidentifier(String uniquelistingidentifier) {
        this.uniquelistingidentifier = uniquelistingidentifier;
    }

    public void setHascollapsedresults(boolean hascollapsedresults) {
        this.hascollapsedresults = hascollapsedresults;
    }

    public void setCountcollapsedresults(float countcollapsedresults) {
        this.countcollapsedresults = countcollapsedresults;
    }

    public void setEventCode(String EventCode) {
        this.EventCode = EventCode;
    }

    public void setEventname(String eventname) {
        this.eventname = eventname;
    }

    public void setCancelled(String cancelled) {
        this.cancelled = cancelled;
    }

    public void setCancellationDate(String cancellationDate) {
        this.cancellationDate = cancellationDate;
    }

    public void setCancellationType(String cancellationType) {
        this.cancellationType = cancellationType;
    }

    public void setCancellationReason(String cancellationReason) {
        this.cancellationReason = cancellationReason;
    }

    public void setRescheduledDate(String rescheduledDate) {
        this.rescheduledDate = rescheduledDate;
    }

    public void setVenue(Venue venueObject) {
        this.VenueObject = venueObject;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public void setLargeimageurl(String largeimageurl) {
        this.largeimageurl = largeimageurl;
    }

    public void setXlargeimageurl(String xlargeimageurl) {
        this.xlargeimageurl = xlargeimageurl;
    }

    public void setXlargeimageurlWebP(String xlargeimageurlWebP) {
        this.xlargeimageurlWebP = xlargeimageurlWebP;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setOpeningtimes(Openingtimes openingtimesObject) {
        this.OpeningtimesObject = openingtimesObject;
    }

    public void setMinage(String minage) {
        this.minage = minage;
    }

    public void setImgoing(String imgoing) {
        this.imgoing = imgoing;
    }

    public void setGoingtos(float goingtos) {
        this.goingtos = goingtos;
    }

    public void setGoingtocount(String goingtocount) {
        this.goingtocount = goingtocount;
    }

    public void setTickets(boolean tickets) {
        this.tickets = tickets;
    }

    public void setTicketpricing(Ticketpricing ticketpricingObject) {
        this.TicketpricingObject = ticketpricingObject;
    }

    public void setEntryprice(String entryprice) {
        this.entryprice = entryprice;
    }

    public void setEventvisibility(String eventvisibility) {
        this.eventvisibility = eventvisibility;
    }

    public void setTicketUrl(String ticketUrl) {
        this.ticketUrl = ticketUrl;
    }

    public void setHotSeller(boolean hotSeller) {
        this.hotSeller = hotSeller;
    }

    public void setRep(Rep repObject) {
        this.RepObject = repObject;
    }

    public void setHeaderHex(String headerHex) {
        this.headerHex = headerHex;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
