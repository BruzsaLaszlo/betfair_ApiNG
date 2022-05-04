package navigation.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "races")
@NoArgsConstructor
@Getter
public class Race extends Node {

//    @ManyToOne
//    @JoinColumn(name ="parent_event")
//    private Event parentEvent;
//
//    @ManyToOne
//    @JoinColumn(name = "parent_event_type")
//    private EventType parentEventType;

    @OneToMany//(mappedBy = "parentRace")
    @JoinColumn(name = "race_id")
    private final List<Market> markets = new ArrayList<>();

    private String venue;
    private LocalDateTime startTime;
    private String raceNumber;
    private String countryCode;


    public Race(int depth, String id, String name, String venue, LocalDateTime startTime, String raceNumber, String countryCode) {
        super(depth, id, name);
        this.venue = venue;
        this.startTime = startTime;
        this.raceNumber = raceNumber;
        this.countryCode = countryCode;
    }

    @Override
    public String toString() {
        return "Race{" +
               "id='" + id + '\'' +
               ", name='" + name + '\'' +
               ", venue='" + venue + '\'' +
               ", startTime=" + startTime +
               ", raceNumber='" + raceNumber + '\'' +
               ", countryCode='" + countryCode + '\'' +
               '}';
    }

}
