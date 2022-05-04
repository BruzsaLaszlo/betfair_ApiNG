package historic;

import java.time.LocalDateTime;
import java.util.List;

public class MarketDefinition {

    private boolean bspMarket;
    private boolean turnInPlayEnabled;
    private boolean persistenceEnabled;
    private double marketBaseRate;
    private String eventId;
    private String eventTypeId;
    private int numberOfWinners;
    private String bettingType;
    private String marketType;
    private LocalDateTime marketTime;
    private LocalDateTime suspendTime;
    private boolean bspReconciled;
    private boolean complete;
    private boolean inPlay;
    private boolean crossMatching;
    private boolean runnersVoidable;
    private int numberOfActiveRunners;
    private int betDelay;
    private String status;
    private List<Runner> runners;
    private List<String> regulators;
    private String countryCode;
    private boolean discountAllowed;
    private String timezone;
    private LocalDateTime openDate;
    private Object version;
    private String name;
    private String eventName;
    private LocalDateTime settledTime;

}