package bruzsal.betfair.entities;

import bruzsal.betfair.enums.OrderType;
import bruzsal.betfair.enums.PersistenceType;
import bruzsal.betfair.enums.Side;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

public class ClearedOrderSummary {


    private String eventTypeId;
    private String eventId;
    private String marketId;
    private long selectionId;
    private double handicap;
    private String betId;
    private Date placedDate;
    private PersistenceType persistenceType;
    private OrderType orderType;
    private Side side;
    private ItemDescription itemDescription;
    private String betOutcome;
    private double priceRequested;
    private Date settledDate;
    private Date lastMatchedDate;
    private int betCount;
    private double commission;
    private double priceMatched;
    private boolean priceReduced;
    private double sizeSettled;
    private double profit;
    private double sizeCancelled;
    private String customerOrderRef;
    private String customerStrategyRef;

    @Override
    public String toString() {
        return "ClearedOrderSummary" + '\n' +
                "    eventTypeId = " + eventTypeId + '\n' +
                "    eventId = " + eventId + '\n' +
                "    marketId = " + marketId + '\n' +
                "    selectionId = " + selectionId + '\n' +
                "    handicap = " + handicap + '\n' +
                "    betId = " + betId + '\n' +
                "    placedDate = " + placedDate + '\n' +
                "    persistenceType = " + persistenceType + '\n' +
                "    orderType = " + orderType + '\n' +
                "    side = " + side + '\n' +
                "    itemDescription = " + itemDescription + '\n' +
                "    betOutcome = " + betOutcome + '\n' +
                "    priceRequested = " + priceRequested + '\n' +
                "    settledDate = " + settledDate + '\n' +
                "    lastMatchedDate = " + lastMatchedDate + '\n' +
                "    betCount = " + betCount + '\n' +
                "    commission = " + commission + '\n' +
                "    priceMatched = " + priceMatched + '\n' +
                "    priceReduced = " + priceReduced + '\n' +
                "    sizeSettled = " + sizeSettled + '\n' +
                "    profit = " + profit + '\n' +
                "    sizeCancelled = " + sizeCancelled + '\n' +
                "    customerOrderRef = " + customerOrderRef + '\n' +
                "    customerStrategyRef = " + customerStrategyRef;
    }

    public String getEventTypeId() {
        return eventTypeId;
    }

    public String getEventId() {
        return eventId;
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

    public String getBetId() {
        return betId;
    }

    public LocalDateTime getPlacedDate() {
        return new Timestamp(placedDate.getTime()).toLocalDateTime();
    }

    public PersistenceType getPersistenceType() {
        return persistenceType;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public Side getSide() {
        return side;
    }

    public ItemDescription getItemDescription() {
        return itemDescription;
    }

    public String getBetOutcome() {
        return betOutcome;
    }

    public double getPriceRequested() {
        return priceRequested;
    }

    public LocalDateTime getSettledDate() {
        return new Timestamp(settledDate.getTime()).toLocalDateTime();
    }

    public LocalDateTime getLastMatchedDate() {
        return new Timestamp(lastMatchedDate.getTime()).toLocalDateTime();
    }

    public int getBetCount() {
        return betCount;
    }

    public double getCommission() {
        return commission;
    }

    public double getPriceMatched() {
        return priceMatched;
    }

    public boolean isPriceReduced() {
        return priceReduced;
    }

    public double getSizeSettled() {
        return sizeSettled;
    }

    public double getProfit() {
        return profit;
    }

    public double getSizeCancelled() {
        return sizeCancelled;
    }

    public String getCustomerOrderRef() {
        return customerOrderRef;
    }

    public String getCustomerStrategyRef() {
        return customerStrategyRef;
    }
}

    
