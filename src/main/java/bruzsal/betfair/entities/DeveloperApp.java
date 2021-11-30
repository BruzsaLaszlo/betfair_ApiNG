package bruzsal.betfair.entities;

import java.util.List;


public record DeveloperApp (
        /**
         * The unique name of the application
         */
        String appName,

        /**
         * A unique id of this application
         */
        long appId,

        /**
         * The application versions (including application keys)
         */
        List<DeveloperAppVersion> appVersions
){

    @Override
    public String toString() {
        return "DeveloperApp" + '\n' +
                "  appName  =  " + appName + '\n' +
                "  appId  =  " + appId + '\n' +
                "  appVersions  =  " + appVersions;
    }

}
