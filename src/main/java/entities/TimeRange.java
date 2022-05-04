package entities;

import java.time.LocalDateTime;

public record TimeRange(

        LocalDateTime from,
        LocalDateTime to

) {

    public TimeRange(LocalDateTime from) {
        this(from, null);
    }

    @Override
    public String toString() {
        return "    from = " + from + '\n' +
               "    to = " + to;
    }
}
