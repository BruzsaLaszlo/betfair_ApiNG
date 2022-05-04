package entities;

import enums.InstructionReportErrorCode;
import enums.InstructionReportStatus;

import java.util.List;

public record UpdateExecutionReport(

        String customerRef,
        InstructionReportStatus status,
        InstructionReportErrorCode errorCode,
        List<UpdateInstructionReport> instructionReports

) {
}
