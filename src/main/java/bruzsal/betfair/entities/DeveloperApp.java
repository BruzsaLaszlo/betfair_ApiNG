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

    public List<DeveloperAppVersion> getAppVersions() {
        return appVersions;
    }

    public void setAppVersions(List<DeveloperAppVersion> appVersions) {
        this.appVersions = appVersions;
    }

    public long getAppId() {
        return appId;
    }

    public void setAppId(long appId) {
        this.appId = appId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }
}
