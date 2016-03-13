package com.kam.slani.kamino.kamino;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.kam.slani.kamino.KaminoRestClient;
import com.kam.slani.kamino.ServiceGenerator;

import org.json.JSONObject;

import java.io.IOException;
import java.security.KeyManagementException;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by slani on 13.3.2016.
 */
public class LikeAdapter extends TypeAdapter<LikeResponse>{

    @Override
    public void write(JsonWriter out, LikeResponse value) throws IOException {
    }

    @Override
    public LikeResponse read(JsonReader in) throws IOException {

        if (in.peek() == JsonToken.NULL) {
            in.nextNull();
            return null;
        }

        LikeResponse likeResponse = new LikeResponse();
        in.beginObject();

        while (in.hasNext()) {
            String name = in.nextName();
            if (name.equals("planet_id")) {
                int id = in.nextInt();
                likeResponse.setPlanet_id(id);
            } else if (name.equals("likes ")) {
                int likes = in.nextInt();
                likeResponse.setLikes(likes);
                in.endObject();
            }
        }
        return likeResponse;
    }
}
