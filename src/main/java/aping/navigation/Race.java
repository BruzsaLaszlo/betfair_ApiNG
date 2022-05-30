package aping.navigation;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class Race extends Node {

    private final String venue;
    private final LocalDateTime startTime;
    private final String raceNumber;
    private final String countryCode;

    private final List<Market> markets = new ArrayList<>();

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
