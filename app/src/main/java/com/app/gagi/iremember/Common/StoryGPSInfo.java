package com.app.gagi.iremember.Common;

/**
 * Created by igaglioti on 27/03/14.
 */
public class StoryGPSInfo {

    public double getLatitude() {
        return mLatitude;
    }

    public void setLatitude(double mLatitude) {
        this.mLatitude = mLatitude;
    }

    public double getLongitude() {
        return mLongitude;
    }

    public void setLongitude(double mLongitude) {
        this.mLongitude = mLongitude;
    }

    private double mLatitude;
    private double mLongitude;
    public StoryGPSInfo(double latitude, double longitude )
    {
        mLatitude = latitude;
        mLongitude = longitude;
    }
}
