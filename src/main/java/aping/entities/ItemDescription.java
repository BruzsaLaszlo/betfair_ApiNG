package aping.entities;

import java.time.LocalDateTime;

public record ItemDescription(

        String eventTypeDesc,
        String eventDesc,
        String marketDesc,
        LocalDateTime marketStartTime,
        String runnerDesc,
        Integer numberOfWinners

) {
}

