package bruzsal.betfair.entities;

public class MarketTypeResult {


    private String marketType;

    private int marketCount;


    public String getMarketType() {
        return marketType;
    }

    public int getMarketCount() {
        return marketCount;
    }


    @Override
    public String toString() {
        return String.format("%-20s %s", marketType, marketCount);
    }
}
