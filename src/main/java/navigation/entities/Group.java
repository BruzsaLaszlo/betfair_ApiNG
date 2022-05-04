package navigation.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "groups")
@NoArgsConstructor
@Getter
public class Group extends Node {

//    @ManyToOne
//    @JoinColumn(name = "parent_event_type")
//    private EventType parentEventType;
//
//    @ManyToOne
//    @JoinColumn(name = "parent_group")
//    private Group parentGroup;
//
//    @ManyToOne
//    @JoinColumn(name ="parent_event")
//    private Event parentEvent;

    @OneToMany
    @JoinColumn(name = "group_id")
    private final List<Event> events = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "group_id")
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
