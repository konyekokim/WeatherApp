package com.gigi.mobile.giditestjava.models;

import com.google.gson.annotations.SerializedName;

public class City {

    public City(int id, String name, String country) {
        this.id = id;
        this.name = name;
        this.country = country;
    }

    public City(){

    }

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("country")
    private String country;


    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountry() {
        return country;
    }

    @Override
    public String toString() {
        return
                "City{" +
                        "id = '" + id + '\'' +
                        ",name = '" + name + '\'' +
                        ",country = '" + country + '\'' +
                        "}";
    }
}