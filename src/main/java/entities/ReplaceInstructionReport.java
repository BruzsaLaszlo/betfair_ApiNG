package entities;

import enums.InstructionReportErrorCode;
import enums.InstructionReportStatus;

public record ReplaceInstructionReport(

        InstructionReportStatus status,

        InstructionReportErrorCode errorCode,

        CancelInstructionReport cancelInstructionReport,

        PlaceInstructionReport placeInstructionReport

) {
}
