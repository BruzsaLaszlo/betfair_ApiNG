package bruzsal.betfair.entities;

import java.util.List;

public class ClearedOrderSummaryReport {
    public List<ClearedOrderSummary> getClearedOrders() {
        return clearedOrders;
    }

    public void setClearedOrders(List<ClearedOrderSummary> clearedOrders) {
        this.clearedOrders = clearedOrders;
    }

    public boolean isMoreAvailable() {
        return moreAvailable;
    }

    public void setMoreAvailable(boolean moreAvailable) {
        this.moreAvailable = moreAvailable;
    }

    private boolean moreAvailable;
    private List<ClearedOrderSummary> clearedOrders;
}

