package aping.entities;

import aping.enums.PriceData;

import java.util.Set;


public record PriceProjection(

        Set<PriceData> priceData,
        ExBestOfferOverRides exBestOfferOverRides,
        Boolean virtualise,
        Boolean rolloverStakes

) {
}
