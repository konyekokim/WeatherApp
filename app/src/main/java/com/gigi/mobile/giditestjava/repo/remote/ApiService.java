package com.gigi.mobile.giditestjava.repo.remote;

import com.gigi.mobile.giditestjava.models.CurrentResponse;

import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("weather")
    Single<Response<CurrentResponse>> getCurrentWeather(@Query("q") String q,
                                                        @Query("units") String unit,
                                                        @Query("APPID") String key);

    @GET("weather")
    Single<retrofit2.Response<CurrentResponse>> getCurrentWeatherByLatLng(@Query("lat") double lat,
                                                                          @Query("lon") double lng,
                                                                          @Query("units") String unit,
                                                                          @Query("APPID") String key);

    @GET("forecast")
    Single<retrofit2.Response<com.gigi.mobile.giditestjava.models.Response>> getForecastByCity(@Query("q") String q,
                                                                                               @Query("units") String unit,
                                                                                               @Query("APPID") String key);

    @GET("forecast")
    Single<retrofit2.Response<com.gigi.mobile.giditestjava.models.Response>> getForecastByLatLng(@Query("lat") double lat,
                                                                                                 @Query("lon") double lng,
                                                                                                 @Query("units") String unit,
                                                                                                 @Query("APPID") String key);

}
