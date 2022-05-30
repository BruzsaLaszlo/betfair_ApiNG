package aping.entities;

/**
 * @param selectionId Selection ID of the runner in the key line handicap.
 * @param handicap    Handicap value of the key line.
 */
public record KeyLineSelection(

        Long selectionId,
        Double handicap

) {
}
