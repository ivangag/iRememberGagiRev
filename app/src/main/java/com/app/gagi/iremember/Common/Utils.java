package com.app.gagi.iremember.Common;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;

import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Created by igaglioti on 27/03/14.
 */
public class Utils {

    private  static Utils utility;

    public static Utils getInstance()
    {
        if(utility == null)
            utility = new Utils();
        return utility;
    }

    String LOG_TAG = Utils.class.getCanonicalName();
    Bitmap ScaleBitmap(Bitmap bitmap, double scaleSize)
    {
        int nh = (int) ( bitmap.getHeight() * (scaleSize/ bitmap.getWidth()) );
        Bitmap scaled = Bitmap.createScaledBitmap(bitmap, (int)scaleSize, nh, true);
        return  scaled;
    }

    Bitmap photoBitmap;
    public Bitmap getScaledImageBitmap(Context context, String imageMetaDataPath, double scaleSize)
    {
        try {
            InputStream input = context.getContentResolver().openInputStream(Uri
                    .parse(imageMetaDataPath));

            if (input != null) {

                photoBitmap = BitmapFactory.decodeStream(input);

            }
        } catch (FileNotFoundException e) {

            Log.i(LOG_TAG, "FileNotFoundException");

        }

        return ScaleBitmap(photoBitmap,scaleSize);
    }
}
