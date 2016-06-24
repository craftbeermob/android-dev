package com.example.craftbeermob.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.craftbeermob.Interfaces.HasPhoto;
import com.example.craftbeermob.Interfaces.IObject;

/**
 * Created by ret70 on 6/14/2016.
 */
public class RecentActivity implements IObject, HasPhoto, Parcelable {

    public static final Parcelable.Creator<RecentActivity> CREATOR = new Parcelable.Creator<RecentActivity>() {

        @Override
        public RecentActivity createFromParcel(Parcel source) {
            return new RecentActivity(source);
        }

        @Override
        public RecentActivity[] newArray(int size) {
            return new RecentActivity[size];
        }
    };
    @com.google.gson.annotations.SerializedName("PhotoId")
    private
    String PhotoId;
    @com.google.gson.annotations.SerializedName("Username")
    private
    String Username;
    @com.google.gson.annotations.SerializedName("Comment")
    private
    String Comment;
    @com.google.gson.annotations.SerializedName("BeerType")
    private
    String BeerType;
    @com.google.gson.annotations.SerializedName("Brewery_Info")
    private
    String Brewery_Info;
    @com.google.gson.annotations.SerializedName("PhotoURI")
    private
    String PhotoURI;
    @com.google.gson.annotations.SerializedName("createdAt")
    private
    String createdAt;
    @com.google.gson.annotations.SerializedName("Rating")
    private
    float Rating;
    @com.google.gson.annotations.SerializedName("UserId")
    private
    String UserId;
    @com.google.gson.annotations.SerializedName("id")
    private
    String id;



    public RecentActivity(String photoId) {

        PhotoId = photoId;


    }

    RecentActivity(Parcel in) {
        this.Username = in.readString();
        this.BeerType = in.readString();
        this.PhotoURI = in.readString();
        this.PhotoId = in.readString();
        this.Brewery_Info = in.readString();
        this.Comment = in.readString();
        this.UserId = in.readString();
        this.Rating = in.readFloat();

    }

    public RecentActivity returnObj() {
        return this;
    }

    public String getPhotoId() {
        return PhotoId;
    }

    public void setPhotoId(String photoId) {
        PhotoId = photoId;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }

    public String getBrewery_Info() {
        return Brewery_Info;
    }

    public void setBrewery_Info(String brewery_Info) {
        Brewery_Info = brewery_Info;
    }

    public String getPhotoURI() {
        return PhotoURI;
    }

    public void setPhotoURI(String photoURI) {
        PhotoURI = photoURI;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String getPhotoID() {
        return getPhotoId();
    }

    @Override
    public String getUserId() {
        return getUserId();
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public float getRating() {
        return Rating;
    }

    public void setRating(float rating) {
        Rating = rating;
    }

    public String getBeerType() {
        return BeerType;
    }

    public void setBeerType(String beerType) {
        BeerType = beerType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Username);
        dest.writeString(BeerType);
        dest.writeString(PhotoURI);
        dest.writeString(PhotoId);
        dest.writeString(Brewery_Info);
        dest.writeString(Comment);
        dest.writeString(UserId);
        dest.writeFloat(Rating);
        dest.writeString(id);

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
