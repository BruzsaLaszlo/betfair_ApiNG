package bruzsal.betfair.entities;

import java.util.List;


public class DeveloperApp {

    /**
     * The unique name of the application
     */
    private String appName;

    /**
     * A unique id of this application
     */
    private long appId;

    /**
     * The application versions (including application keys)
     */
    private List<DeveloperAppVersion> appVersions;


    public long getAppId() {
        return appId;
    }

    public String getAppName() {
        return appName;
    }
    
    public List<DeveloperAppVersion> getAppVersions() {
        return appVersions;
    }

    @Override
    public String toString() {
        return "DeveloperApp" + '\n' +
                "  appName  =  " + appName + '\n' +
                "  appId  =  " + appId + '\n' +
                "  appVersions  =  " + appVersions;
    }
}
