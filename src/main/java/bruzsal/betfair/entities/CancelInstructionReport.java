package bruzsal.betfair.entities;

import bruzsal.betfair.enums.InstructionReportErrorCode;
import bruzsal.betfair.enums.InstructionReportStatus;

import java.util.Date;

public record CancelInstructionReport(

        InstructionReportStatus status,

        InstructionReportErrorCode errorCode,

        CancelInstruction instruction,

        double sizeCancelled,

        Date cancelledDate

) {
}
