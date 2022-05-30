package aping.entities;

import aping.enums.RollupModel;

public record ExBestOfferOverRides(

        Integer bestPricesDepth,
        RollupModel rollupModel,
        Integer rollupLimit,
        Double rollupLiabilityThreshold,
        Integer rollupLiabilityFactor

) {
}
