package com.app.gagi.iremember.Common;

import android.location.Location;

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

    public static StoryGPSInfo buildFromLocation(Location location) {
        double lat = 0.0,lon = 0.0;
        if(null != location)
        {
            lat = location.getLatitude();
            lon = location.getLongitude();
        }
        return  new StoryGPSInfo(lat,lon);
    }
}
