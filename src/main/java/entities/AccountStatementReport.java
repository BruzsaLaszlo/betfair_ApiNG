package entities;

import java.util.List;

public record AccountStatementReport(

        List<StatementItem> accountStatement,
        Boolean moreAvailable

) {
}
