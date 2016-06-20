package com.example.craftbeermob.Models;

import com.example.craftbeermob.Interfaces.HasPhoto;
import com.example.craftbeermob.Interfaces.IObject;

import java.util.Date;

/**
 * Created by ret70 on 6/14/2016.
 */
public class RecentActivity implements IObject, HasPhoto {

    @com.google.gson.annotations.SerializedName("PhotoId")
    private
    String PhotoId;

    @com.google.gson.annotations.SerializedName("Username")
    private
    String Username;

    @com.google.gson.annotations.SerializedName("Title")
    private
    String Title;

    @com.google.gson.annotations.SerializedName("Brewery_Info")
    private
    String Brewery_Info;

    @com.google.gson.annotations.SerializedName("PhotoURI")
    private
    String PhotoURI;

    @com.google.gson.annotations.SerializedName("createdAt")
    private
    Date createdAt;

    @com.google.gson.annotations.SerializedName("UserId")
    private
    String UserId;

    @com.google.gson.annotations.SerializedName("id")
    private
    String id;


    public RecentActivity(String photoId) {

        PhotoId = photoId;
        //TODO:change the user stuff once we actually implement it
        Username = "testuser";
        UserId = "1234";
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

    public String getTitle() {
        return Title;
    }


    public void setTitle(String title) {
        Title = title;
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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }


    @Override
    public String getPhotoID() {
        return getPhotoId();
    }

    @Override
    public String getUserID() {
        return getUserID();
    }
}
