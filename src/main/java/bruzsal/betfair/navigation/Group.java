package bruzsal.betfair.navigation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Group extends NavigationData {

    private final List<Event> events = new ArrayList<>();

    private final List<Group> groups = new ArrayList<>();

    public Group(String id, String name) {
        super(id, name);
        allGroup.add(this);
    }

    @Override
    List<List<? extends NavigationData>> getLists() {
        return Arrays.asList(events, groups);
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
