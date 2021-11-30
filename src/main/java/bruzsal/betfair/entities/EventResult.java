package bruzsal.betfair.entities;

public record EventResult(

        Event event,
        int marketCount

) {

    @Override
    public String toString() {
        return event + "\n" +
                "    marketCount = " + marketCount;
    }

}
