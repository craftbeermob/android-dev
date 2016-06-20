package com.example.craftbeermob.Models;

import com.example.craftbeermob.Interfaces.IObject;

/**
 * Created by ret on 6/3/16.
 */
public class Missions implements IObject {


    @com.google.gson.annotations.SerializedName("id")
    private String id;

    @com.google.gson.annotations.SerializedName("Description")
    private String Description;

    @com.google.gson.annotations.SerializedName("Title")
    private String Title;

    @com.google.gson.annotations.SerializedName("Status")
    private String Status;






    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    @Override
    public Missions returnObj() {
        return this;
    }
}
