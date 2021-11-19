package bruzsal.betfair.entities;

import bruzsal.betfair.enums.OrderType;
import bruzsal.betfair.enums.PersistenceType;
import bruzsal.betfair.enums.Side;

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

    public Date getPlacedDate() {
        return placedDate;
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

    public Date getSettledDate() {
        return settledDate;
    }

    public Date getLastMatchedDate() {
        return lastMatchedDate;
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

    
