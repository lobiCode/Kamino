package com.kam.slani.kamino;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by slani on 12.3.2016.
 */
public class DownloadImg extends AsyncTask<String, Integer, Bitmap> {


    private WeakReference<SetImg> setImageWeakReference;

    public DownloadImg(SetImg setKaminoView) {

       setImageWeakReference = new WeakReference<SetImg>(setKaminoView);
    }

    @Override
    protected Bitmap doInBackground(String... params) {

        URL urlImage;
        Bitmap bitmap  = null;

        try {

            urlImage = new URL(params[0]);
            HttpURLConnection http = (HttpURLConnection) urlImage.openConnection();
            InputStream in = http.getInputStream();
            bitmap = BitmapFactory.decodeStream(in);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {

        SetImg setKaminoView = setImageWeakReference.get();
        if (setKaminoView != null) {
            setKaminoView.setImg(bitmap);
        }
    }
}
