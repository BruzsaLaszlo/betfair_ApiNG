package entities;

public record MarketTypeResult(

        String marketType,
        Integer marketCount

) {

    @Override
    public String toString() {
        return String.format("%-20s %s", marketType, marketCount);
    }

}
