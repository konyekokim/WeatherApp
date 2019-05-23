package com.gigi.mobile.giditestjava.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "forecasts")
public class Response {

    public Response() {
    }

    public Response(int id, String name, String cod, String country, int cnt, List<Data> list, City city) {
        this.id = id;
        this.name = name;
        this.cod = cod;
        this.country = country;
        this.cnt = cnt;
        this.list = list;
        this.city = city;
    }

    @PrimaryKey
    @NonNull
    private int id = 1;

    private String name;

    @SerializedName("cod")
    private String cod;

    @SerializedName("country")
    private String country;

    @SerializedName("cnt")
    private int cnt;

    @SerializedName("list")
    private List<Data> list;

    @SerializedName("city")
    private City city;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public String getCod() {
        return cod;
    }

    public void setCountry(String country){
        this.country = country;
    }

    public String getCountry(){
        return country;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

    public int getCnt() {
        return cnt;
    }

    public void setList(List<Data> list) {
        this.list = list;
    }

    public List<Data> getList() {
        return list;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public City getCity() {
        return city;
    }

    @Override
    public String toString() {
        return
                "Response{" +
                        "cod = '" + cod + '\'' +
                        ",cnt = '" + cnt + '\'' +
                        ",list = '" + list + '\'' +
                        ",city = '" + city + '\'' +
                        "}";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
