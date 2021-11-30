package bruzsal.betfair.entities;

import bruzsal.betfair.enums.ItemClass;

import java.util.Date;
import java.util.Map;

public record StatementItem(

        String refId,
        Date itemDate,
        double amount,
        double balance,
        ItemClass itemClass,
        Map<String, String> itemClassData,
        StatementLegacyData legacyData

) {
}
