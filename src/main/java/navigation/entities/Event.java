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
@Table(name = "events")
@NoArgsConstructor
@Getter
public class Event extends Node {

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

    private String countryCode;

    @OneToMany//(mappedBy = "parentEvent")
    @JoinColumn(name = "event_id")
    private final List<Market> markets = new ArrayList<>();

    @OneToMany//(mappedBy = "parentEvent")
    @JoinColumn(name = "event_id")
    private final List<Event> events = new ArrayList<>();

    @OneToMany//(mappedBy = "parentEvent")
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
