package aping.entities;

import java.util.List;

public record StartingPrices(

        Double nearPrice,
        Double farPrice,
        List<PriceSize> backStakeTaken,
        List<PriceSize> layLiabilityTaken,
        Double actualSP

) {
}
