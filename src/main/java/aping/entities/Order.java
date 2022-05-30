package aping.entities;

import java.time.LocalDateTime;

public record Order(

        String betId,
        String orderType,
        String status,
        String persistenceType,
        String side,
        Double price,
        Double size,
        Double bspLiability,
        LocalDateTime placedDate,
        Double avgPriceMatched,
        Double sizeMatched,
        Double sizeRemaining,
        Double sizeLapsed,
        Double sizeCancelled,
        Double sizeVoided

) {

    @Override
    public String toString() {
        return "  Order{" +
                "    betId='" + betId + '\n' +
                "    orderType='" + orderType + '\n' +
                "    status='" + status + '\n' +
                "    persistenceType='" + persistenceType + '\n' +
                "    side='" + side + '\n' +
                "    price=" + price + '\n' +
                "    size=" + size + '\n' +
                "    bspLiability=" + bspLiability + '\n' +
                "    placedDate=" + placedDate + '\n' +
                "    avgPriceMatched=" + avgPriceMatched + '\n' +
                "    sizeMatched=" + sizeMatched + '\n' +
                "    sizeRemaining=" + sizeRemaining + '\n' +
                "    sizeLapsed=" + sizeLapsed + '\n' +
                "    sizeCancelled=" + sizeCancelled + '\n' +
                "    sizeVoided=" + sizeVoided + '\n' +
                "  } order";
    }
}
