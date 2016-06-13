package com.example.craftbeermob.Models;

import com.example.craftbeermob.Interfaces.IObject;

/**
 * Created by ret on 6/4/16.
 */
public class Leaderboard implements IObject {


    @com.google.gson.annotations.SerializedName("Username")
    private String Username;

    @com.google.gson.annotations.SerializedName("Badges")
    private String Badges;

    @com.google.gson.annotations.SerializedName("Points")
    private Integer Points;

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getBadges() {
        return Badges;
    }

    public void setBadges(String badges) {
        Badges = badges;
    }

    public Integer getPoints() {
        return Points;
    }

    public void setPoints(Integer points) {
        Points = points;
    }

    @Override
    public Object returnObj() {
        return this;
    }
}
