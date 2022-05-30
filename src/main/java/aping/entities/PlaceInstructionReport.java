package aping.entities;

import aping.api.PlaceInstruction;
import aping.enums.InstructionReportErrorCode;
import aping.enums.InstructionReportStatus;
import aping.enums.OrderStatus;

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
