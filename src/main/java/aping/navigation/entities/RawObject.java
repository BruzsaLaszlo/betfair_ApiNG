package aping.navigation.entities;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "raw_navigation_data")
@Getter
public class RawObject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dbId;

    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "parent_id")
    private List<RawObject> children;

    private String id;
    private String type;
    private String name;
    private String marketType;
    private LocalDateTime marketStartTime;
    private String numberOfWinners;
    private String countryCode;
    private String venue;
    private LocalDateTime startTime;
    private String raceNumber;

    @Deprecated(since = "many years")
    @Transient
    private String exchangeId;

}
