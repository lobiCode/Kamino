package com.kam.slani.kamino;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.kam.slani.kamino.kamino.Resident;

public class ResidentDetail extends AppCompatActivity implements SetImg {

    private ImageView residentImg;
    private TextView name;
    private TextView height;
    private TextView mass;
    private TextView hair_color;
    private TextView skin_color;
    private TextView eye_color;
    private TextView birth_year;
    private TextView gender;

    private Resident resident;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resident_detail);

        residentImg = (ImageView) findViewById(R.id.resident_img);
        name = (TextView) findViewById(R.id.resident_name);
        height = (TextView) findViewById(R.id.resident_height);
        mass = (TextView) findViewById(R.id.resident_mass);
        hair_color = (TextView) findViewById(R.id.resident_hair_color);
        skin_color = (TextView) findViewById(R.id.resident_skin_color);
        eye_color = (TextView) findViewById(R.id.resident_eye_color);
        birth_year = (TextView) findViewById(R.id.birth_year);
        gender =  (TextView) findViewById(R.id.resident_gender);

        Intent intent = getIntent();

        String resJson = intent.getStringExtra(Residents.RESIDENT);
        Gson gson = new Gson();
        resident = gson.fromJson(resJson, Resident.class);

        DownloadImg downloadImg = new DownloadImg(this);
        downloadImg.execute(resident.getImage_url());

    }

    @Override
    public void setImg(Bitmap bitmap) {

        residentImg.setImageBitmap(bitmap);
        name.setText(getString(R.string.resident_name)
                + " " + resident.getName());
        height.setText(getString(R.string.resident_height)
                + " " + resident.getHeight());
        mass.setText(getString(R.string.resident_mass)
                + " " + resident.getMass());
        hair_color.setText(getString(R.string.resident_hair_color)
                + " " + resident.getHair_color());
        skin_color.setText(getString(R.string.resident_skin_color)
                + " " + resident.getSkin_color());
        eye_color.setText(getString(R.string.resident_eye_color)
                + " " + resident.getEye_color());
        birth_year.setText(getString(R.string.resident_birth_year)
                + " " + resident.getBirth_year());
        gender.setText(getString(R.string.resident_gender)
                + " " + resident.getGender());
    }
}
