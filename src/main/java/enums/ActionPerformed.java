package enums;

public enum ActionPerformed {

    /**
     * No action was performed since last heartbeat, or this is the first heartbeat
     */
    NONE,


    /**
     * A request to cancel all unmatched bets was submitted since last heartbeat
     */
    CANCELLATION_REQUEST_SUBMITTED,

    /**
     * All unmatched bets were cancelled since last heartbeat
     */
    ALL_BETS_CANCELLED,


    /**
     * Not all unmatched bets were cancelled since last heartbeat
     */
    SOME_BETS_NOT_CANCELLED,

    /**
     * There was an error requesting cancellation, no bets have been cancelled
     */
    CANCELLATION_REQUEST_ERROR,


    /**
     * There was no response from requesting cancellation, cancellation status unknown
     */
    CANCELLATION_STATUS_UNKNOWN

}
