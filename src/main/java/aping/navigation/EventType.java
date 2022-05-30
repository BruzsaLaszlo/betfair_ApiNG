package aping.navigation;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class EventType extends Node {

    public EventType(int depth, String id, String name) {
        super(depth, id, name);
    }

    private final List<Group> groups = new ArrayList<>();

    private final List<Event> events = new ArrayList<>();

    private final List<Race> races = new ArrayList<>();

    public String toString() {
        return "{EventType" + "" + "id=" + getId() + "," + "name=" + getName() + "}";
    }

}
