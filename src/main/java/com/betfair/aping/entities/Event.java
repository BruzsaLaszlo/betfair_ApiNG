package com.betfair.aping.entities;

public class Event {

    private String id;
    private String name;
    private String countryCode;
    private String timezone;
    private String venue;
    private String openDate;


    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getTimezone() {
        return timezone;
    }

    public String getVenue() {
        return venue;
    }

    public String getOpenDate() {
        return openDate;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", countryCode='" + countryCode + '\'' +
                ", timezone='" + timezone + '\'' +
                ", venue='" + venue + '\'' +
                ", openDate='" + openDate + '\'' +
                '}';
    }
}
