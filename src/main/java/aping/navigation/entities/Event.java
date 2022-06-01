package aping.navigation.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "node_events")
@NoArgsConstructor
@Getter
public class Event extends Node {

    private String countryCode;

    @OneToMany(cascade = CascadeType.ALL)//(mappedBy = "parentEvent")
    @JoinColumn(name = "event_id")
    private final List<Market> markets = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)//(mappedBy = "parentEvent")
    @JoinColumn(name = "event_id")
    private final List<Event> events = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)//(mappedBy = "parentEvent")
    @JoinColumn(name = "event_id")
    private final List<Group> groups = new ArrayList<>();


    public Event(int depth, String id, String name, String countryCode) {
        super(depth, id, name);
        this.countryCode = countryCode;
    }

    @Override
    public String toString() {
        return "\n" + SPACES[depth] + "Event{" +
               "id='" + id + '\'' +
               ", name='" + name + '\'' +
               ", countryCode='" + countryCode + '\'' +
               '}';
    }

}
