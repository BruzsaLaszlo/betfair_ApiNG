package entities;

import api.PlaceInstruction;
import enums.InstructionReportErrorCode;
import enums.InstructionReportStatus;
import enums.OrderStatus;

import java.time.LocalDateTime;

public record PlaceInstructionReport(

        InstructionReportStatus status,
        InstructionReportErrorCode errorCode,
        OrderStatus orderStatus,
        PlaceInstruction instruction,
        String betId,
        LocalDateTime placedDate,
        Double averagePriceMatched,
        Double sizeMatched

) {
}
