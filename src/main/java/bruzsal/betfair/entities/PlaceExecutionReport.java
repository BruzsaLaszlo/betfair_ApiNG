package bruzsal.betfair.entities;

import bruzsal.betfair.enums.ExecutionReportErrorCode;
import bruzsal.betfair.enums.ExecutionReportStatus;

import java.util.List;


public record PlaceExecutionReport(

        String customerRef,
        ExecutionReportStatus status,
        ExecutionReportErrorCode errorCode,
        String marketId,
        List<PlaceInstructionReport> instructionReports

) {
}
