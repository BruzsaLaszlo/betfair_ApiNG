package bruzsal.betfair.entities;

import bruzsal.betfair.enums.OrderStatus;
import bruzsal.betfair.enums.OrderType;
import bruzsal.betfair.enums.PersistenceType;
import bruzsal.betfair.enums.Side;

import java.util.Date;

public class CurrentOrderSummary {

    private String betId;
    private String marketId;
    private long selectionId;
    private double handicap;
    private PriceSize priceSize;
    private double bspLiability;
    private Side side;
    private OrderStatus status;
    private PersistenceType persistenceType;
    private OrderType orderType;
    private Date placedDate;
    private Date matchedDate;
    private double averagePriceMatched;
    private double sizeMatched;
    private double sizeRemaining;
    private double sizeLapsed;
    private double sizeCancelled;
    private double sizeVoided;
    private String regulatorAuthCode;
    private String regulatorCode;
    private String customerOrderRef;
    private String customerStrategyRef;
    private CurrentItemDescription currentItemDescription;


    @Override
    public String toString() {
        return "CurrentOrderSummary" + '\n' +
                "    betId = " + betId + '\n' +
                "    marketId = " + marketId + '\n' +
                "    selectionId = " + selectionId + '\n' +
                "    handicap = " + handicap + '\n' +
                "    priceSize = " + priceSize + '\n' +
                "    bspLiability = " + bspLiability + '\n' +
                "    side = " + side + '\n' +
                "    status = " + status + '\n' +
                "    persistenceType = " + persistenceType + '\n' +
                "    orderType = " + orderType + '\n' +
                "    placedDate = " + placedDate + '\n' +
                "    matchedDate = " + matchedDate + '\n' +
                "    averagePriceMatched = " + averagePriceMatched + '\n' +
                "    sizeMatched = " + sizeMatched + '\n' +
                "    sizeRemaining = " + sizeRemaining + '\n' +
                "    sizeLapsed = " + sizeLapsed + '\n' +
                "    sizeCancelled = " + sizeCancelled + '\n' +
                "    sizeVoided = " + sizeVoided + '\n' +
                "    regulatorAuthCode = " + regulatorAuthCode + '\n' +
                "    regulatorCode = " + regulatorCode + '\n' +
                "    customerOrderRef = " + customerOrderRef + '\n' +
                "    customerStrategyRef = " + customerStrategyRef + '\n' +
                "    currentItemDescription = " + currentItemDescription;
    }

    public String getBetId() {
        return betId;
    }

    public String getMarketId() {
        return marketId;
    }

    public long getSelectionId() {
        return selectionId;
    }

    public double getHandicap() {
        return handicap;
    }

    public PriceSize getPriceSize() {
        return priceSize;
    }

    public double getBspLiability() {
        return bspLiability;
    }

    public Side getSide() {
        return side;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public PersistenceType getPersistenceType() {
        return persistenceType;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public Date getPlacedDate() {
        return placedDate;
    }

    public Date getMatchedDate() {
        return matchedDate;
    }

    public double getAveragePriceMatched() {
        return averagePriceMatched;
    }

    public double getSizeMatched() {
        return sizeMatched;
    }

    public double getSizeRemaining() {
        return sizeRemaining;
    }

    public double getSizeLapsed() {
        return sizeLapsed;
    }

    public double getSizeCancelled() {
        return sizeCancelled;
    }

    public double getSizeVoided() {
        return sizeVoided;
    }

    public String getRegulatorAuthCode() {
        return regulatorAuthCode;
    }

    public String getRegulatorCode() {
        return regulatorCode;
    }

    public String getCustomerOrderRef() {
        return customerOrderRef;
    }

    public String getCustomerStrategyRef() {
        return customerStrategyRef;
    }

    public CurrentItemDescription getCurrentItemDescription() {
        return currentItemDescription;
    }
}
