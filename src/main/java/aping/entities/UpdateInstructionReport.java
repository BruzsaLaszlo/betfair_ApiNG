package aping.entities;

import aping.enums.InstructionReportErrorCode;
import aping.enums.InstructionReportStatus;

public record UpdateInstructionReport(

        InstructionReportStatus status,
        InstructionReportErrorCode errorCode,
        UpdateInstruction instruction

) {
}
