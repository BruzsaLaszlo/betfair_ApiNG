package bruzsal.betfair.entities;

import java.util.List;

public class CurrentOrderSummaryReport {


    private List<CurrentOrderSummary> currentOrders;
    private boolean moreAvailable;


    public List<CurrentOrderSummary> getCurrentOrders() {
        return currentOrders;
    }

    public boolean isMoreAvailable() {
        return moreAvailable;
    }

    @Override
    public String toString() {
        return "CurrentOrderSummaryReport" + '\n' +
                ">>> moreAvailable=" + moreAvailable +'\n' +
                ">>>" + currentOrders;
    }
}
