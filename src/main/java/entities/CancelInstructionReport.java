package entities;

import enums.InstructionReportErrorCode;
import enums.InstructionReportStatus;

import java.time.LocalDateTime;

public record CancelInstructionReport(

        InstructionReportStatus status,

        InstructionReportErrorCode errorCode,

        CancelInstruction instruction,

        Double sizeCancelled,

        LocalDateTime cancelledDate

) {
}
