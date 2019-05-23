package com.gigi.mobile.giditestjava.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import androidx.room.PrimaryKey;

public class Data {

    public Data(int id, long dt, Main main, List<Weather> weather, Clouds clouds, Wind wind, Rain rain, String dtTxt) {
        this.id = id;
        this.dt = dt;
        this.main = main;
        this.weather = weather;
        this.clouds = clouds;
        this.wind = wind;
        this.rain = rain;
        this.dtTxt = dtTxt;
    }

    public Data(){

    }

    @PrimaryKey
    private int id;

    @SerializedName("dt")
    private long dt;

    @SerializedName("main")
    private Main main;

    @SerializedName("weather")
    private List<Weather> weather;

    @SerializedName("clouds")
    private Clouds clouds;

    @SerializedName("wind")
    private Wind wind;

    @SerializedName("rain")
    private Rain rain;

    @SerializedName("dt_txt")
    private String dtTxt;

    public void setDt(long dt) {
        this.dt = dt;
    }

    public long getDt() {
        return dt;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public Main getMain() {
        return main;
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public void setClouds(Clouds clouds) {
        this.clouds = clouds;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public Wind getWind() {
        return wind;
    }

    public void setRain(Rain rain) {
        this.rain = rain;
    }

    public Rain getRain() {
        return rain;
    }

    public void setDtTxt(String dtTxt) {
        this.dtTxt = dtTxt;
    }

    public String getDtTxt() {
        return dtTxt;
    }

    @Override
    public String toString() {
        return
                "Data{" +
                        "dt = '" + dt + '\'' +
                        ",main = '" + main + '\'' +
                        ",weather = '" + weather + '\'' +
                        ",clouds = '" + clouds + '\'' +
                        ",wind = '" + wind + '\'' +
                        ",rain = '" + rain + '\'' +
                        ",dt_txt = '" + dtTxt + '\'' +
                        "}";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
