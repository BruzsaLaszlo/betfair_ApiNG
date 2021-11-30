package bruzsal.betfair.entities;

import java.util.Date;

public record StatementLegacyData(

        Double averagePrice,
        Double betSize,
        String betType,
        String betCategoryType,
        String commissionRate,
        Long eventId,
        Long eventTypeId,
        String fullMarketName,
        Double grossBetAmount,
        String marketName,
        String marketType,
        Date placedDate,
        Long selectionId,
        String selectionName,
        Date startDate,
        String transactionType,
        Long transactionId,
        String winLose

) {
}
