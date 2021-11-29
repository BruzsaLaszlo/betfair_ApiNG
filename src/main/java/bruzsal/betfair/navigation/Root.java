package bruzsal.betfair.navigation;

import java.util.ArrayList;
import java.util.List;

public final class Root extends Child {

    public Root() {
        super("0", "ROOT");
    }

    private List<EventType> eventTypes = new ArrayList<>();

    @Override

    List<List<? extends Child>> getLists() {
        return List.of(eventTypes);
    }

    public List<EventType> getEventTypes() {
        return eventTypes;
    }

    @Override
    public String toString() {
        return "Root{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                '}';
    }

}
