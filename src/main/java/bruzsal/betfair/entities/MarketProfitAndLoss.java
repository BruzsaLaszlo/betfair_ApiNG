package bruzsal.betfair.entities;

import java.util.List;

public record MarketProfitAndLoss(

        String marketId,
        Double commissionApplied,
        List<RunnerProfitAndLoss> profitAndLosses

) {
}
