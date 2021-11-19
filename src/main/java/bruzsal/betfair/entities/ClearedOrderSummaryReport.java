package bruzsal.betfair.entities;

import java.util.List;

public class ClearedOrderSummaryReport {

    private boolean moreAvailable;

    private List<ClearedOrderSummary> clearedOrders;


    public boolean isMoreAvailable() {
        return moreAvailable;
    }

    public List<ClearedOrderSummary> getClearedOrders() {
        return clearedOrders;
    }


}

