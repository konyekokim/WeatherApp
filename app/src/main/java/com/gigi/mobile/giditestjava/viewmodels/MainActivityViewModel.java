package com.gigi.mobile.giditestjava.viewmodels;

import com.gigi.mobile.giditestjava.models.ForecastResponse;
import com.gigi.mobile.giditestjava.models.Response;
import com.gigi.mobile.giditestjava.models.WeatherResponse;
import com.gigi.mobile.giditestjava.repo.Repository;
import com.gigi.mobile.giditestjava.utils.scheduler.SchedulerProvider;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.disposables.CompositeDisposable;

public class MainActivityViewModel extends ViewModel {
    private final Repository repo;
    private SchedulerProvider provider;

    private MutableLiveData<WeatherResponse> weatherMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<ForecastResponse> forecastMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<WeatherResponse> weatherGeoMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<ForecastResponse> forecastGeoMutableLiveData = new MutableLiveData<>();

    private CompositeDisposable disposable = new CompositeDisposable();

    @Inject
    public MainActivityViewModel(Repository repo, SchedulerProvider provider) {
        this.repo = repo;
        this.provider = provider;
    }

    public LiveData<ForecastResponse> getForecast(String cityName) {
        getForecastFromDb(cityName);
        return forecastMutableLiveData;
    }

    public LiveData<WeatherResponse> getCurrentData(String cityName) {
        getWeatherFromDb(cityName);
        return weatherMutableLiveData;
    }

    public LiveData<ForecastResponse> getForecastGeo(double lat, double lon) {
        getForecastGeos(lat, lon);
        return forecastGeoMutableLiveData;
    }

    public LiveData<WeatherResponse> getCurrentDataGeo(double lat, double lon) {
        getWeatherGeos(lat, lon);
        return weatherGeoMutableLiveData;
    }

    public Response getForecastInDb(){
        return repo.forecastFromDb().blockingGet();
    }

