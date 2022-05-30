package aping.entities;

import aping.enums.ExecutionReportErrorCode;
import aping.enums.ExecutionReportStatus;

import java.util.List;


public record PlaceExecutionReport(

        String customerRef,
        ExecutionReportStatus status,
        ExecutionReportErrorCode errorCode,
        String marketId,
        List<PlaceInstructionReport> instructionReports

) {
}
