package com.betfair.aping.entities;

public class DeveloperAppVersion {

    /** The sportex user who owns the specific version of the application */
    private String owner;

    /** The unique Id of the application version */
    private long versionId;

    /** The version identifier string such as 1.0, 2.0. Unique for a given
     * application. */
    private String version;

    /** The unqiue application key associated with this application version */
    private String applicationKey;

    /** Indicates whether the data exposed by platform services as seen by this
     * application key is delayed or realtime. */
    private boolean delayData;

    /** Indicates whether the application version needs explicit subscription */
    private boolean subscriptionRequired;

    /** Indicates whether the application version needs explicit management by
     * producers. A value of false indicates, this is a version meant for
     * developer use. */
    private boolean ownerManaged;

    /** Indicates whether the application version is currently active */
    private boolean active;

    public String getOwner() {
	return owner;
    }

    public void setOwner(String owner) {
	this.owner = owner;
    }

    public long getVersionId() {
	return versionId;
    }

    public void setVersionId(long versionId) {
	this.versionId = versionId;
    }

    public String getVersion() {
	return version;
    }

    public void setVersion(String version) {
	this.version = version;
    }

    public String getApplicationKey() {
	return applicationKey;
    }

    public void setApplicationKey(String applicationKey) {
	this.applicationKey = applicationKey;
    }

    public boolean isDelayData() {
	return delayData;
    }

    public void setDelayData(boolean delayData) {
	this.delayData = delayData;
    }

    public boolean isSubscriptionRequired() {
	return subscriptionRequired;
    }

    public void setSubscriptionRequired(boolean subscriptionRequired) {
	this.subscriptionRequired = subscriptionRequired;
    }

    public boolean isOwnerManaged() {
	return ownerManaged;
    }

    public void setOwnerManaged(boolean ownerManaged) {
	this.ownerManaged = ownerManaged;
    }

    public boolean isActive() {
	return active;
    }

    public void setActive(boolean active) {
	this.active = active;
    }

}
