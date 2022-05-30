package aping.entities;

import java.util.List;

/**
 * @param appName     The unique name of the aping.application
 * @param appId       A unique id of this aping.application
 * @param appVersions The aping.application versions (including aping.application keys)
 */
public record DeveloperApp(

        String appName,
        Long appId,
        List<DeveloperAppVersion> appVersions

) {

    @Override
    public String toString() {
        return "DeveloperApp" + '\n' +
                "  appName  =  " + appName + '\n' +
                "  appId  =  " + appId + '\n' +
                "  appVersions  =  " + appVersions;
    }

}
