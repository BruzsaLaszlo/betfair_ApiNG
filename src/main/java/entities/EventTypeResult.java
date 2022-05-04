package entities;

public record EventTypeResult(

        EventType eventType,

        Integer marketCount

) {

    @Override
    public String toString() {
        return String.format("%5s  %s", marketCount, eventType);
    }
}
