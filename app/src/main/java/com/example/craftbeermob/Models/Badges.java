package com.example.craftbeermob.Models;

import com.example.craftbeermob.Interfaces.HasPhoto;
import com.example.craftbeermob.Interfaces.IObject;

/**
 * Created by ret70 on 6/19/2016.
 */
public class Badges implements IObject, HasPhoto {

    @com.google.gson.annotations.SerializedName("BadgeId")
    private String BadgeId;

    @com.google.gson.annotations.SerializedName("UserId")
    private String UserId;

    @com.google.gson.annotations.SerializedName("BadgeUri")
    private String BadgeUri;

    @com.google.gson.annotations.SerializedName("Id")
    private String Id;

    public Badges returnObj() {
        return this;
    }

    public String getBadgeId() {
        return BadgeId;
    }

    public void setBadgeId(String badgeId) {
        BadgeId = badgeId;
    }

    public String getBadgeUri() {
        return BadgeUri;
    }

    public void setBadgeUri(String badgeUri) {
        BadgeUri = badgeUri;
    }

    @Override
    public String getPhotoID() {
        return getBadgeId();
    }

    @Override
    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }
}
