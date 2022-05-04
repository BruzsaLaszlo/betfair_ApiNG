package entities;

import enums.ExecutionReportErrorCode;
import enums.ExecutionReportStatus;

import java.util.List;

public record ReplaceExecutionReport(

        String customerRef,

        ExecutionReportStatus status,

        ExecutionReportErrorCode errorCode,

        String marketId,

        List<ReplaceInstructionReport> instructionReports

) {
}
