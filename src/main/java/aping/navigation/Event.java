package aping.navigation;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Event extends Node {

    private final String countryCode;

    private final List<Market> markets = new ArrayList<>();

    private final List<Event> events = new ArrayList<>();

    private final List<Group> groups = new ArrayList<>();

    public Event(int depth, String id, String name, String countryCode) {
        super(depth, id, name);
        this.countryCode = countryCode;
    }

    @Override
    public String toString() {
        return "\n" + SPACES[depth] + "Event{" +
               "id='" + id + '\'' +
               ", name='" + name + '\'' +
               ", countryCode='" + countryCode + '\'' +
               '}';
    }

}
