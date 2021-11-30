package bruzsal.betfair.entities;

public record RunnerProfitAndLoss(

        Long selectionId,
        Double ifWin,
        Double ifLose

) {
}
