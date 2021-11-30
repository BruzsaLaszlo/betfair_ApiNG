package bruzsal.betfair.entities;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

public record Event(

        String id,
        String name,
        String countryCode,
        String timezone,
        String venue,
        Date openDate

) {

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
