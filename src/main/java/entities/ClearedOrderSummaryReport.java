package entities;

import java.util.List;

public record ClearedOrderSummaryReport(

        Boolean moreAvailable,

        List<ClearedOrderSummary> clearedOrders

) {
}

