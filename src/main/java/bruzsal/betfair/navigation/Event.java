package bruzsal.betfair.navigation;

import java.util.ArrayList;
import java.util.List;

public class Event extends Child {

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
