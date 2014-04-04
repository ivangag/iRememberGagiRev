package com.app.gagi.iremember.DAL;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.app.gagi.iremember.Common.StoryGPSInfo;

import java.util.ArrayList;

/**
 * Created by igaglioti on 10/03/14.
 */
public class StoryDataEntity implements Parcelable {

    public static String TITLE_TAG = "title";
    public static String BODY_TAG = "body";
    public static String PHOTO_TAG = "photo";
    public static String AUDIO_TAG = "audio";
    public static String VIDEO_TAG = "video";
    public static String GPS_LAT_TAG = "gps_lat";
    public static String GPS_LONG_TAG = "gps_long";
    public static String STORYTIME_TAG = "story_time";

    public  StoryDataEntity(){}

    public String getBody() {
        return mBody;
    }

    public void setBody(String mBody) {
        this.mBody = mBody;
    }

    public double getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(double creationTime) {
        this.creationTime = creationTime;
    }

    private double creationTime;
    private String mBody;
    private String mPhotoPath;

    public String getPhotoName() {
        return mPhotoName;
    }

    public void setPhotoName(String mPhotoName) {
        this.mPhotoName = mPhotoName;
    }

    private String mPhotoName;
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

    public StoryDataEntity(String itemName,
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
    private StoryDataEntity(Parcel in) {
        mAudioPath = in.readString();
        mPhotoPath = in.readString();
        mVideoPath = in.readString();
        mStoryDate = in.readString();
        mItemName  = in.readString();
        mLatitude = in.readDouble();
        mLongitude = in.readDouble();
    }

    public static final Parcelable.Creator<StoryDataEntity> CREATOR
            = new Parcelable.Creator<StoryDataEntity>() {
        public StoryDataEntity createFromParcel(Parcel in) {
            return new StoryDataEntity(in);
        }

        public StoryDataEntity[] newArray(int size) {
            return new StoryDataEntity[size];
        }
    };


    public static ContentValues getCVFromStoryData(StoryDataEntity story)
    {
        ContentValues cv = new ContentValues();
        cv.put(StoryDBHelper.TITLE,story.getItemName());
        cv.put(StoryDBHelper.AUDIO_PATH,story.getAudioPath());
        cv.put(StoryDBHelper.BODY,story.getBody());
        cv.put(StoryDBHelper.CREATION_TIME,story.getCreationTime());
        cv.put(StoryDBHelper.GPS_LAT,story.getLatitude());
        cv.put(StoryDBHelper.GPS_LON,story.getLongitude());
        cv.put(StoryDBHelper.PHOTO_NAME,story.getPhotoName());
        cv.put(StoryDBHelper.PHOTO_PATH,story.getPhotoPath());
        cv.put(StoryDBHelper.VIDEO_PATH,story.getVideoPath());

        return cv;
    }

    public static StoryDataEntity getStoryFromValues(ContentValues values)
    {
        StoryDataEntity story = new StoryDataEntity();
        story.setAudioPath(values.getAsString(StoryDBHelper.AUDIO_PATH));
        story.setItemName(values.getAsString(StoryDBHelper.TITLE));
        story.setBody(values.getAsString(StoryDBHelper.BODY));
        story.setLatitude(values.getAsDouble(StoryDBHelper.GPS_LAT));
        story.setLongitude(values.getAsDouble(StoryDBHelper.GPS_LON));
        story.setCreationTime(values.getAsDouble(StoryDBHelper.CREATION_TIME));
        story.setPhotoName(values.getAsString(StoryDBHelper.PHOTO_NAME));
        story.setPhotoPath(values.getAsString(StoryDBHelper.PHOTO_PATH));
        story.setVideoPath(values.getAsString(StoryDBHelper.VIDEO_PATH));
        return story;
    }
}
