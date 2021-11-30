package bruzsal.betfair.entities;

public record KeyLineSelection(

        /**
         * Selection ID of the runner in the key line handicap.
         */
        Long selectionId,

        /**
         * Handicap value of the key line.
         */
        Double handicap

) {
}
