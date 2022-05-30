package aping.entities;

import java.time.LocalDateTime;

public record Event(

        String id,
        String name,
        String countryCode,
        String timezone,
        String venue,
        LocalDateTime openDate

) {


    @Override
    public String toString() {
        return "Event" + '\n' +
               "    " + name + '\n' +
               "    countryCode = " + countryCode + '\n' +
               "    id = " + id + '\n' +
               "    timezone = " + timezone + '\n' +
               "    openDate = " + openDate().toString().replace('T', ' ');
    }
}
