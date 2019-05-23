package com.gigi.mobile.giditestjava.repo.offline;

import com.gigi.mobile.giditestjava.models.City;
import com.gigi.mobile.giditestjava.models.Clouds;
import com.gigi.mobile.giditestjava.models.Data;
import com.gigi.mobile.giditestjava.models.Main;
import com.gigi.mobile.giditestjava.models.Rain;
import com.gigi.mobile.giditestjava.models.Sys;
import com.gigi.mobile.giditestjava.models.Weather;
import com.gigi.mobile.giditestjava.models.Wind;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import androidx.room.TypeConverter;

public class Converters {

    @TypeConverter
    public static String fromClouds(Clouds clouds) {
        return new Gson().toJson(clouds);
    }

    @TypeConverter
    public static Clouds toClouds(String str) {
        return new Gson().fromJson(str, Clouds.class);
    }

    @TypeConverter
    public static String fromSys(Sys sys){
        return new Gson().toJson(sys);
    }

    @TypeConverter
    public static Sys toSys(String str){
        return new Gson().fromJson(str, Sys.class);
    }

    @TypeConverter
    public static String fromWind(Wind wind) {
        return new Gson().toJson(wind);
    }

    @TypeConverter
    public static Wind toWind(String str) {
        return new Gson().fromJson(str, Wind.class);
    }

    @TypeConverter
    public static String fromMain(Main main) {
        return new Gson().toJson(main);
    }

    @TypeConverter
    public static Main toMain(String str) {
        return new Gson().fromJson(str, Main.class);
    }

    @TypeConverter
    public static String fromRain(Rain rain) {
        return new Gson().toJson(rain);
    }

    @TypeConverter
    public static Rain toRain(String str) {
        return new Gson().fromJson(str, Rain.class);
    }

    @TypeConverter
    public static String fromList(List<Weather> weathers) {
        return new Gson().toJson(weathers);
    }

    @TypeConverter
    public static List<Weather> toList(String str) {
        Type type = new TypeToken<List<Weather>>(){}.getType();
        return new Gson().fromJson(str, type);
    }

    @TypeConverter
    public static String fromData(List<Data> data) {
        return  new Gson().toJson(data);
    }

    @TypeConverter
    public static List<Data> toData(String str) {
        Type type = new TypeToken<List<Data>>(){}.getType();
        return new Gson().fromJson(str, type);
    }

    @TypeConverter
    public static String fromCity(City city) {
        return new Gson().toJson(city);
    }

    @TypeConverter
    public static City toCity(String str) {
        return new Gson().fromJson(str, City.class);
    }

}
