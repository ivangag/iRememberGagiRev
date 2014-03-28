package com.app.gagi.iremember.UI;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.app.gagi.iremember.R;

import java.io.IOException;

/**
 * Created by igaglioti on 28/03/14.
 */
public class SoundRecordActivity  extends Activity{

    private static final String LOG_TAG = SoundRecordActivity.class.getName();
    public static final String EXTRA_OUTPUT = "OUTPUT_FILENAME";
    private static String mFileName = null;

    private boolean recorded = false;

    //private RecordButton mRecordButton = null;
    private MediaRecorder mRecorder = null;

    //private PlayButton mPlayButton = null;
    private MediaPlayer mPlayer = null;

    private void onRecord(boolean start) {
        if (start) {
            startRecording();
        } else {
            stopRecording();
        }
    }

    private void onPlay(boolean start) {
        if (start) {
            startPlaying();
        } else {
            stopPlaying();
        }
    }

    private void startPlaying() {
        mPlayer = new MediaPlayer();
        try {
            mPlayer.setDataSource(mFileName);
            mPlayer.prepare();
            mPlayer.start();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }
    }

    private void stopPlaying() {
        mPlayer.release();
        mPlayer = null;
    }

    private void startRecording() {
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.DEFAULT);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setOutputFile(mFileName);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mRecorder.prepare();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }

        mRecorder.start();
    }

    private void stopRecording() {
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
        recorded = true;
    }

//    // The Record Button & its logic
//    class RecordButton extends Button {
//        boolean mStartRecording = true;
//
//        OnClickListener clicker = new OnClickListener() {
//            public void onClick(View v) {
//                onRecord(mStartRecording);
//                if (mStartRecording) {
//                    setText("Stop recording");
//                } else {
//                    Intent data = new Intent();
//                    data.putExtra("data", mFileName);
//                    setResult(RESULT_OK, data);
//                    setText("Start recording");
//                }
//                mStartRecording = !mStartRecording;
//            }
//        };
//
//        public RecordButton(Context ctx) {
//            super(ctx);
//            setText("Start recording");
//            setOnClickListener(clicker);
//        }
//    }

//    // The Play button & its logic
//    class PlayButton extends Button {
//        boolean mStartPlaying = true;
//
//        OnClickListener clicker = new OnClickListener() {
//            public void onClick(View v) {
//                onPlay(mStartPlaying);
//                if (mStartPlaying) {
//                    setText("Stop playing");
//                } else {
//                    setText("Start playing");
//                }
//                mStartPlaying = !mStartPlaying;
//            }
//        };
//
//        public PlayButton(Context ctx) {
//            super(ctx);
//            setText("Start playing");
//            setOnClickListener(clicker);
//        }
//    }

    @Override
    /**
     * Sets up Activity's View
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        Intent caller = getIntent();
        mFileName = caller.getStringExtra(EXTRA_OUTPUT);

        Log.i("zzz", mFileName);

    }
    @Override
    protected void onResume()
    {
        super.onResume();
        ShowCustomDialog(this);
    }

    boolean mRecStarted = false;
    void ShowCustomDialog(final Activity activity)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Add the buttons
        builder.setMessage(R.string.audio_player_dialog_title)
                .setPositiveButton(R.string.start_rec_audio_player, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                })
                .setNegativeButton(R.string.exit_audio_player_registration, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                        activity.finish();
                    }
                })
                .setNeutralButton(R.string.start_audio_player_registration, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });

        // Create the AlertDialog
        AlertDialog dialog = builder.create();
        dialog.show();
        final Button startRegistrationButton =
                dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        startRegistrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // handle the click
                onRecord(!mRecStarted);
                if(!mRecStarted) {
                    startRegistrationButton.setText(R.string.stop_audio_player_registration);
                }
                else
                {
                    startRegistrationButton.setText(R.string.start_audio_player_registration);
                    Intent data = new Intent();
                    data.putExtra("data", mFileName);
                    setResult(RESULT_OK, data);
                }
                mRecStarted = !mRecStarted;
            }
        });

        final Button startPlayerButton =
                dialog.getButton(DialogInterface.BUTTON_NEUTRAL);
        startPlayerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // handle the click
                //startPlayerButton.setText(R.string.stop_audio_player_registration);
            }
        });
    }
    @Override
    /**
     * Handle onPause to release the media Recorder and Player instances.
     * @see android.app.Activity#onPause()
     */
    public void onPause() {
        super.onPause();
        if (mRecorder != null) {
            mRecorder.release();
            mRecorder = null;
        }

        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }
    }

    @Override
    /**
     * @see android.app.Activity#onStop()
     */
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        if(recorded == false){
            setResult(RESULT_CANCELED, null);
        }
        super.onBackPressed();
    }

}
