package bruzsal.betfair.navigation;

import java.util.ArrayList;
import java.util.List;

public class EventType extends Child {

    public EventType(int depth, String id, String name) {
        super(depth, id, name);
    }

    private final List<Group> groups = new ArrayList<>();

    private final List<Event> events = new ArrayList<>();

    private final List<Race> races = new ArrayList<>();

    public List<Group> getGroups() {
        return groups;
    }

    public List<Event> getEvents() {
        return events;
    }

    public List<Race> getRaces() {
        return races;
    }

    public String toString() {
        return "{EventType" + "" + "id=" + getId() + "," + "name=" + getName() + "}";
    }

}
