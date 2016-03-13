package com.kam.slani.kamino.kamino;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by slani on 12.3.2016.
 */
public class Resident implements Parcelable {

    private String name;
    private String height;
    private String mass;
    private String hair_color;
    private String skin_color;
    private String eye_color;
    private String birth_year;
    private String gender;
    private String homeworld;
    private Date created;
    private Date edited;
    private String image_url;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getMass() {
        return mass;
    }

    public void setMass(String mass) {
        this.mass = mass;
    }

    public String getHair_color() {
        return hair_color;
    }

    public void setHair_color(String hair_color) {
        this.hair_color = hair_color;
    }

    public String getSkin_color() {
        return skin_color;
    }

    public void setSkin_color(String skin_color) {
        this.skin_color = skin_color;
    }

    public String getEye_color() {
        return eye_color;
    }

    public void setEye_color(String eye_color) {
        this.eye_color = eye_color;
    }

    public String getBirth_year() {
        return birth_year;
    }

    public void setBirth_year(String birth_year) {
        this.birth_year = birth_year;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getHomeworld() {
        return homeworld;
    }

    public void setHomeworld(String homeworld) {
        this.homeworld = homeworld;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getEdited() {
        return edited;
    }

    public void setEdited(Date edited) {
        this.edited = edited;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(name);
        dest.writeString(height);
        dest.writeString(mass);
        dest.writeString(hair_color);
        dest.writeString(skin_color);
        dest.writeString(eye_color);
        dest.writeString(birth_year);
        dest.writeString(gender);
        dest.writeString(homeworld);
        dest.writeString(created.toString());
        dest.writeString(edited.toString());
        dest.writeString(image_url);
    }

    public static final Parcelable.Creator<Resident> CREATOR
            = new Parcelable.Creator<Resident>() {
        public Resident createFromParcel(Parcel in) {
            return new Resident(in);
        }

        public Resident[] newArray(int size) {
            return new Resident[size];
        }
    };

    private Resident(Parcel in) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);

        name  = in.readString();
        height  = in.readString();
        mass  = in.readString();
        hair_color  = in.readString();
        skin_color  = in.readString();
        eye_color  = in.readString();
        birth_year  = in.readString();
        gender  = in.readString();
        homeworld  = in.readString();
        try {
            created  = format.parse(in.readString());
            edited  = format.parse(in.readString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        image_url  = in.readString();
    }

    public Resident () {

    }
}
