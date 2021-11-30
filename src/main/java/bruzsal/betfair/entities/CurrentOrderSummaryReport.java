package bruzsal.betfair.entities;

import java.util.List;

public record CurrentOrderSummaryReport(

        List<CurrentOrderSummary> currentOrders,
        boolean moreAvailable

) {

    @Override
    public String toString() {
        return "CurrentOrderSummaryReport" + '\n' +
                ">>> moreAvailable=" + moreAvailable + '\n' +
                ">>>" + currentOrders;
    }

}
