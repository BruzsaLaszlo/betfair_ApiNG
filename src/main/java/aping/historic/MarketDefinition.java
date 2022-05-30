package aping.historic;

import aping.enums.MarketBettingType;
import aping.enums.MarketStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Fields containing details of the market - new
 * market definition is published if any of these field change
 */
@Entity
@Table(name = "historic_market_definition")
@NoArgsConstructor
@Getter
public class MarketDefinition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "db_id")
    private Long dbId;

    /**
     * If 'true' the market supports Betfair SP betting
     */
    private Boolean bspMarket;

    /**
     * If 'true' the market is set to turn in-play
     */
    private Boolean turnInPlayEnabled;

    /**
     * If 'true' the market supports 'Keep' bets if the market is to be
     * turned in-play
     */
    private Boolean persistenceEnabled;

    /**
     * The commission rate applicable to the market
     */
    private Double marketBaseRate;

    /**
     * The unique id for the event
     */
    private String eventId;

    /**
     * The unique eventTypeId that the event belongs to
     */
    private String eventTypeId;

    /**
     * The number of winners on a market
     */
    private Integer numberOfWinners;

    /**
     * The market betting type i.e.
     * ODDS,ASIAN_HANDICAP_Double_LINE,
     * ASIAN_HANDICAP_SINGLE_LINE
     */
    @Enumerated(EnumType.STRING)
    private MarketBettingType bettingType;

    /**
     * Market base type
     */
    private String marketType;

    /**
     * The market start time
     */
    private LocalDateTime marketTime;

    /**
     * The market suspend time
     */
    private LocalDateTime suspendTime;

    /**
     * True if the market starting price has been reconciled
     */
    private Boolean bspReconciled;

    /**
     * If false, runners may be added to the maket
     */
    private Boolean complete;

    /**
     * True if the market is currently in play
     */
    private Boolean inPlay;

    /**
     * True if cross matching is enabled for this market
     */
    private Boolean crossMatching;

    /**
     * True if runners in the market can be voided
     */
    private Boolean runnersVoidable;

    /**
     * The number of runners that are currently active. An active
     * runner is a selection available for betting
     */
    private Integer numberOfActiveRunners;

    /**
     * The number of seconds an order is held until it is submitted Integero
     * the market. Orders are usually delayed when the market is in play
     */
    private Integer betDelay;

    /**
     * The status of the market, for example OPEN, SUSPENDED,
     * CLOSED (settled), etc.
     */
    @Enumerated(EnumType.STRING)
    private MarketStatus status;

    /**
     *
     */
    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "market_definition_db_id")
    private List<aping.historic.RunnerDefinition> runners;

    /**
     * The market regulators
     */
    @ElementCollection
    @CollectionTable(name = "historic_regulators", joinColumns = @JoinColumn(name = "market_definition_db_id"))
    @Column(name = "regulator")
    private List<String> regulators;

    /**
     *
     */
    private String countryCode;

    /**
     * Indicate whether or not the users discount rate is taken Integero
     * account on this market
     */
    private Boolean discountAllowed;

    /**
     * This is the timezone in which the event is taking place
     */
    private String timezone;

    /**
     * The scheduled start date and time of the event. This is GMT by
     * default
     */
    private LocalDateTime openDate;

    /**
     * A non-monotonically increasing number indicating market
     * changes
     */
    private Long version;

    /**
     * The name of the market
     */
    private String name;

    /**
     * The name of the event
     */
    private String eventName;

    /**
     *
     */
    private LocalDateTime settledTime;

}