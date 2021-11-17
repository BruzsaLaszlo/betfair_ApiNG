package bruzsal.betfair.entities;

import bruzsal.betfair.enums.InstructionReportErrorCode;
import bruzsal.betfair.enums.InstructionReportStatus;

import java.util.List;

public class UpdateExecutionReport {
    public void setCustomerRef(String customerRef) {
        this.customerRef = customerRef;
    }

    public String getCustomerRef() {
        return customerRef;
    }

    public void setStatus(InstructionReportStatus status) {
        this.status = status;
    }

    public InstructionReportStatus getStatus() {
        return status;
    }

    public void setErrorCode(InstructionReportErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public InstructionReportErrorCode getErrorCode() {
        return errorCode;
    }

    public void setInstructionReports(List<UpdateInstructionReport> instructionReports) {
        this.instructionReports = instructionReports;
    }

    public List<UpdateInstructionReport> getInstructionReports() {
        return instructionReports;
    }

    private String customerRef;
    private InstructionReportStatus status;
    private InstructionReportErrorCode errorCode;
    private List<UpdateInstructionReport> instructionReports;
}
