package bruzsal.betfair.entities;

import java.util.List;

public record ClearedOrderSummaryReport(

        boolean moreAvailable,

        List<ClearedOrderSummary> clearedOrders

) {
}

