package bruzsal.betfair.entities;

import java.util.List;

public record MarketProfitAndLoss(

        String marketId,
        double commissionApplied,
        List<RunnerProfitAndLoss> profitAndLosses

) {
}
