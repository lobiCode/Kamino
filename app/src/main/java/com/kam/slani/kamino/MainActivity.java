package com.kam.slani.kamino;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kam.slani.kamino.kamino.LikeResponse;
import com.kam.slani.kamino.kamino.Planet;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity implements SetImg,
        View.OnClickListener {

    public static final String IMG_URL = "img_url";
    public static final String LIKE_PLANET = "like";
    public static final String RESIDENTS = "residents";


    private static final String TAG = MainActivity.class.toString();

    private Planet planetKamino;

    private ImageView kamino_img;
    private TextView planet_name, rotation_period,
            orbital_period, diameter,
            climate, gravity, terrain,
            surface_water, population,
            likes;
    private Bitmap kamino_bitmap;
    private Button residentsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        planet_name = (TextView) findViewById(R.id.planet_name);
        rotation_period = (TextView) findViewById(R.id.rotation_period);
        orbital_period = (TextView) findViewById(R.id.orbital_period);
        diameter = (TextView) findViewById(R.id.diameter);
        climate = (TextView) findViewById(R.id.climate);
        gravity = (TextView) findViewById(R.id.gravity);
        terrain = (TextView) findViewById(R.id.terrain);
        surface_water = (TextView) findViewById(R.id.surface_water);
        population = (TextView) findViewById(R.id.population);
        likes = (TextView) findViewById(R.id.n_likes);
        kamino_img = (ImageView) findViewById(R.id.kamino_img);
        residentsButton = (Button) findViewById(R.id.residents_button);

        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            getPlanetInfo();
        } else {
            Toast.makeText(this, getString(R.string.no_network),
                    Toast.LENGTH_LONG).show();
            // TODO
        }


    }

    private void getPlanetInfo() {

        KaminoRestClient kaminoRestClient = ServiceGenerator
                .createService(KaminoRestClient.class);

        kaminoRestClient.getPlanetInfo(new Callback<Planet>() {
            @Override
            public void success(Planet planet, Response response) {

                planetKamino = planet;
                getPlanetImg();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e(TAG, error.toString());
            }
        });
    }


    private void getPlanetImg() {
        DownloadImg downloadImg = new DownloadImg(this);
        downloadImg.execute(planetKamino.getImage_url());
    }

    @Override
    public void setImg(Bitmap bitmap) {

        kamino_bitmap = bitmap;
        setPlanetInfoView();

    }
    private void setPlanetInfoView() {

        DisplayMetrics display = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(display);
        int size = (display.widthPixels > display.heightPixels)
                ? display.widthPixels : display.heightPixels;
        Bitmap bitmapScaled = Bitmap.createScaledBitmap(kamino_bitmap,
                size/4, size/4, true);

        kamino_img.setOnClickListener(this);

        kamino_img.setImageBitmap(bitmapScaled);
        planet_name.setText(getString(R.string.planet_name)
                + " " + planetKamino.getName());
        rotation_period.setText(getString(R.string.rotation_period)
                + " " + planetKamino.getRotation_period());
        orbital_period.setText(getString(R.string.orbital_period)
                +  " " + planetKamino.getOrbital_period());
        diameter.setText(getString(R.string.diameter)
                +  " " + planetKamino.getDiameter());
        climate.setText(getString(R.string.climate)
                +  " " + planetKamino.getClimate());
        gravity.setText(getString(R.string.gravity)
                +  " " + planetKamino.getGravity());
        terrain.setText(getString(R.string.terrain)
                +  " " + planetKamino.getTerrain());
        surface_water.setText(getString(R.string.surface_water)
                +  " " + planetKamino.getSurface_water() + " %");
        population.setText(getString(R.string.population)
                +  " " + planetKamino.getPopulation());
        likes.setText(getString(R.string.likes)
                + " " + planetKamino.getLikes());

        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        boolean l = preferences.getBoolean(LIKE_PLANET, false);
        if (!l) {
            Button likeButton = (Button) findViewById(R.id.like_button);
            likeButton.setVisibility(View.VISIBLE);
            likeButton.setOnClickListener(this);
        }
        residentsButton.setVisibility(View.VISIBLE);
        residentsButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case (R.id.kamino_img):
                // TODO save img or resize
                Intent intent = new Intent(this, ShowPlanetImg.class);
                intent.putExtra(IMG_URL, planetKamino.getImage_url());
                startActivity(intent);
                break;
            case (R.id.residents_button):
                Intent residentsIntent = new Intent(this, Residents.class);
                residentsIntent.putExtra(RESIDENTS, planetKamino.getResidents());
                startActivity(residentsIntent);
                break;
            case (R.id.like_button):
                like_planet();
                break;
            default:
                break;
        }


    }

    private void like_planet() {
        KaminoRestClient kaminoRestClient = ServiceGenerator
                .createService(KaminoRestClient.class);
        kaminoRestClient.likePlanet(10, new Callback<LikeResponse>() {
            @Override
            public void success(LikeResponse likeResponse, Response response) {
                SharedPreferences preferences = getPreferences(MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean(LIKE_PLANET, true);
                likes.setText(getString(R.string.likes)
                        + " " + likeResponse.getLikes());
                Button likeButton = (Button) findViewById(R.id.like_button);
                likeButton.setVisibility(View.GONE);
            }

            @Override
            public void failure(RetrofitError error) {

                Toast.makeText(getApplicationContext(),
                        "Invalid JSON response", Toast.LENGTH_LONG).show();
                Log.e(TAG, error.toString());
            }
        });

    }
}
