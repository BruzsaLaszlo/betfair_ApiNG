package bruzsal.betfair.navigation;

import java.util.ArrayList;
import java.util.List;

public class Group extends Child {

    private final List<Event> events = new ArrayList<>();

    private final List<Group> groups = new ArrayList<>();

    public Group(int depth, String id, String name) {
        super(depth, id, name);
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
