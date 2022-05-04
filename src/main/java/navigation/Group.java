package navigation;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Group extends Node {

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

}
