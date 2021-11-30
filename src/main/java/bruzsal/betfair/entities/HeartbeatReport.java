package bruzsal.betfair.entities;


import bruzsal.betfair.enums.ActionPerformed;

/**
 * Response from heartbeat operation
 */
public record HeartbeatReport(

        /**
         * The action performed since your last heartbeat request.
         */
        ActionPerformed actionPerformed,

        /**
         * The actual timeout applied to your heartbeat request, see timeout request parameter description for details.
         */
        Integer actualTimeoutSeconds

) {
}
