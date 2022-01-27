package bruzsal.betfair.navigation;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public final class Root extends Child {

    @Getter
    private final List<EventType> eventTypes = new ArrayList<>();

    public Root() {
        super(0, "0", "ROOT");
    }

    @Override
    public String toString() {
        return "Root{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                '}';
    }

}
