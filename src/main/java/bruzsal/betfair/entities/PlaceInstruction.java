package bruzsal.betfair.entities;

import bruzsal.betfair.enums.OrderType;
import bruzsal.betfair.enums.Side;

public class PlaceInstruction {

    private OrderType orderType;

    private Long selectionId;

    private Double handicap;

    private Side side;

    private LimitOrder limitOrder;

    private LimitOnCloseOrder limitOnCloseOrder;

    private MarketOnCloseOrder marketOnCloseOrder;

    private String customerOrderRef;


    public PlaceInstruction validate() {
        if (orderType == null || selectionId == null || side == null)
            throw new IllegalStateException("a kötelezö paraméterk hiányoznak");
        return this;
    }

    public PlaceInstruction setOrderType(OrderType orderType) {
        this.orderType = orderType;
        return this;
    }

    /**
     * The selection_id.
     */
    public PlaceInstruction setSelectionId(Long selectionId) {
        this.selectionId = selectionId;
        return this;
    }

    /**
     * The handicap associated with the runner in case of Asian handicap markets
     * (e.g. marketTypes ASIAN_HANDICAP_DOUBLE_LINE, ASIAN_HANDICAP_SINGLE_LINE) null otherwise.
     */
    public PlaceInstruction setHandicap(double handicap) {
        this.handicap = handicap;
        return this;
    }

    /**
     * Back or Lay
     */
    public PlaceInstruction setSide(Side side) {
        this.side = side;
        return this;
    }

    /**
     * A simple exchange bet for immediate execution
     */
    public PlaceInstruction setLimitOrder(LimitOrder limitOrder) {
        this.limitOrder = limitOrder;
        return this;
    }

    /**
     * Bets are matched if, and only if, the returned starting price is better than a specified price.
     * In the case of back bets, LOC bets are matched if the calculated starting price is greater than the
     * specified price. In the case of lay bets, LOC bets are matched if the starting price is less than the
     * specified price. If the specified limit is equal to the starting price, then it may be matched,
     * partially matched, or may not be matched at all, depending on how much is needed to balance all bets
     * against each other (MOC, LOC and normal exchange bets)
     */
    public PlaceInstruction setLimitOnCloseOrder(LimitOnCloseOrder limitOnCloseOrder) {
        this.limitOnCloseOrder = limitOnCloseOrder;
        return this;
    }

    /**
     * Bets remain unmatched until the market is reconciled.
     * They are matched and settled at a price that is representative of the market at the point the market is
     * turned in-play. The market is reconciled to find a starting price and MOC bets are settled at whatever
     * starting price is returned. MOC bets are always matched and settled, unless a starting price is not available
     * for the selection. Market on Close bets can only be placed before the starting price is determined
     */
    public PlaceInstruction setMarketOnCloseOrder(MarketOnCloseOrder marketOnCloseOrder) {
        this.marketOnCloseOrder = marketOnCloseOrder;
        return this;
    }

    /**
     * An optional reference customers can set to identify instructions.
     * No validation will be done on uniqueness and the string is limited
     * to 32 characters. If an empty string is provided it will be treated as null.
     */
    public PlaceInstruction setCustomerOrderRef(String customerOrderRef) {
        this.customerOrderRef = customerOrderRef;
        return this;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public Long getSelectionId() {
        return selectionId;
    }

    public Double getHandicap() {
        return handicap;
    }

    public Side getSide() {
        return side;
    }

    public LimitOrder getLimitOrder() {
        return limitOrder;
    }

    public LimitOnCloseOrder getLimitOnCloseOrder() {
        return limitOnCloseOrder;
    }

    public MarketOnCloseOrder getMarketOnCloseOrder() {
        return marketOnCloseOrder;
    }

    public String getCustomerOrderRef() {
        return customerOrderRef;
    }
}
