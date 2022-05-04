package enums;

public enum PriceLadderType {

    /**
     * Price ladder increments traditionally used for Odds Markets.
     */
    CLASSIC,

    /**
     * Price ladder with the finest available increment, traditionally used for
     * Asian Handicap markets.
     */
    FINEST,

    /**
     * Price ladder used for LINE markets. Refer to MarketLineRangeInfo for more details.
     */
    LINE_RANGE

}