    private void getForecasts(String cityName) {
        disposable.add(repo.getForecast(cityName)
                .subscribeOn(provider.io())
                .observeOn(provider.ui())
                .subscribe( response -> {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            response.body().setName(response.body().getCity().getName());
                            disposable.add(repo.insertForecastToDb(response.body()).subscribeOn(provider.io())
                                    .observeOn(provider.io())
                                    .subscribe(() -> forecastMutableLiveData.postValue(new ForecastResponse(response.body(), "")),
                                            error ->
                                                    forecastMutableLiveData.postValue(new ForecastResponse(response.body(), "Failed to save to DB"))
                                    ));
                        }
                    } else if (response.errorBody() != null) {
                        forecastMutableLiveData.postValue(new ForecastResponse(null, response.message()));
                    }
                }, error ->{
                    if(error instanceof SocketTimeoutException || error instanceof ConnectException || error instanceof UnknownHostException) {
                            forecastMutableLiveData.postValue(new ForecastResponse(null, "poor internet"));
                    } else
                        forecastMutableLiveData.postValue(new ForecastResponse(null, "An error occurred"));
                })
        );
    }

    private void getForecastGeos(double lat, double lon) {
        disposable.add(repo.getForecastGeo(lat, lon)
                .subscribeOn(provider.io())
                .observeOn(provider.ui())
                .subscribe(response -> {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            if (response.body().getCity() != null)
                                response.body().setName(response.body().getCity().getName());
                            if (repo.insertForecastToDb(response.body()) != null) {
                                disposable.add(repo.insertForecastToDb(response.body()).subscribeOn(provider.io())
                                        .observeOn(provider.io())
                                        .subscribe(() -> forecastGeoMutableLiveData.postValue(new ForecastResponse(response.body(), "")),
                                                error -> forecastGeoMutableLiveData.postValue(
                                                        new ForecastResponse(response.body(),
                                                                "Failed to save to DB")
                                                )));
                            }
                        }
                    } else if (response.errorBody() != null) {
                        forecastGeoMutableLiveData.postValue(new ForecastResponse(null, response.message()));
                    }
                }, error -> {
                    if (error instanceof SocketTimeoutException || error instanceof ConnectException || error instanceof UnknownHostException) {
                            forecastGeoMutableLiveData.postValue(new ForecastResponse(null, "poor internet"));
                    } else
                        forecastGeoMutableLiveData.postValue(new ForecastResponse(null, "An error occurred"));
                })
        );
    }

    private void getWeathers(String cityName) {
        disposable.add(repo.getWeather(cityName)
                .subscribeOn(provider.io())
                .observeOn(provider.ui())
                .subscribe( response -> {
                    if (response.isSuccessful() &&response.body() != null) {
                        //Save data in offline db
                        disposable.add(repo.insertCurrentToDb(response.body()).subscribeOn(provider.io())
                                .observeOn(provider.io())
                                .subscribe(() -> {
                                    //Get data from db
                                    weatherMutableLiveData.postValue(new WeatherResponse(response.body(), ""));
                                }, error -> weatherMutableLiveData.postValue(
                                        new WeatherResponse(
                                                response.body(),
                                                "An error occurred can't save to db")
                                )));

                    } else if (response.errorBody() != null) {
                        weatherMutableLiveData.postValue(new WeatherResponse(null, response.message()));
                    }
                }, error ->{
                    if(error instanceof SocketTimeoutException || error instanceof ConnectException || error instanceof UnknownHostException) {
                            weatherMutableLiveData.postValue(new WeatherResponse(null, "poor internet error occurred"));
                    } else
                        weatherMutableLiveData.postValue(new WeatherResponse(null, "An error occurred"));
                })
        );
    }

    private void getWeatherGeos(double lat, double lon) {
        disposable.add(repo.getWeatherGeo(lat, lon)
                .subscribeOn(provider.io())
                .observeOn(provider.ui())
                .subscribe( response -> {
                    if (response.isSuccessful() && response.body() != null) {
                        disposable.add(repo.insertCurrentToDb(response.body()).subscribeOn(provider.io())
                                .observeOn(provider.io())
                                .subscribe(() -> {
                                            //Get data from db
                                            weatherGeoMutableLiveData.postValue(new WeatherResponse(response.body(), ""));
                                        },
                                        error -> weatherGeoMutableLiveData.postValue(new WeatherResponse(response.body(), "Failed to save to db"))));
                    } else if (response.errorBody() != null) {
                        weatherGeoMutableLiveData.postValue(new WeatherResponse(null, response.message()));
                    }
                }, error ->{
                    if(error instanceof SocketTimeoutException || error instanceof ConnectException || error instanceof UnknownHostException) {
                            weatherGeoMutableLiveData.postValue(new WeatherResponse(null, "poor internet"));
                    } else
                        weatherGeoMutableLiveData.postValue(new WeatherResponse(null, "An error occurred"));
                })
        );
    }

    private void getWeatherFromDb(String cityName) {
        disposable.add(repo.currentFromDb()
                .subscribeOn(provider.io())
                .observeOn(provider.io())
                .subscribe(currentResponse -> {
                    if (currentResponse != null && cityName.equals("")) {
                        weatherMutableLiveData.postValue(new WeatherResponse(currentResponse, ""));
                    } else  {
                        getWeathers(cityName);
                    }
                }, error -> {
                    error.printStackTrace();
                    getWeathers(cityName);
                })
        );
    }

    void getForecastFromDb(String cityName) {
        disposable.add(repo.forecastFromDb()
                .subscribeOn(provider.io())
                .observeOn(provider.io())
                .subscribe(currentResponse -> {
                    if (currentResponse != null && cityName.equals("")) {
                        forecastMutableLiveData.postValue(new ForecastResponse(currentResponse, ""));
                    } else  {
                        getForecasts(cityName);
                    }
                }, error ->{
                    error.printStackTrace();
                    getForecasts(cityName);
                })
        );
    }
}
