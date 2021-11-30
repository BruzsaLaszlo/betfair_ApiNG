package bruzsal.betfair.entities;

import bruzsal.betfair.enums.InstructionReportErrorCode;
import bruzsal.betfair.enums.InstructionReportStatus;

public record UpdateInstructionReport(

        InstructionReportStatus status,
        InstructionReportErrorCode errorCode,
        UpdateInstruction instruction

) {
}
