package com.gigi.mobile.giditestjava.models;

import com.google.gson.annotations.SerializedName;

public class Clouds {

    public Clouds(int all) {
        this.all = all;
    }

    public Clouds(){

    }

    @SerializedName("all")
    private int all;

    public void setAll(int all) {
        this.all = all;
    }

    public int getAll() {
        return all;
    }

    @Override
    public String toString() {
        return
                "Clouds{" +
                        "all = '" + all + '\'' +
                        "}";
    }
}