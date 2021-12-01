package bruzsal.betfair.entities;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

public record TimeRange (

     Date from,
     Date to

){

    public TimeRange(LocalDateTime from, LocalDateTime to) {
        this(getDate(from), getDate(to));
    }

    public TimeRange(LocalDateTime from) {
        this(getDate(from), null);
    }

    private static Date getDate(LocalDateTime ldt) {
        if (ldt == null)
            return null;
        else
            return Timestamp.valueOf(ldt);
    }

    @Override
    public String toString() {
        return "    from = " + from + '\n' +
                "    to = " + to;
    }
}
