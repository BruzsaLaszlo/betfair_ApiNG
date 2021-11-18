package bruzsal.betfair.entities;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

public class Event {

    private String id;
    private String name;
    private String countryCode;
    private String timezone;
    private String venue;
    private Date openDate;


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

    public LocalDateTime getOpenDate() {
        return new Timestamp(openDate.getTime()).toLocalDateTime();
    }

    @Override
    public String toString() {
        return "Event" + '\n' +
                "    " + name + '\n' +
                "    countryCode = " + countryCode + '\n' +
                "    id = " + id + '\n' +
                "    timezone = " + timezone + '\n' +
                "    openDate = " + getOpenDate().toString().replace('T', ' ');
    }
}
