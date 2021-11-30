package bruzsal.betfair.entities;

import java.util.List;

public record ExchangePrices(

        List<PriceSize> availableToBack,
        List<PriceSize> availableToLay,
        List<PriceSize> tradedVolume

) {
}
