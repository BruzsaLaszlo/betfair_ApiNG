package bruzsal.betfair.entities;

public class ReplaceInstruction {

    private String betId;
    private double newPrice;


    public ReplaceInstruction(String betId, double newPrice) {
        this.betId = betId;
        this.newPrice = newPrice;
    }
}
