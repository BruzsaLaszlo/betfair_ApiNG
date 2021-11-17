package bruzsal.betfair.entities;


import bruzsal.betfair.enums.ActionPerformed;

/**
 * Response from heartbeat operation
 */
public class HeartbeatReport {


    /**
     * The action performed since your last heartbeat request.
     */
    private ActionPerformed actionPerformed;


    /**
     * The actual timeout applied to your heartbeat request, see timeout request parameter description for details.
     */
    private int actualTimeoutSeconds;


    public ActionPerformed getActionPerformed() {
        return actionPerformed;
    }

    public int getActualTimeoutSeconds() {
        return actualTimeoutSeconds;
    }
}
