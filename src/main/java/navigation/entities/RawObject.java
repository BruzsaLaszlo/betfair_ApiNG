package navigation.entities;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "navigation_data")
@Getter
public class RawObject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dbId;

    @Column(name = "id")
    private String id;

    @Column(name = "type")
    private String type;

    @Column(name = "name")
    private String name;

    @Deprecated(since = "many years")
    private String exchangeId;

    @Column(name = "market_type")
    private String marketType;

    @Column(name = "market_start_time")
    private LocalDateTime marketStartTime;

    @Column(name = "number_of_winners")
    private String numberOfWinners;

    @Column(name = "country_code")
    private String countryCode;

    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "parent_id")
    private List<RawObject> children;

    @Column(name = "venue")
    private String venue;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "race_number")
    private String raceNumber;

}
