package com.app.gagi.iremember.DAL;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by igaglioti on 03/04/14.
 */
public class StoryDBHelper extends SQLiteOpenHelper {

    final String LOG_TAG = StoryDBHelper.class.getCanonicalName();

    Context mContext;
    //* Columns Name *//
    final static  String ID = "_id";
    final static  String TITLE = "Title";
    final static  String BODY = "Body";
    final static  String CREATION_TIME = "CreationTime";
    final static  String AUDIO_PATH = "AudioPath";
    final static  String VIDEO_PATH = "VideoPath";
    final static  String PHOTO_PATH = "PhotoPath";
    final static  String PHOTO_NAME = "PhotoName";
    final static  String GPS_LAT = "GpsLat";
    final static  String GPS_LON = "GpsLon";

    final static  String STORY_TABLE_NAME = "tb_story";
    final static  String DATABASE_NAME = "story_db";
    final static  int DATABASE_VERSION = 1;
    final static String[] columns = { ID, TITLE, BODY,CREATION_TIME,AUDIO_PATH,VIDEO_PATH,PHOTO_PATH,PHOTO_NAME,GPS_LAT,GPS_LON};


    private static final String DATABASE_CREATE_STORY = "create table "
            + STORY_TABLE_NAME + " (" // start table
            + ID + " integer primary key autoincrement, " // setup
            // auto-inc.
            // ST:tableCreateVariables:start
//            + Story_LoginId + " INTEGER ," //
//            + Story_StoryId + " INTEGER ," //
            + TITLE + " TEXT ," //
            + BODY + " TEXT ," //
            + AUDIO_PATH + " TEXT ," //
            + VIDEO_PATH + " TEXT ," //
            + PHOTO_NAME + " TEXT ," //
            + PHOTO_PATH + " TEXT ," //
            + CREATION_TIME + " INTEGER ," //
//            + Story_StoryTime + " INTEGER ," //
            + GPS_LAT + " REAL ," //
            + GPS_LON + " REAL  " //
            // ST:tableCreateVariables:finish
            + " );"; // end table

    public StoryDBHelper(Context context, SQLiteDatabase.CursorFactory factory) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
        this.mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE_STORY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Log version upgrade.
        Log.w(LOG_TAG + "DBHelper", "Upgrading from version " + oldVersion
                + " to " + newVersion + ", which will destroy all old data");

        // **** Upgrade DB ****
        // drop old DB

        // ST:dropTableIfExists:start
        db.execSQL("DROP TABLE IF EXISTS " + STORY_TABLE_NAME);
        //db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_TAGS);
        // ST:dropTableIfExists:finish

        // Create a new one.
        onCreate(db);
    }

    public boolean deleteDataBase()
    {
       return mContext.deleteDatabase(DATABASE_NAME);
    }
}
