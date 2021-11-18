package bruzsal.betfair.entities;

public class TimeRangeResult {

    private TimeRange timeRange;

    private int marketCount;


    public TimeRange getTimeRange() {
        return timeRange;
    }

    public int getMarketCount() {
        return marketCount;
    }


    @Override
    public String toString() {
        return "TimeRangeResult" + '\n'+
                timeRange + '\n'+
                "    marketCount = " + marketCount;
    }
}
