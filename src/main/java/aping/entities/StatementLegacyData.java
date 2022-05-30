package aping.entities;

import java.time.LocalDateTime;

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
        LocalDateTime placedDate,
        Long selectionId,
        String selectionName,
        LocalDateTime startDate,
        String transactionType,
        Long transactionId,
        String winLose

) {
}
