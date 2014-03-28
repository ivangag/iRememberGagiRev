package com.app.gagi.iremember.Common;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by igaglioti on 10/03/14.
 */
public class StoryCreateItem implements Parcelable {

    public static String TITLE_TAG = "title";
    public static String PHOTO_TAG = "photo";
    public static String AUDIO_TAG = "audio";
    public static String VIDEO_TAG = "video";
    public static String GPS_LAT_TAG = "gps_lat";
    public static String GPS_LONG_TAG = "gps_long";
    public static String STORYTIME_TAG = "story_time";

    private String mPhotoPath;
    private String mAudioPath;
    private String mVideoPath;
    private StoryGPSInfo mGPSInfo;
    private String mStoryDate;
    private String mItemName;

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

    double mLatitude;
    double mLongitude;

    public String getPhotoPath() {
        return mPhotoPath;
    }

    public void setPhotoPath(String mPhotoPath) {
        this.mPhotoPath = mPhotoPath;
    }

    public String getAudioPath() {
        return mAudioPath;
    }

    public void setAudioPath(String mAudioPath) {
        this.mAudioPath = mAudioPath;
    }

    public String getVideoPath() {
        return mVideoPath;
    }

    public void setVideoPath(String mVideoPath) {
        this.mVideoPath = mVideoPath;
    }

    public StoryGPSInfo getGPSInfo() {
        return mGPSInfo;
    }

    public void setGPSInfo(StoryGPSInfo mGPSInfo)
    {
        this.mGPSInfo = mGPSInfo;
        mLatitude = mGPSInfo.getLatitude();
        mLongitude = mGPSInfo.getLongitude();
    }

    public String getStoryDate() {
        return mStoryDate;
    }

    public void setStoryDate(String mStoryDate) {
        this.mStoryDate = mStoryDate;
    }



    public String getItemName()
    {
        return this.mItemName;
    }

    public  void setItemName(String itemName)
    {
        this.mItemName = itemName;
    }

    public StoryCreateItem(String itemName,
                           String audioPath, String photoPath,
                           String videoPath, String storyDate, StoryGPSInfo gpsInfo)
    {
        this.mItemName = itemName;
        this.mAudioPath = audioPath;
        this.mPhotoPath = photoPath;
        this.mVideoPath = videoPath;
        this.mStoryDate = storyDate;
        setGPSInfo(gpsInfo);
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mAudioPath);
        dest.writeString(mPhotoPath);
        dest.writeString(mVideoPath);
        dest.writeString(mStoryDate);
        dest.writeString(mItemName);
        dest.writeDouble(mLatitude);
        dest.writeDouble(mLongitude);
    }
    private StoryCreateItem(Parcel in) {
        mAudioPath = in.readString();
        mPhotoPath = in.readString();
        mVideoPath = in.readString();
        mStoryDate = in.readString();
        mItemName  = in.readString();
        mLatitude = in.readDouble();
        mLongitude = in.readDouble();
    }

    public static final Parcelable.Creator<StoryCreateItem> CREATOR
            = new Parcelable.Creator<StoryCreateItem>() {
        public StoryCreateItem createFromParcel(Parcel in) {
            return new StoryCreateItem(in);
        }

        public StoryCreateItem[] newArray(int size) {
            return new StoryCreateItem[size];
        }
    };


}
