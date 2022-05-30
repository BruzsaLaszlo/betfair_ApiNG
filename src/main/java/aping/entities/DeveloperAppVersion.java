package aping.entities;

/**
 * @param owner                The sportex user who owns the specific version of the aping.application
 * @param versionId            The unique Id of the aping.application version
 * @param version              The version identifier string such as 1.0    2.0. Unique for a given aping.application.
 * @param applicationKey       The unique aping.application key associated with this aping.application version
 * @param delayData            Indicates whether the data exposed by platform services as seen by this
 *                             aping.application key is delayed or realtime.
 * @param subscriptionRequired Indicates whether the aping.application version needs explicit subscription
 * @param ownerManaged         Indicates whether the aping.application version needs explicit management by producers.
 *                             A value of false indicates    this is a version meant for developer use.
 * @param active               Indicates whether the aping.application version is currently active
 */
public record DeveloperAppVersion(

        String owner,
        Long versionId,
        String version,
        String applicationKey,
        Boolean delayData,
        Boolean subscriptionRequired,
        Boolean ownerManaged,
        Boolean active

) {

    @Override
    public String toString() {
        return "\n >  DeveloperAppVersion" + '\n' +
               "    owner = " + owner + '\n' +
               "    versionId = " + versionId + '\n' +
               "    version = " + version + '\n' +
               "    applicationKey = " + applicationKey + '\n' +
               "    delayData = " + delayData + '\n' +
               "    subscriptionRequired = " + subscriptionRequired + '\n' +
               "    ownerManaged = " + ownerManaged + '\n' +
               "    active = " + active;
    }
}
