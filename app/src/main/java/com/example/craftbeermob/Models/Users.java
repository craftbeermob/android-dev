package com.example.craftbeermob.Models;

import com.example.craftbeermob.Interfaces.HasPhoto;
import com.example.craftbeermob.Interfaces.IObject;

/**
 * Created by ret70 on 6/14/2016.
 */
public class Users implements IObject, HasPhoto {
    @com.google.gson.annotations.SerializedName("id")
    String id;
    @com.google.gson.annotations.SerializedName("Username")
    private String Username;
    @com.google.gson.annotations.SerializedName("UserId")
    private String UserId;
    @com.google.gson.annotations.SerializedName("ProfilePictureURI")
    private
    String ProfilePictureUri;
    @com.google.gson.annotations.SerializedName("ProfilePictureId")
    private
    String ProfilePictureId;

    @com.google.gson.annotations.SerializedName("CurrentRank")
    private
    String CurrentRank;

    @com.google.gson.annotations.SerializedName("Points")
    private
    String Points;



    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getProfilePictureUri() {
        return ProfilePictureUri;
    }

    public void setProfilePictureUri(String profilePictureUri) {
        ProfilePictureUri = profilePictureUri;
    }

    @Override
    public Users returnObj() {
        return this;
    }

    @Override
    public String getPhotoID() {
        return getProfilePictureId();
    }

    @Override
    public String getUserID() {
        return getUserId();
    }

    public String getProfilePictureId() {
        return ProfilePictureId;
    }

    public void setProfilePictureId(String profilePictureId) {
        ProfilePictureId = profilePictureId;
    }

    public String getCurrentRank() {
        return CurrentRank;
    }

    public void setCurrentRank(String currentRank) {
        CurrentRank = currentRank;
    }

    public String getPoints() {
        return Points;
    }

    public void setPoints(String points) {
        Points = points;
    }
}
