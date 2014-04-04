package com.app.gagi.iremember.DAL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by igaglioti on 03/04/14.
 */
public class StoryDBMapper {

    final String LOG_TAG = StoryDBMapper.class.getCanonicalName();
    Context mContext;
    private static  StoryDBMapper mStoryDBMapper;
//    public StoryDBMapper (Context context)
//    {
//        mContext = context;
//    }

    public StoryDBMapper(Context context)
    {
        mContext = context;
    }
    public static StoryDBMapper getInstance(Context context)
    {
        if(null == mStoryDBMapper)
            mStoryDBMapper = new StoryDBMapper(context);
        return mStoryDBMapper;
    }
    private StoryDBHelper mDBHelper;
    private SQLiteDatabase mDB;

    public synchronized StoryDBMapper open()  throws SQLException
    {
        if(null == mDBHelper) {
            try {
                Log.i(LOG_TAG, "db open");
                mDBHelper = new StoryDBHelper(mContext, null);
                mDB = mDBHelper.getWritableDatabase();

            } catch (SQLiteException e) {
                Log.w(LOG_TAG, "db open in readable mode::" + e.getMessage());
                mDB = mDBHelper.getReadableDatabase();
            }
        }
        else
        {
            if(!mDB.isOpen())
                mDB = mDBHelper.getWritableDatabase();
        }
        return this;
    }

    public ArrayList<StoryDataEntity> queryAllStories()
    {
        Cursor cursor = query(StoryDBHelper.STORY_TABLE_NAME,StoryDBHelper.columns,null, new String[]{},null);
        ArrayList<StoryDataEntity> res = new ArrayList<StoryDataEntity>();
        ContentValues values = new ContentValues();
        while (cursor.moveToNext()) {
            values.put(StoryDBHelper.PHOTO_NAME, cursor.getString(cursor.getColumnIndex(StoryDBHelper.PHOTO_NAME)));
            values.put(StoryDBHelper.PHOTO_PATH, cursor.getString(cursor.getColumnIndex(StoryDBHelper.PHOTO_PATH)));
            values.put(StoryDBHelper.VIDEO_PATH, cursor.getString(cursor.getColumnIndex(StoryDBHelper.VIDEO_PATH)));
            values.put(StoryDBHelper.GPS_LON, cursor.getDouble(cursor.getColumnIndex(StoryDBHelper.GPS_LON)));
            values.put(StoryDBHelper.GPS_LAT, cursor.getDouble(cursor.getColumnIndex(StoryDBHelper.GPS_LAT)));
            values.put(StoryDBHelper.BODY, cursor.getString(cursor.getColumnIndex(StoryDBHelper.BODY)));
            values.put(StoryDBHelper.CREATION_TIME, cursor.getDouble(cursor.getColumnIndex(StoryDBHelper.CREATION_TIME)));
            values.put(StoryDBHelper.TITLE, cursor.getString(cursor.getColumnIndex(StoryDBHelper.TITLE)));
            res.add(StoryDataEntity.getStoryFromValues(values));
            values.clear();
        }
        return  res;
    }

    public synchronized Cursor query(final String table, final String[] columns,
                        final String selection, final String[] selectionArgs,
                        final String sortOrder) {
        Cursor cursor = null;
        if(null != mDB)
            cursor = mDB.query(table,columns,selection,selectionArgs,null,null,sortOrder);
        else
            Log.w(LOG_TAG,"query-> database is null");

        return cursor;
    }
    public synchronized long insertStoryData(StoryDataEntity storyData)
    {
        long res;
        res = -1;
        if(mDB.isOpen())
        {
            try {
                ContentValues values = StoryDataEntity.getCVFromStoryData(storyData);
                res = mDB.insertOrThrow(StoryDBHelper.STORY_TABLE_NAME,null,values);
                Log.i(LOG_TAG,"insertStoryData new_index:" + res);
            }
            catch (SQLException e)
            {
                Log.e(LOG_TAG,"insertStoryData failed::" + e.getMessage());
            }
        }
        else
        {
            Log.w(LOG_TAG,"db is not open");
        }
        return  res;
    }

    public synchronized void close()
    {
        if((null != mDB)
            && mDB.isOpen())
            mDB.close();
    }
}
