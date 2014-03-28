package com.app.gagi.iremember.UI;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.app.gagi.iremember.R;

public class StoryCreateActivity extends Activity implements StoryCreateFragment.OnFragmentInteractionListener{


    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;
    public static final int MEDIA_TYPE_AUDIO = 3;

    public static final int CAMERA_PIC_REQUEST = 1;
    public static final int CAMERA_VIDEO_REQUEST = 2;
    public static final int MIC_SOUND_REQUEST = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.story_create_activity);
        StoryCreateFragment mCreateFragment = StoryCreateFragment.newInstance("","");

        if(savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container_activity_creation,mCreateFragment,"")
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.story_create, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
