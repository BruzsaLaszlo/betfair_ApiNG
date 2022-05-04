package entities;

import enums.ExecutionReportErrorCode;
import enums.ExecutionReportStatus;

import java.util.List;

public record CancelExecutionReport(

        String customerRef,
        ExecutionReportStatus status,
        ExecutionReportErrorCode errorCode,
        String marketId,
        List<CancelInstructionReport> instructionReports

) {
}
