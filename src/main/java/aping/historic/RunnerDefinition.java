package aping.historic;

import aping.enums.RunnerStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "historic_runner")
@NoArgsConstructor
@Getter
public class RunnerDefinition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "db_id")
    private Long dbId;

    /**
     * The selctionId of the runner
     */
    private Integer id;

    /**
     * The status of the selection (i.e., ACTIVE, REMOVED, WINNER,
     * PLACED, LOSER, HIDDEN)
     */
    @Enumerated(EnumType.STRING)
    private RunnerStatus status;

    /**
     * The sort priority of this runner
     */
    private Integer sortPriority;

    /**
     * The Betfair Starting Price of this runner
     */
    private Double bsp;

    /**
     * Date and time the runner was removed
     */
    private String removalDate;

    /**
     * The name of the runner
     */
    private String name;

    /**
     * handicap - The handicap of the runner
     */
    private Double hc;

    /**
     * The adjustment factor applied if the selection is removed
     */
    private Double adjustmentFactor;

}