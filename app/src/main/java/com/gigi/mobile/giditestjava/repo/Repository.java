package com.gigi.mobile.giditestjava.repo;

import com.gigi.mobile.giditestjava.BuildConfig;
import com.gigi.mobile.giditestjava.models.CurrentResponse;
import com.gigi.mobile.giditestjava.models.Response;
import com.gigi.mobile.giditestjava.repo.offline.CurrentDao;
import com.gigi.mobile.giditestjava.repo.offline.ForecastDao;
import com.gigi.mobile.giditestjava.repo.remote.ApiService;
import com.gigi.mobile.giditestjava.utils.scheduler.SchedulerProvider;

import javax.inject.Inject;
import io.reactivex.Completable;
import io.reactivex.Single;

public class Repository {

    private ApiService apiService;
    private CurrentDao currentDao;
    private ForecastDao dataDao;
    private SchedulerProvider provider;

    /**
     * Injecting necessary components necessary for Repository
     */
    @Inject
    public Repository(ApiService apiService, CurrentDao currentDao, ForecastDao dataDao, SchedulerProvider provider) {
        this.apiService = apiService;
        this.currentDao = currentDao;
        this.dataDao = dataDao;
        this.provider = provider;
    }

    /**
     * Data from API endpoints
     */

    public Single<retrofit2.Response<Response>> getForecast(String cityName){
        return apiService.getForecastByCity(cityName, "metric", BuildConfig.APPID);
    }

    public Single<retrofit2.Response<Response>> getForecastGeo(double lat, double lon){
        return apiService.getForecastByLatLng(lat, lon, "metric", BuildConfig.APPID);
    }

    public Single<retrofit2.Response<CurrentResponse>> getWeather(String cityName) {
        return apiService.getCurrentWeather(cityName, "metric", BuildConfig.APPID);
    }

    public Single<retrofit2.Response<CurrentResponse>> getWeatherGeo(double lat, double lon) {
        return apiService.getCurrentWeatherByLatLng(lat, lon, "metric", BuildConfig.APPID);
    }

    /**
     * Data from and to offline storage
     */

    public Single<CurrentResponse> currentFromDb(){
        return currentDao.getCurrent()
                .subscribeOn(provider.io())
                .observeOn(provider.io());
    }

    public Single<Response> forecastFromDb(){
        return dataDao.getDataById()
                .subscribeOn(provider.io())
                .observeOn(provider.io());
    }

    public Completable insertCurrentToDb(CurrentResponse response){
        return currentDao.insertCurrent(response)
                .subscribeOn(provider.io())
                .observeOn(provider.io());
    }

    public Completable insertForecastToDb(Response response){
        return dataDao.insertForeCastData(response)
                .subscribeOn(provider.io())
                .observeOn(provider.io());
    }

}
