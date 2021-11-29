package bruzsal.betfair.navigation;

import java.util.ArrayList;
import java.util.List;

public class Event extends Child {

    private final String countryCode;

    private final List<Market> markets = new ArrayList<>();

    private final List<Event> events = new ArrayList<>();

    private final List<Group> groups = new ArrayList<>();

    public Event(String id, String name, String countryCode) {
        super(id, name);
        this.countryCode = countryCode;
    }

    @Override
    List<List<? extends Child>> getLists() {
        return List.of(markets, events, groups);
    }

    @Override
    public String toString() {
        return "\n" + NavigationData.SPACES[depth] + "Event{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", countryCode='" + countryCode + '\'' +
                '}';
    }

    public String getCountryCode() {
        return countryCode;
    }

    public List<Market> getMarkets() {
        return markets;
    }

    public List<Event> getEvents() {
        return events;
    }

    public List<Group> getGroups() {
        return groups;
    }
}
