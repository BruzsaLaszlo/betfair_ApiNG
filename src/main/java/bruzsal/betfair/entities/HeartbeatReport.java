package bruzsal.betfair.entities;


import bruzsal.betfair.enums.ActionPerformed;

/**
 * Response from heartbeat operation
 * @param actionPerformed The action performed since your last heartbeat request.
 * @param actualTimeoutSeconds The actual timeout applied to your heartbeat request,
 *                             see timeout request parameter description for details.
 */
public record HeartbeatReport(

        ActionPerformed actionPerformed,

        Integer actualTimeoutSeconds

) {

    @Override
    public String toString() {
        return "HeartbeatReport{" + '\n' +
                "    actualTimeoutSeconds = " + actualTimeoutSeconds + '\n' +
                "    actionPerformed = " + actionPerformed + '\n' +
                '}' + '\n';
    }
}
