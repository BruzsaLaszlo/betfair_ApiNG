package bruzsal.betfair.entities;

public record RunnerProfitAndLoss(

        long selectionId,
        double ifWin,
        double ifLose

) {
}
