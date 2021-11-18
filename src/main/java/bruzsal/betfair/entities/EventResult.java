package bruzsal.betfair.entities;

public class EventResult {


    private Event event;

    private int marketCount;


    public Event getEvent() {
        return event;
    }

    public int getMarketCount() {
        return marketCount;
    }


    @Override
    public String toString() {
        return event + "\n" +
                "    marketCount = " + marketCount;
    }
}
