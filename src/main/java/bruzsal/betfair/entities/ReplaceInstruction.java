package bruzsal.betfair.entities;

public record ReplaceInstruction(

        String betId,

        Double newPrice

) {
}
