package bruzsal.betfair.entities;

public record EventType (

        String id,

        String name

) {

    @Override
    public String toString() {
        return "    name = " + name + '\n' +
                "    id = " + id;
    }
}
