package aping.entities;

public record EventResult(

        Event event,
        Integer marketCount

) {

    @Override
    public String toString() {
        return event + "\n" +
                "    marketCount = " + marketCount;
    }

}
