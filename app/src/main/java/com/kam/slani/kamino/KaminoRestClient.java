package com.kam.slani.kamino;

import com.kam.slani.kamino.kamino.LikeResponse;
import com.kam.slani.kamino.kamino.Planet;
import com.kam.slani.kamino.kamino.Resident;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by slani on 11.3.2016.
 */
public interface KaminoRestClient {

    public static final String PLANET = "/planets/10";
    public static final String LIKE = "/planets/10/like";
    public static final String RES = "/residents/{id}";

    @GET(PLANET)
    void getPlanetInfo(Callback<Planet> planet);

    @POST(LIKE)
    void likePlanet(@Body Integer planet_id, Callback<LikeResponse> likeResponse);

    @GET(RES)
    Resident getResident(@Path("id") int id);
}
