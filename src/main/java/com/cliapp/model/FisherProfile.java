package com.cliapp.model;

public class FisherProfile {
    private Long id;
    private String fishingLicenseNumber;
    private String userName;
    private String defaultLanding;

    public FisherProfile(String userName, String fishingLicenseNumber, Long id) {
        this.userName = userName;
        this.fishingLicenseNumber = fishingLicenseNumber;
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFishingLicenseNumber() {
        return fishingLicenseNumber;
    }

    public void setFishingLicenseNumber(String fishingLicenseNumber) {
        this.fishingLicenseNumber = fishingLicenseNumber;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDefaultLanding() {
        return defaultLanding;
    }

    public void setDefaultLanding(String defaultLanding) {
        this.defaultLanding = defaultLanding;
    }
}
