package bruzsal.betfair.entities;

import bruzsal.betfair.enums.InstructionReportErrorCode;
import bruzsal.betfair.enums.InstructionReportStatus;
import bruzsal.betfair.enums.OrderStatus;

import java.util.Date;

public record PlaceInstructionReport (

     InstructionReportStatus status,
     InstructionReportErrorCode errorCode,
     OrderStatus orderStatus,
     PlaceInstruction instruction,
     String betId,
     Date placedDate,
     double averagePriceMatched,
     double sizeMatched

){
}
