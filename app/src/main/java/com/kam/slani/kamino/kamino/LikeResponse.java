package com.kam.slani.kamino.kamino;

import com.google.gson.annotations.SerializedName;

/**
 * Created by slani on 12.3.2016.
 */
public class LikeResponse {

    int planet_id;

    @SerializedName("likes ")
    int likes;

    public int getPlanet_id() {
        return planet_id;
    }

    public void setPlanet_id(int planet_id) {
        this.planet_id = planet_id;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }
}
