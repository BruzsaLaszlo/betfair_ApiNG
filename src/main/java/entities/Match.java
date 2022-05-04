package entities;

import java.time.LocalDateTime;

public record Match(

        String betId,
        String matchId,
        String side,
        Double price,
        Double size,
        LocalDateTime matchDate

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
