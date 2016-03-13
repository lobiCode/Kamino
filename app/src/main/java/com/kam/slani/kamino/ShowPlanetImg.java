package com.kam.slani.kamino;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

public class ShowPlanetImg extends AppCompatActivity implements SetImg {

    private static final String TAG = ShowPlanetImg.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_show_planet_img);

        Intent intent = getIntent();
        String img_url = intent.getStringExtra(MainActivity.IMG_URL);
        DownloadImg downloadImg = new DownloadImg(this);
        downloadImg.execute(img_url);

    }

    @Override
    public void setImg(Bitmap bitmap) {

        ImageView imageView = (ImageView) findViewById(R.id.kamino_img_big);
        imageView.setImageBitmap(bitmap);
    }
}
