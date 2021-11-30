package bruzsal.betfair.entities;

import bruzsal.betfair.enums.InstructionReportErrorCode;
import bruzsal.betfair.enums.InstructionReportStatus;

public record ReplaceInstructionReport(

        InstructionReportStatus status,

        InstructionReportErrorCode errorCode,

        CancelInstructionReport cancelInstructionReport,

        PlaceInstructionReport placeInstructionReport

) {
}
