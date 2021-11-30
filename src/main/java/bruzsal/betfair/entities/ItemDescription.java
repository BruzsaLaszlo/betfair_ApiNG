package bruzsal.betfair.entities;

import java.util.Date;

public record ItemDescription(

        String eventTypeDesc,
        String eventDesc,
        String marketDesc,
        Date marketStartTime,
        String runnerDesc,
        Integer numberOfWinners

) {
}

