package bruzsal.betfair.entities;

import java.util.Date;

public record Match(

        String betId,
        String matchId,
        String side,
        Double price,
        Double size,
        Date matchDate

) {

    @Override
    public String toString() {
        return "Match{" +
                "    betId = " + betId + '\n' +
                "    matchId = " + matchId + '\n' +
                "    side = " + side + '\n' +
                "    price = " + price + '\n' +
                "    size = " + size + '\n' +
                "    matchDate = " + matchDate + '\n' +
                '}';
    }
}
