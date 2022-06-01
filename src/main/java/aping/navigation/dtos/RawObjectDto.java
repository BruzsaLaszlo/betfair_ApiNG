package aping.navigation.dtos;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public record RawObjectDto(

        String id,
        String type,
        String name,
        String exchangeId,
        String marketType,
        LocalDateTime marketStartTime,
        String numberOfWinners,
        String countryCode,
        String venue,
        LocalDateTime startTime,
        String raceNumber,
        List<RawObjectDto> children

) implements Serializable {
}
