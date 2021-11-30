package bruzsal.betfair.entities;

public record TimeRangeResult (

        TimeRange timeRange,

        int marketCount

){


    @Override
    public String toString() {
        return "TimeRangeResult" + '\n'+
                timeRange + '\n'+
                "    marketCount = " + marketCount;
    }
}
