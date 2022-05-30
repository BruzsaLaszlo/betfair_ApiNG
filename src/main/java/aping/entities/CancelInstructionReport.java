package aping.entities;

import aping.enums.InstructionReportErrorCode;
import aping.enums.InstructionReportStatus;

import java.time.LocalDateTime;

public record CancelInstructionReport(

        InstructionReportStatus status,

        InstructionReportErrorCode errorCode,

        CancelInstruction instruction,

        Double sizeCancelled,

        LocalDateTime cancelledDate

) {
}
