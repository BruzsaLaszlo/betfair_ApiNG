package aping.entities;

import java.util.List;

public record CurrentOrderSummaryReport(

        List<CurrentOrderSummary> currentOrders,

        Boolean moreAvailable

) {

    @Override
    public String toString() {
        return "CurrentOrderSummaryReport" + '\n' +
                ">>> moreAvailable=" + moreAvailable + '\n' +
                ">>>" + currentOrders;
    }

}
