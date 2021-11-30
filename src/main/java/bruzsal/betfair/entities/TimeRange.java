package bruzsal.betfair.entities;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

public class TimeRange {

    private Date from;
    private Date to;


    public final Date getFrom() {
        return from;
    }

    public final void setFrom(Date from) {
        this.from = from;
    }

    public final void setLFrom(LocalDateTime from) {
        this.from = Timestamp.valueOf(from);
    }

    public final Date getTo() {
        return to;
    }

    public final void setTo(Date to) {
        this.to = to;
    }

    public final void setLTo(LocalDateTime to) {
        this.to = Timestamp.valueOf(to);
    }


    @Override
    public String toString() {
        return "    from = " + from + '\n' +
                "    to = " + to;
    }
}
