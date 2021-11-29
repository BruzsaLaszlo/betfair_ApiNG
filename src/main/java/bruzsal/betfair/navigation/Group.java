package bruzsal.betfair.navigation;

import java.util.ArrayList;
import java.util.List;

public class Group extends Child {

    private final List<Event> events = new ArrayList<>();

    private final List<Group> groups = new ArrayList<>();

    public Group(String id, String name) {
        super(id, name);
    }

    @Override
    List<List<? extends Child>> getLists() {
        return List.of(events, groups);
    }

    @Override
    public String toString() {
        return "Group{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    public List<Event> getEvents() {
        return events;
    }

    public List<Group> getGroups() {
        return groups;
    }
}
