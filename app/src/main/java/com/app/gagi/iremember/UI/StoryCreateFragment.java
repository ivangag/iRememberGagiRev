package com.app.gagi.iremember.UI;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.app.gagi.iremember.Common.StoryCreateItem;
import com.app.gagi.iremember.Common.StoryGPSInfo;
import com.app.gagi.iremember.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link StoryCreateFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link StoryCreateFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class StoryCreateFragment extends Fragment {


    private final static String LOG_TAG = StoryCreateFragment.class
            .getCanonicalName();



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    Bitmap photoBitmap;
    ImageView mImageView;
    View mRootView;
    EditText txtPhotoPath;
    EditText txtVideoPath;
    EditText txtAudioPath;
    EditText txtStoryTitle;
    static EditText txtStoryTime;
    ImageButton btnAddStoryTime;
    private OnFragmentInteractionListener mListener;
    private String mAudioPath;
    private String mPhotoPath;
    private String mStoryTitle;
    private String mStoryBody;
    private Uri mVideoFileUri;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StoryCreateFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StoryCreateFragment newInstance(String param1, String param2) {
        StoryCreateFragment fragment = new StoryCreateFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public StoryCreateFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreateOptionsMenu (Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.story_create_fragment_menu,menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_create_validate:
                Intent data = new Intent();
                Bundle bundle = new Bundle();
                mStoryTitle = txtStoryTitle.getText().toString();
                bundle.putString(StoryCreateItem.TITLE_TAG,mStoryTitle);
                bundle.putString(StoryCreateItem.AUDIO_TAG,mAudioPath);
                bundle.putString(StoryCreateItem.PHOTO_TAG,imagePathFinal);
                bundle.putString(StoryCreateItem.VIDEO_TAG,mVideoFileUri.toString());
                bundle.putString(StoryCreateItem.STORYTIME_TAG,txtStoryTime.getText().toString());
                data.putExtra("EXTRA_TEST", bundle);
                data.putExtra("EXTRA_OBJ",new StoryCreateItem(mStoryTitle,
                        mAudioPath, imagePathFinal,
                        mVideoFileUri.toString(),txtStoryTime.getText().toString(), new StoryGPSInfo(45.0,4.5)));
                this.getActivity().setResult(getActivity().RESULT_OK,data);
                this.getActivity().finish();
                break;
            case R.id.action_add_audio:
                launchSoundIntent();
                break;
            case R.id.action_add_photo:
                launchCameraIntent();
                break;
            case R.id.action_add_video:
                launchVideoCameraIntent();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mRootView = inflater.inflate(R.layout.story_create_fragment,container,false);

        txtPhotoPath = (EditText)mRootView.findViewById(R.id.txtViewPhotoPath);
        txtAudioPath = (EditText)mRootView.findViewById(R.id.txtTextAudioPath);
        txtVideoPath = (EditText)mRootView.findViewById(R.id.txtViewVideoPath);
        txtStoryTime = (EditText)mRootView.findViewById(R.id.editTexStoryTime);
        txtStoryTitle = (EditText)mRootView.findViewById(R.id.editTextStoryTitle);



        btnAddStoryTime = (ImageButton)mRootView.findViewById(R.id.imgBtnAddDate);

        btnAddStoryTime.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment fragment = new DatePickerFragment();
                fragment.show(getFragmentManager(),"dateTimePicker");
            }
        });

        return mRootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setHasOptionsMenu(true);
        registerForContextMenu(mRootView);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    String imagePathFinal = "";
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == StoryCreateActivity.CAMERA_PIC_REQUEST) {
            if (resultCode == StoryCreateActivity.RESULT_OK) {
                // Image captured and saved to fileUri specified in the Intent
                if((null != data)
                        && (null != data.getData()))
                    imagePathFinal = data.getData().toString();
                txtPhotoPath.setText(imagePathFinal);
            } else if (resultCode == StoryCreateActivity.RESULT_CANCELED) {
                // User cancelled the image capture
            } else {
                // Image capture failed, advise user
            }
        }
        else if (requestCode == StoryCreateActivity.CAMERA_VIDEO_REQUEST) {
            if (resultCode == StoryCreateActivity.RESULT_OK) {
                // Image captured and saved to fileUri specified in the Intent
                // fileUriFinal = fileUri;
                mVideoFileUri = data.getData();
                txtVideoPath.setText(mVideoFileUri.toString());
            } else if (resultCode == StoryCreateActivity.RESULT_CANCELED) {
                // User cancelled the image capture
            } else {
                // Image capture failed, advise user
            }
        }

    }

    private static Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    private static File getOutputMediaFile(int type) {
        Log.d(LOG_TAG, "getOutputMediaFile() type:" + type);
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        // For future implementation: store videos in a separate directory
        File mediaStorageDir = new File(
                Environment
                        .getExternalStorageDirectory(),
                "iRememberGAGIRev");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US)
                .format(new Date());
        File mediaFile;
        if (type == StoryCreateActivity.MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");
        } else if (type == StoryCreateActivity.MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "VID_" + timeStamp + ".mp4");
        } else if (type == StoryCreateActivity.MEDIA_TYPE_AUDIO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "AUD_" + timeStamp + ".3gp");
        } else {
            Log.e(LOG_TAG, "typ of media file not supported: type was:" + type);
            return null;
        }

        return mediaFile;
    }

    // This function creates a new Intent to launch the Audio Recording Activity

    private void launchSoundIntent() {


       // AudioPlayerDialogFragment audioPlayerDialogFragment = new AudioPlayerDialogFragment(getActivity());
        //audioPlayerDialogFragment.show(getFragmentManager(),"");


        // TODO - Create a new intent to launch the SoundRecordActivity activity
       // Intent intentSoundRecord = new Intent(getActivity().getApplicationContext(), SoundRecordActivity.class);

        // TODO - Use getOutputMediaFile() to create a new
        // filename for this specific sound file
        File soundFile = getOutputMediaFile(StoryCreateActivity.MEDIA_TYPE_AUDIO);
        HandleAudioCreationDialog(soundFile.getAbsolutePath());
        // TODO - Add the filename to the Intent as an extra. Use the Intent-extra name
        // from the SoundRecordActivity class, EXTRA_OUTPUT
        //intentSoundRecord.putExtra(SoundRecordActivity.EXTRA_OUTPUT, soundFile.getAbsolutePath());

        // TODO - Start a new activity for result, using the new intent and the request
        // code MIC_SOUND_REQUEST
        //startActivityForResult(intentSoundRecord, StoryCreateActivity.MIC_SOUND_REQUEST);

    }

    // This function creates a new Intent to launch the built-in Camera activity

    private void launchCameraIntent() {

        // TODO - Create a new intent to launch the MediaStore, Image capture function
        // Hint: use standard Intent from MediaStore class
        // See: http://developer.android.com/reference/android/provider/MediaStore.html
        Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // TODO - Set the imagePath for this image file using the pre-made function
        // getOutputMediaFile to create a new filename for this specific image;
        File imageFile  = getOutputMediaFile(StoryCreateActivity.MEDIA_TYPE_IMAGE);

        // TODO - Add the filename to the Intent as an extra. Use the Intent-extra name
        // from the MediaStore class, EXTRA_OUTPUT
        Uri uri = getOutputMediaFileUri(StoryCreateActivity.MEDIA_TYPE_IMAGE);
        imagePathFinal = uri.toString();
        intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, uri);

        // TODO - Start a new activity for result, using the new intent and the request
        // code CAMERA_PIC_REQUEST
        startActivityForResult(intentCamera, StoryCreateActivity.CAMERA_PIC_REQUEST);

    }

    // This function creates a new Intent to launch the built-in Video Camera activity
    private void launchVideoCameraIntent() {
        // TODO - Create a new intent to launch the MediaStore, Image capture function
        // Hint: use standard Intent from MediaStore class
        // See: http://developer.android.com/reference/android/provider/MediaStore.html
        Intent intentVideoCamera = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

        // TODO - Set the fileUri for this video file using the pre-made function
        // getOutputMediaFile to create a new filename for this specific video;
        File videoFile = getOutputMediaFile(StoryCreateActivity.MEDIA_TYPE_VIDEO);


        // TODO - Add the filename to the Intent as an extra. Use the Intent-extra name
        // from the MediaStore class, EXTRA_OUTPUT
        intentVideoCamera.putExtra(MediaStore.EXTRA_OUTPUT, videoFile.getPath());

        // TODO - Specify as an extra that the video quality should be HIGH. Use the
        // Intent-extra name, EXTRA_VIDEO_QUALITY, from the MediaStore class
        // set the video image quality to high
        intentVideoCamera.putExtra(MediaStore.EXTRA_VIDEO_QUALITY,1 );

        // TODO - Start a new activity for result, using the new intent and the request
        // code CAMERA_VIDEO_REQUEST
        startActivityForResult(intentVideoCamera, StoryCreateActivity.CAMERA_VIDEO_REQUEST);


    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    static void setStringDate(int year, int monthOfYear, int dayOfMonth){

        // Increment monthOfYear for Calendar/Date -> Time Format setting
        monthOfYear++;
        String mon = "" + monthOfYear;
        String day = "" + dayOfMonth;

        if (monthOfYear < 10)
            mon = "0" + monthOfYear;
        if (dayOfMonth < 10)
            day = "0" + dayOfMonth;

        txtStoryTime.setText(year + "-" + mon + "-" + day);

    }


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
            txtAudioPath.setText(mAudioPath);
        }
    }
    private void startRecording() {
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.DEFAULT);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setOutputFile(mAudioPath);
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
            mPlayer.setDataSource(mAudioPath);
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

    boolean mRecStarted = false;
    boolean mPlayerStart = false;
    void HandleAudioCreationDialog(String audioPath)
    {
        mAudioPath = audioPath;
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Add the buttons
        builder.setMessage(R.string.audio_player_dialog_title)
                .setPositiveButton(R.string.start_rec_audio_player, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                })
                .setNegativeButton(R.string.exit_audio_player_registration, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
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
                    startRegistrationButton.setText(R.string.stop_audio_rec_registration);
                }
                else
                {
                    startRegistrationButton.setText(R.string.start_rec_audio_player);
                    //Intent data = new Intent();
                    //data.putExtra("data", mFileName)
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
                mPlayerStart = !mPlayerStart;
                onPlay(mPlayerStart);
                if(mPlayerStart)
                    startPlayerButton.setText(R.string.stop_audio_player_registration);
                else
                    startPlayerButton.setText(R.string.start_audio_player_registration);
            }
        });
    }
}
