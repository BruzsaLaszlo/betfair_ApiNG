package com.betfair.aping.entities;

import com.betfair.aping.enums.InstructionReportErrorCode;
import com.betfair.aping.enums.InstructionReportStatus;

public class UpdateInstructionReport {
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

    public void setInstruction(UpdateInstruction instruction) {
        this.instruction = instruction;
    }

    public UpdateInstruction getInstruction() {
        return instruction;
    }

    private InstructionReportStatus status;
    private InstructionReportErrorCode errorCode;
    private UpdateInstruction instruction;
}
