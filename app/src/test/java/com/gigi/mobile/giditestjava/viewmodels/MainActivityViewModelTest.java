package com.gigi.mobile.giditestjava.viewmodels;

import com.gigi.mobile.giditestjava.models.City;
import com.gigi.mobile.giditestjava.models.Clouds;
import com.gigi.mobile.giditestjava.models.CurrentResponse;
import com.gigi.mobile.giditestjava.models.Main;
import com.gigi.mobile.giditestjava.models.Weather;
import com.gigi.mobile.giditestjava.repo.Repository;
import com.gigi.mobile.giditestjava.utils.scheduler.SchedulerProvider;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

import com.gigi.mobile.giditestjava.models.Response;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MainActivityViewModelTest {

    @Mock
    private Repository repo;

    @Mock
    private SchedulerProvider provider;

    private MainActivityViewModel mViewModel;

    private CurrentResponse responseCurrent;

    private Response responseForecast;

    @Before
    public void setUp() throws Exception {
        mViewModel = new MainActivityViewModel(repo, provider);
        when(provider.io()).thenReturn(Schedulers.trampoline());
        when(provider.ui()).thenReturn(Schedulers.trampoline());

        responseCurrent = new CurrentResponse();
        responseCurrent.setClouds(new Clouds(20));
        responseCurrent.setMain(new Main(27.0, 1013.0, 88.0, 27.0, 27.0));
        responseCurrent.setWeather(Collections.singletonList(new Weather(801, "Clouds", "few clouds", "02d")));
        responseCurrent.setName("Ebute Ikorodu");
        responseCurrent.setId(2344082);
        responseCurrent.setDt(1556960400);

        responseForecast = new Response();
        responseForecast.setCountry("NG");
        responseForecast.setCity(new City(100, "Ikeja", "NG"));
    }

    @Test
    public void getForecast_returnsData() {
        Single<retrofit2.Response<Response>> resp = Single.just(retrofit2.Response.success(responseForecast));
        Single<Response> responseFromDb = Single.just(new Response());
        when(repo.getForecast(anyString())).thenReturn(resp);
        when(repo.forecastFromDb()).thenReturn(responseFromDb);
        when(repo.insertForecastToDb(responseForecast)).thenReturn(Completable.complete());

        mViewModel.getForecast("Lagos");

        verify(repo).forecastFromDb();

        assertEquals(resp, repo.getForecast("Lagos"));
        assertEquals(responseFromDb, repo.forecastFromDb());
    }

    @Test
    public void getCurrentData_returnsCurrentData() {
        
        Single<retrofit2.Response<CurrentResponse>> resp  = Single.just(retrofit2.Response.success(responseCurrent));
        Single<CurrentResponse> responseFromDb = Single.just(new CurrentResponse());
        when(repo.getWeather(anyString())).thenReturn(resp);
        when(repo.currentFromDb()).thenReturn(responseFromDb);
        when(repo.insertCurrentToDb(responseCurrent)).thenReturn(Completable.complete());

        mViewModel.getCurrentData("Lagos");

        verify(repo).currentFromDb();

        assertEquals(resp, repo.getWeather(""));
        assertEquals(responseFromDb, repo.currentFromDb());
    }

    @Test
    public void getForecastGeo_returnForecastData() {
        Single<retrofit2.Response<Response>> resp  = Single.just(retrofit2.Response.success(responseForecast));
        when(repo.getForecastGeo(anyDouble(), anyDouble())).thenReturn(resp);

        mViewModel.getForecastGeo(0.00, 0.00);
        verify(repo).getForecastGeo(0.00, 0.00);

        assertEquals(resp, repo.getForecastGeo(0.00, 0.00));
    }

    @Test
    public void getCurrentDataGeo_returnsCurrentDataGeo() {
        Single<retrofit2.Response<CurrentResponse>> resp  = Single.just(retrofit2.Response.success(responseCurrent));
        when(repo.getWeatherGeo(anyDouble(), anyDouble())).thenReturn(resp);
        when(repo.insertCurrentToDb(responseCurrent)).thenReturn(Completable.complete());

        mViewModel.getCurrentDataGeo(0.00, 0.00);
        verify(repo).getWeatherGeo(0.00, 0.00);

        assertEquals(resp, repo.getWeatherGeo(0.00, 0.00));
    }

    @Test
    public void getForecastFromDb_returnsForecastData() {
        Single<Response> response = Single.just(new Response());
        when(repo.forecastFromDb()).thenReturn(response);

        assertEquals(response.blockingGet(), mViewModel.getForecastInDb());
    }
}