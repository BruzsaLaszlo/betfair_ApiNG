package aping.entities;

import aping.enums.InstructionReportErrorCode;
import aping.enums.InstructionReportStatus;

import java.util.List;

public record UpdateExecutionReport(

        String customerRef,
        InstructionReportStatus status,
        InstructionReportErrorCode errorCode,
        List<UpdateInstructionReport> instructionReports

) {
}
