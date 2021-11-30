package bruzsal.betfair.entities;

public record MarketLineRangeInfo(

        Double maxUnitValue,
        Double minUnitValue,
        Double interval,
        String marketUnit

) {
}
