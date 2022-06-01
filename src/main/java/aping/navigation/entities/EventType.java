package aping.navigation.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "node_event_types")
@NoArgsConstructor
@Getter
public class EventType extends Node {

    public EventType(int depth, String id, String name) {
        super(depth, id, name);
    }

    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)//(mappedBy = "parentEventType")
    @JoinColumn(name = "event_type_id")
    private final List<Group> groups = new ArrayList<>();

    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)//(mappedBy = "parentEventType")
    @JoinColumn(name = "event_type_id")
    private final List<Event> events = new ArrayList<>();

    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)//(mappedBy = "parentEventType")
    @JoinColumn(name = "event_type_id")
    private final List<Race> races = new ArrayList<>();


    public String toString() {
        return "{EventType" + "" + "id=" + getId() + "," + "name=" + getName() + "}";
    }

}
