package aping.navigation.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "races")
@NoArgsConstructor
@Getter
public class Race extends Node {

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
