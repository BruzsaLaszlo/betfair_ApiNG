package bruzsal.betfair.entities;


public record MarketVersion (

    /**
     * A non-monotonically increasing number indicating market changes
     */
     long version

){
}
