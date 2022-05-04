package entities;

import java.time.LocalDateTime;
import java.util.List;

public record MarketCatalogue(

        String marketId,
        String marketName,
        LocalDateTime marketStartTime,
        MarketDescription description,
        Double totalMatched,
        List<RunnerCatalog> runners,
        EventType eventType,
        Competition competition,
        Event event

) {

    @Override
    public String toString() {
        return "MarketCatalogue{" + '\n' +
                "marketId = " + marketId + '\n' +
                "marketName = " + marketName + '\n' +
                "description = " + description + '\n' +
                "totalMatched = " + totalMatched + '\n' +
                "runners = " + runners + '\n' +
                "eventType = " + eventType + '\n' +
                "competition = " + competition + '\n' +
                "event = " + event + '\n' +
                '}';
    }
}
