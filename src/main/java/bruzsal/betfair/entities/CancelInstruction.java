package bruzsal.betfair.entities;

public record CancelInstruction(

        String betId,

        Double sizeReduction

) {
}
