package entities;

import java.util.List;

/**
 * @param appName The unique name of the application
 * @param appId A unique id of this application
 * @param appVersions The application versions (including application keys)
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
