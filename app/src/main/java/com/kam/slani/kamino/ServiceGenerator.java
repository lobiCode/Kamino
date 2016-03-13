package com.kam.slani.kamino;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kam.slani.kamino.kamino.LikeAdapter;
import com.kam.slani.kamino.kamino.LikeResponse;
import com.squareup.okhttp.OkHttpClient;

import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;

/**
 * Created by slani on 11.3.2016.
 */
public class ServiceGenerator {


    public static final String KAMINO_BASE_URL = "http://private-anon-89a05ade5-starwars2.apiary-mock.com";

    private static Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            //.registerTypeAdapter(LikeResponse.class, new LikeAdapter())
            .create();

    private static RestAdapter.Builder builder = new RestAdapter.Builder()
            .setEndpoint(KAMINO_BASE_URL)
            .setConverter(new GsonConverter(gson))
            //.setLogLevel(RestAdapter.LogLevel.FULL)
            .setClient(new OkClient(new OkHttpClient()));

    public static <S> S createService(Class<S> serviceClass) {
        RestAdapter adapter = builder.build();
        return adapter.create(serviceClass);
    }
}
