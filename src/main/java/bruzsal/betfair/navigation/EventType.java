package bruzsal.betfair.navigation;

import java.util.ArrayList;
import java.util.List;

public class EventType extends Child {

    public EventType(String id, String name) {
        super(id, name);
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

    @Override
    List<List<? extends Child>> getLists() {
        return List.of(events, groups);
    }

    public String toString() {
        return "{EventType" + "" + "id=" + getId() + "," + "name=" + getName() + "}";
    }

}
