package bruzsal.betfair.entities;

import bruzsal.betfair.enums.InstructionReportErrorCode;
import bruzsal.betfair.enums.InstructionReportStatus;

import java.util.List;

public record UpdateExecutionReport(

        String customerRef,
        InstructionReportStatus status,
        InstructionReportErrorCode errorCode,
        List<UpdateInstructionReport> instructionReports

) {
}
