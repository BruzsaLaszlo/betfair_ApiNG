package bruzsal.betfair.historic;

import java.util.Date;
import java.util.List;

public class MarketDefinition {

    public boolean bspMarket;
    public boolean turnInPlayEnabled;
    public boolean persistenceEnabled;
    public double marketBaseRate;
    public String eventId;
    public String eventTypeId;
    public int numberOfWinners;
    public String bettingType;
    public String marketType;
    public Date marketTime;
    public Date suspendTime;
    public boolean bspReconciled;
    public boolean complete;
    public boolean inPlay;
    public boolean crossMatching;
    public boolean runnersVoidable;
    public int numberOfActiveRunners;
    public int betDelay;
    public String status;
    public List<Runner> runners;
    public List<String> regulators;
    public String countryCode;
    public boolean discountAllowed;
    public String timezone;
    public Date openDate;
    public Object version;
    public String name;
    public String eventName;
    public Date settledTime;

}