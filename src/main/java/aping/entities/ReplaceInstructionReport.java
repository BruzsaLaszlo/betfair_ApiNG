package aping.entities;

import aping.enums.InstructionReportErrorCode;
import aping.enums.InstructionReportStatus;

public record ReplaceInstructionReport(

        InstructionReportStatus status,

        InstructionReportErrorCode errorCode,

        CancelInstructionReport cancelInstructionReport,

        PlaceInstructionReport placeInstructionReport

) {
}
