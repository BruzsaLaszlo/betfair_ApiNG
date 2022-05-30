package aping.navigation.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "markets")
@NoArgsConstructor
@Getter
public class Market extends Node {

    private LocalDateTime marketStartTime;

    private String marketType;

    private String numberOfWinners;


    public Market(int depth, String id, String name, LocalDateTime marketStartTime, String marketType, String numberOfWinners) {
        super(depth, id, name);
        this.marketStartTime = marketStartTime;
        this.marketType = marketType;
        this.numberOfWinners = numberOfWinners;
    }


    @Override
    public String toString() {
        String dateTime = marketStartTime.format(DateTimeFormatter.ofPattern("yyyy.MM.dd  HH:mm"));
        return String.format(
                "{ Market }      %-22s %-35s id=%-17s NumOfWin=%-10s   ",
                dateTime, name, id, numberOfWinners);
    }


}
