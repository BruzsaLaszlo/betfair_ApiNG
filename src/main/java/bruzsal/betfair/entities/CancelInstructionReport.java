package bruzsal.betfair.entities;

import bruzsal.betfair.enums.InstructionReportErrorCode;
import bruzsal.betfair.enums.InstructionReportStatus;

import java.util.Date;

public class CancelInstructionReport {
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

    public void setInstruction(CancelInstruction instruction) {
        this.instruction = instruction;
    }

    public CancelInstruction getInstruction() {
        return instruction;
    }

    public void setSizeCancelled(double sizeCancelled) {
        this.sizeCancelled = sizeCancelled;
    }

    public double getSizeCancelled() {
        return sizeCancelled;
    }

    public void setCancelledDate(Date cancelledDate) {
        this.cancelledDate = cancelledDate;
    }

    public Date getCancelledDate() {
        return cancelledDate;
    }

    private InstructionReportStatus status;
    private InstructionReportErrorCode errorCode;
    private CancelInstruction instruction;
    private double sizeCancelled;
    private Date cancelledDate;
}
