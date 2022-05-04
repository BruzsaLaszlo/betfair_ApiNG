package entities;

import enums.InstructionReportErrorCode;
import enums.InstructionReportStatus;

public record UpdateInstructionReport(

        InstructionReportStatus status,
        InstructionReportErrorCode errorCode,
        UpdateInstruction instruction

) {
}
