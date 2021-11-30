package bruzsal.betfair.entities;

import java.util.Map;

/**
 * @param selectionId The unique id for the selection.
 *                     The same selectionId and runnerName pairs are used accross all Betfair markets which contain them.
 *                     Please note: The selectionId can be mapped to the runner name using the output from listMarketCatalogue
 * @param metadata Metadata associated with the runner.
 *                 For a description of this data for Horse Racing, please see Runner Metadata Description
 */
public record RunnerCatalog(

        Long selectionId,

        String runnerName,

        Double handicap,

        Integer sortPriority,

        Map<String, String> metadata

) {

    @Override
    public String toString() {
        return "RunnerCatalog{" + '\n' +
                "    selectionId=" + selectionId + '\n' +
                "    runnerName='" + runnerName + '\n' +
                "    handicap=" + handicap + '\n';
    }
}
