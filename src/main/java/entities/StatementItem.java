package entities;

import enums.ItemClass;

import java.time.LocalDateTime;
import java.util.Map;

public record StatementItem(

        String refId,
        LocalDateTime itemDate,
        double amount,
        double balance,
        ItemClass itemClass,
        Map<String, String> itemClassData,
        StatementLegacyData legacyData

) {
}
