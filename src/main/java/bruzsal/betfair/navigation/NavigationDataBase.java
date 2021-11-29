package bruzsal.betfair.navigation;

import java.util.ArrayList;
import java.util.List;

public interface NavigationDataBase {

    List<Market> MARKETS = new ArrayList<>(25_000);
    List<Event> EVENTS = new ArrayList<>(2_000);
    List<EventType> EVENT_TYPES = new ArrayList<>(50);
    List<Group> GROUPS = new ArrayList<>(600);
    List<Race> RACES = new ArrayList<>(600);

    static void clearLists() {
        EVENTS.clear();
        MARKETS.clear();
        EVENT_TYPES.clear();
        GROUPS.clear();
        RACES.clear();
    }

    static String getSizeOfLists() {
        return new StringBuilder()
                .append("allEvent: ").append(EVENTS.size()).append("\n")
                .append("allEventType: ").append(EVENT_TYPES.size()).append("\n")
                .append("allGroups: ").append(GROUPS.size()).append("\n")
                .append("allRace: ").append(RACES.size()).append("\n")
                .append("allMarket: ").append(MARKETS.size()).append("\n")
                .toString();
    }
}
