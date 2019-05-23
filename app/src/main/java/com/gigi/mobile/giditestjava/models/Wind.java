package com.gigi.mobile.giditestjava.models;

import com.google.gson.annotations.SerializedName;

public class Wind {

    public Wind(double speed, double deg) {
        this.speed = speed;
        this.deg = deg;
    }

    public Wind(){

    }

    @SerializedName("speed")
    private double speed;

    @SerializedName("deg")
    private double deg;

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getSpeed() {
        return speed;
    }

    public void setDeg(double deg) {
        this.deg = deg;
    }

    public double getDeg() {
        return deg;
    }

    @Override
    public String toString() {
        return
                "Wind{" +
                        "speed = '" + speed + '\'' +
                        ",deg = '" + deg + '\'' +
                        "}";
    }
}
