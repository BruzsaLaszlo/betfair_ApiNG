package bruzsal.betfair.entities;

import bruzsal.betfair.enums.RollupModel;

public record ExBestOfferOverRides(

        Integer bestPricesDepth,
        RollupModel rollupModel,
        Integer rollupLimit,
        Double rollupLiabilityThreshold,
        Integer rollupLiabilityFactor

) {
}
