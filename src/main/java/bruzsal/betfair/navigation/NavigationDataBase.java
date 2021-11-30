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
        MARKETS.clear();
        EVENTS.clear();
        EVENT_TYPES.clear();
        GROUPS.clear();
        RACES.clear();
    }

    static String getSizeOfLists() {
        return "allEvent: " + EVENTS.size() + "\n" +
                "allEventType: " + EVENT_TYPES.size() + "\n" +
                "allGroups: " + GROUPS.size() + "\n" +
                "allRace: " + RACES.size() + "\n" +
                "allMarket: " + MARKETS.size() + "\n";
    }
}
