package com.gigi.mobile.giditestjava.models;

import com.google.gson.annotations.SerializedName;

public class Weather {

    public Weather(int id, String main, String description, String icon) {
        this.id = id;
        this.main = main;
        this.description = description;
        this.icon = icon;
    }

    public Weather(){

    }

    @SerializedName("id")
    private int id;

    @SerializedName("main")
    private String main;

    @SerializedName("description")
    private String description;

    @SerializedName("icon")
    private String icon;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getMain() {
        return main;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getIcon() {
        return icon;
    }

    @Override
    public String toString() {
        return
                "Weather{" +
                        "id = '" + id + '\'' +
                        ",main = '" + main + '\'' +
                        ",description = '" + description + '\'' +
                        ",icon = '" + icon + '\'' +
                        "}";
    }
}