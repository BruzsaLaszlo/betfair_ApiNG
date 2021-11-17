package bruzsal.betfair.entities;


import java.util.Date;

public class MarketDescription {

    private Boolean persistenceEnabled;
    private Boolean bspMarket;
    private Date marketTime;
    private Date suspendTime;
    private Date settleTime;
    private String bettingType;
    private Boolean turnInPlayEnabled;
    private String marketType;
    private String regulator;
    private Double marketBaseRate;
    private Boolean discountAllowed;
    private String wallet;
    private String rules;
    private Boolean rulesHasDate;
    private String clarifications;


    public Boolean getPersistenceEnabled() {
        return persistenceEnabled;
    }


    public Boolean getBspMarket() {
        return bspMarket;
    }


    public Date getMarketTime() {
        return marketTime;
    }


    public Date getSuspendTime() {
        return suspendTime;
    }


    public Date getSettleTime() {
        return settleTime;
    }


    public String getBettingType() {
        return bettingType;
    }


    public Boolean getTurnInPlayEnabled() {
        return turnInPlayEnabled;
    }


    public String getMarketType() {
        return marketType;
    }


    public String getRegulator() {
        return regulator;
    }


    public Double getMarketBaseRate() {
        return marketBaseRate;
    }


    public Boolean getDiscountAllowed() {
        return discountAllowed;
    }


    public String getWallet() {
        return wallet;
    }


    public String getRules() {
        return rules;
    }


    public Boolean getRulesHasDate() {
        return rulesHasDate;
    }


    public String getClarifications() {
        return clarifications;
    }


    @Override
    public String toString() {
        return " ### MarketDescription ###{" + '\n' +
                "persistenceEnabled=" + persistenceEnabled + '\n' +
                "bspMarket=" + bspMarket + '\n' +
                "marketTime=" + marketTime + '\n' +
                "suspendTime=" + suspendTime + '\n' +
                "settleTime=" + settleTime + '\n' +
                "bettingType='" + bettingType + '\'' + '\n' +
                "turnInPlayEnabled=" + turnInPlayEnabled + '\n' +
                "marketType='" + marketType + '\'' + '\n' +
                "marketBaseRate=" + marketBaseRate + '\n' +
                "discountAllowed=" + discountAllowed + '\n' +
                "wallet='" + wallet + '\'' + '\n' +
                "rulesHasDate=" + rulesHasDate + '\n' +
                "clarifications='" + clarifications + '\'' + '\n' +
                "} ### END ###";
    }
}
