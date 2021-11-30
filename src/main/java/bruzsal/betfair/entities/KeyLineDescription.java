package bruzsal.betfair.entities;

import java.util.List;

/**
 * Description of a markets key line selection, comprising the selectionId and handicap of the team it is applied to.
 */
public record KeyLineDescription(

        List<KeyLineSelection> keyLine

) {
}
