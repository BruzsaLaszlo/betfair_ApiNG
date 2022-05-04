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
@Table(name = "event_types")
@NoArgsConstructor
@Getter
public class EventType extends Node {

    public EventType(int depth, String id, String name) {
        super(depth, id, name);
    }

    @OneToMany//(mappedBy = "parentEventType")
    @JoinColumn(name = "event_type_id")
    private final List<Group> groups = new ArrayList<>();

    @OneToMany//(mappedBy = "parentEventType")
    @JoinColumn(name = "event_type_id")
    private final List<Event> events = new ArrayList<>();

    @OneToMany//(mappedBy = "parentEventType")
    @JoinColumn(name = "event_type_id")
    private final List<Race> races = new ArrayList<>();


    public String toString() {
        return "{EventType" + "" + "id=" + getId() + "," + "name=" + getName() + "}";
    }

}
