package bruzsal.betfair.entities;

public record EventTypeResult (

        EventType eventType,

        int marketCount

){

    @Override
    public String toString() {
        return "EventTypeResult" + '\n' +
                eventType + '\n' +
                "    marketCount = " + marketCount;
    }
}
