package com.betfair.aping.entities;

import com.betfair.aping.navigation.Group;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Event {

    private String id;
    private String name;
    private String countryCode;
    private String timezone;
    private String venue;
    private Date openDate;

    private List<Market> markets = new ArrayList<>();

    private List<Event> events = new ArrayList<>();

    private List<Group> groups = new ArrayList<>();

    public List<Event> getEvents() {
        return events;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public List<Market> getMarkets() {
        return markets;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public Date getOpenDate() {
        return openDate;
    }

    public void setOpenDate(Date openDate) {
        this.openDate = openDate;
    }

    public String toString() {
        return "{" + "" + "id=" + getId() + "," + "name=" + getName() + ","
                + "countryCode=" + getCountryCode() + "," + "timezone="
                + getTimezone() + "," + "venue=" + getVenue() + ","
                + "openDate=" + getOpenDate() + "," + "}";
    }

}
