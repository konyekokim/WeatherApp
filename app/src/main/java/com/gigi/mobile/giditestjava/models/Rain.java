package com.gigi.mobile.giditestjava.models;

import com.google.gson.annotations.SerializedName;

public class Rain {
    public Rain(double mm3h) {
        this.mm3h = mm3h;
    }

    public Rain(){

    }

    @SerializedName("3h")
    private double mm3h;

    public double getMm3h() {
        return mm3h;
    }

    public void setMm3h(double mm3h) {
        this.mm3h = mm3h;
    }

    @Override
    public String toString() {
        return "Rain{" +
                ", mm3h=" + mm3h +
                '}';
    }
}
