package bruzsal.betfair.entities;

public record DeveloperAppVersion(

        /**
         * The sportex user who owns the specific version of the application
         */
        String owner,

        /**
         * The unique Id of the application version
         */
        Long versionId,

        /**
         * The version identifier string such as 1.0    2.0. Unique for a given
         * application.
         */
        String version,

        /**
         * The unqiue application key associated with this application version
         */
        String applicationKey,

        /**
         * Indicates whether the data exposed by platform services as seen by this
         * application key is delayed or realtime.
         */
        Boolean delayData,

        /**
         * Indicates whether the application version needs explicit subscription
         */
        Boolean subscriptionRequired,

        /**
         * Indicates whether the application version needs explicit management by
         * producers. A value of false indicates    this is a version meant for
         * developer use.
         */
        Boolean ownerManaged,

        /**
         * Indicates whether the application version is currently active
         */
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
