package com.app.gagi.iremember.Common;

import android.os.Parcelable;

/**
 * Created by igaglioti on 10/03/14.
 */
public class StoryListOverviewItem {

    public static String TITLE_TAG = "title";
    public static String PHOTO_TAG = "photo";
    public static String AUDIO_TAG = "audio";
    public static String VIDEO_TAG = "video";
    public static String GPS_LAT_TAG = "gps_lat";
    public static String GPS_LONG_TAG = "gps_long";
    public static String STORYTIME_TAG = "story_time";

    String mPhotoPath;
    String mAudioPath;
    String mVideoPath;
    StoryGPSInfo mGPSInfo;
    String mStoryDate;
    String mItemName;

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

    public void setGPSInfo(StoryGPSInfo mGPSInfo) {
        this.mGPSInfo = mGPSInfo;
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

    public  StoryListOverviewItem(String itemName,
                                  String audioPath, String photoPath,
                                  String videoPath, String storyDate, StoryGPSInfo gpsInfo)
    {
        this.mItemName = itemName;
        this.mAudioPath = audioPath;
        this.mPhotoPath = photoPath;
        this.mVideoPath = videoPath;
        this.mStoryDate = storyDate;
        this.mGPSInfo = gpsInfo;
    }



}
