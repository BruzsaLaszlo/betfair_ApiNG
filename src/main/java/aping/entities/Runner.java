package aping.entities;

import java.time.LocalDateTime;
import java.util.List;

public record Runner(

        Long selectionId,
        Double handicap,
        String status,
        Double adjustmentFactor,
        Double lastPriceTraded,
        Double totalMatched,
        LocalDateTime removalDate,
        StartingPrices sp,
        ExchangePrices ex,
        List<Order> orders,
        List<Match> matches

) {

    @Override
    public String toString() {
        return "  Runner{" +
                "    selectionId = " + selectionId + '\n' +
                "    handicap = " + handicap + '\n' +
                "    status = " + status + '\n' +
                "    adjustmentFactor = " + adjustmentFactor + '\n' +
                "    lastPriceTraded = " + lastPriceTraded + '\n' +
                "    totalMatched = " + totalMatched + '\n' +
                "    removalDate = " + removalDate + '\n' +
                "    sp = " + sp + '\n' +
                "    ex = " + ex + '\n' +
                "    orders = " + orders + '\n' +
                "    matches = " + matches + '\n' +
                '}';
    }
}
