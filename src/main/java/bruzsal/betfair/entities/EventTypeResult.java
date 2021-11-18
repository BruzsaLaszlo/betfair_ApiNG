package bruzsal.betfair.entities;

public class EventTypeResult {


    private EventType eventType;

    private int marketCount;


    public EventType getEventType() {
        return eventType;
    }

    public int getMarketCount() {
        return marketCount;
    }


    @Override
    public String toString() {
        return "EventTypeResult" + '\n' +
                eventType + '\n' +
                "    marketCount = " + marketCount;
    }
}
