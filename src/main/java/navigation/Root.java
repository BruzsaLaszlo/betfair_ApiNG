package navigation;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public final class Root extends Node {

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
