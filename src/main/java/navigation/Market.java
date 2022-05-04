package navigation;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public class Market extends Node {

    private final LocalDateTime martketStartTime;

    private final String marketType;

    private final String numberOfWinners;

    @Setter
    private Event event;

    public Market(int depth, String id, String name, LocalDateTime martketStartTime, String marketType, String numberOfWinners) {
        super(depth, id, name);
        this.martketStartTime = martketStartTime;
        this.marketType = marketType;
        this.numberOfWinners = numberOfWinners;
    }


    @Override
    public String toString() {
        String dateTime = martketStartTime.format(DateTimeFormatter.ofPattern("yyyy.MM.dd  HH:mm"));
        return String.format(
                "{ Market }      %-22s %-35s id=%-17s NumOfWin=%-10s   ",
                dateTime, name, id, numberOfWinners);
    }

}
