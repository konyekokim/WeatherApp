package com.gigi.mobile.giditestjava.ui.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import dagger.android.support.DaggerFragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.gigi.mobile.giditestjava.R;
import com.gigi.mobile.giditestjava.models.CurrentResponse;
import com.gigi.mobile.giditestjava.models.Data;
import com.gigi.mobile.giditestjava.models.DataGroup;
import com.gigi.mobile.giditestjava.models.Response;
import com.gigi.mobile.giditestjava.ui.adapters.ForecastAdapter;
import com.gigi.mobile.giditestjava.utils.Constants;
import com.gigi.mobile.giditestjava.utils.GlideApp;
import com.gigi.mobile.giditestjava.utils.Utils;
import com.gigi.mobile.giditestjava.viewmodels.MainActivityViewModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

public class WeatherFragment extends DaggerFragment {

    private static final int LOCATION_REQUEST = 419;

    @Inject
    ViewModelProvider.Factory factory;

    private MainActivityViewModel model;
    private FusedLocationProviderClient fusedLocationClient;
    private EditText search;
    private ImageView weatherImage;
    private ProgressBar currentProgress, forecastProgress;
    private TextView todayDate, location, todayTemperature, todayHumidity, indoorTemperature, windSpeed,
            dewPoint, weatherDesc, minMaxTemperature;
    private ConstraintLayout currentLayout;
    private RecyclerView forecastList;
    private ForecastAdapter mAdapter;

    public WeatherFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActivity() != null)
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_weather, container, false);
        model = ViewModelProviders.of(this, factory).get(MainActivityViewModel.class);
        initViews(v);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialCalls();
    }

    @Override
    public void onResume() {
        super.onResume();
        initialCalls();
    }

    @SuppressLint("CheckResult")
    private void initialCalls() {
        if (checkPermission()) {
            Utils.hasInternet().subscribe(connected -> {
                if (connected) {
                    if (getActivity() != null) {
                        fromLocation();
                    }
                } else {
                    fetchData("");
                }
            }, error -> {
                Utils.showToast(getContext(), "No network and no data saved");
            });
        } else {
            requestPermission();
        }
    }

    private void initViews(View v) {
        search = v.findViewById(R.id.searchEntry);
        search.setOnEditorActionListener((v1, actionId, event) -> {
            if (!TextUtils.isEmpty(search.getText().toString())) {
                fetchData(search.getText().toString());
                hideKeyboard();
                return true;
            }
            return false;
        });
        currentProgress = v.findViewById(R.id.currentProgress);
        forecastProgress = v.findViewById(R.id.forecastProgress);
        currentLayout = v.findViewById(R.id.currentLayout);
        todayDate = v.findViewById(R.id.todaysDate);
        location = v.findViewById(R.id.location);
        todayTemperature = v.findViewById(R.id.todaysTemp);
        todayHumidity = v.findViewById(R.id.todaysHumidity);
        indoorTemperature = v.findViewById(R.id.indoorTemp);
        windSpeed = v.findViewById(R.id.windSpeed);
        dewPoint = v.findViewById(R.id.dewPoint);
        weatherDesc = v.findViewById(R.id.weatherDesc);
        minMaxTemperature = v.findViewById(R.id.minMaxTemp);
        weatherImage = v.findViewById(R.id.todayWeatherImage);
        forecastList = v.findViewById(R.id.forecastList);
        mAdapter = new ForecastAdapter();
        forecastList.setLayoutManager(new LinearLayoutManager(
                getContext(),
                RecyclerView.VERTICAL,
                false));
        forecastList.setHasFixedSize(true);
        forecastList.addItemDecoration(
                new DividerItemDecoration(
                        getContext(),
                        RecyclerView.VERTICAL));
        forecastList.setAdapter(mAdapter);
    }

    private void showCurrent(CurrentResponse response, String error) {
        if (response != null) {
            Log.e("Current", response.toString());
            todayDate.setText(Utils.getDateString((response.getDt())));
            this.location.setText(String.valueOf(response.getName() + ", " + String.valueOf(response.getSys().getCountry())));
            todayTemperature.setText(String.valueOf(response.getMain().getTemp() + "°"));
            todayHumidity.setText(String.valueOf(response.getMain().getHumidity() + "%"));
            indoorTemperature.setText(String.valueOf(response.getMain().getTempMax() + "°C"));
            windSpeed.setText(String.valueOf(response.getWind().getSpeed() + "m/s"));
            dewPoint.setText(String.valueOf(response.getMain().getPressure() / 1000 + "bar"));
            weatherDesc.setText(String.valueOf(response.getWeather().get(0).getMain()));
            minMaxTemperature.setText(String.valueOf(response.getMain().getTempMin() + "°C/" +
                    response.getMain().getTempMax() + "°C"));
            GlideApp.with(weatherImage)
                    .load(Constants.IMAGE_URL + response.getWeather().get(0).getIcon() + ".png")
                    .fitCenter()
                    .into(weatherImage);
            currentProgress.setVisibility(View.GONE);
            currentLayout.setVisibility(View.VISIBLE);
        }
        if (!error.isEmpty()) {
            currentProgress.setVisibility(View.GONE);
            currentLayout.setVisibility(View.GONE);
            Utils.showToast(getContext(), error);
        }
    }

    private void showForecast(Response response, String error) {
        if (response != null) {
            Log.e("Forecast", response.toString());
            List<Data> data0 = new ArrayList<>();
            List<Data> data1 = new ArrayList<>();
            List<Data> data2 = new ArrayList<>();
            List<Data> data3 = new ArrayList<>();
            List<Data> data4 = new ArrayList<>();
            List<Data> data5 = new ArrayList<>();
            Calendar calendar0 = Calendar.getInstance();
            calendar0.set(Calendar.HOUR_OF_DAY, 0);
            calendar0.set(Calendar.MINUTE, 0);
            calendar0.set(Calendar.SECOND, 0);
            calendar0.set(Calendar.MILLISECOND, 0);
            Calendar calendar1 = (Calendar) calendar0.clone();
            calendar1.add(Calendar.DAY_OF_YEAR, 1);
            Calendar calendar2 = (Calendar) calendar0.clone();
            calendar2.add(Calendar.DAY_OF_YEAR, 2);
            Calendar calendar3 = (Calendar) calendar0.clone();
            calendar3.add(Calendar.DAY_OF_YEAR, 3);
            Calendar calendar4 = (Calendar) calendar0.clone();
            calendar4.add(Calendar.DAY_OF_YEAR, 4);
            Calendar calendar5 = (Calendar) calendar0.clone();
            calendar5.add(Calendar.DAY_OF_YEAR, 5);
            for (Data data : response.getList()) {
                if (getCalendarFromDate(data.getDt()).before(calendar1)) {
                    data0.add(data);
                } else if (getCalendarFromDate(data.getDt()).before(calendar2)) {
                    data1.add(data);
                } else if (getCalendarFromDate(data.getDt()).before(calendar3)) {
                    data2.add(data);
                } else if (getCalendarFromDate(data.getDt()).before(calendar4)) {
                    data3.add(data);
                } else if (getCalendarFromDate(data.getDt()).before(calendar5)) {
                    data4.add(data);
                } else {
                    data5.add(data);
                }
            }
            DataGroup dataGroup = new DataGroup(data0);
            if (data1.size() > 0)
                dataGroup.addData(data1);
            if (data2.size() > 0)
                dataGroup.addData(data2);
            if (data3.size() > 0)
                dataGroup.addData(data3);
            if (data4.size() > 0)
                dataGroup.addData(data4);
            if (data5.size() > 0)
                dataGroup.addData(data5);
            mAdapter.updateData(dataGroup);
            forecastProgress.setVisibility(View.GONE);
            forecastList.setVisibility(View.VISIBLE);
        }
        if (!error.isEmpty()) {
            forecastProgress.setVisibility(View.GONE);
            forecastList.setVisibility(View.GONE);
            Utils.showToast(getContext(), error);
        }
    }

    private void hideKeyboard() {
        if (getActivity() != null) {
            final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            try {
                imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private Calendar getCalendarFromDate(long date) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(date * 1000L);
        return cal;
    }

    private void fetchData(String cityName) {
        currentProgress.setVisibility(View.VISIBLE);
        forecastProgress.setVisibility(View.VISIBLE);
        forecastList.setVisibility(View.GONE);
        currentLayout.setVisibility(View.GONE);
        model.getCurrentData(cityName).observe(this, response ->
                showCurrent(response.getCurrentResponse(), response.getErrorMessage())
        );
        model.getForecast(cityName).observe(this, response->
                showForecast(response.getResponse(), response.getErrorMessage())
        );
    }

    private void fetchDataFromLoc(Location location) {
        forecastProgress.setVisibility(View.VISIBLE);
        forecastList.setVisibility(View.GONE);
        model.getForecastGeo(location.getLatitude(), location.getLongitude())
                .observe(this, response ->
                        showForecast(response.getResponse(), response.getErrorMessage())
                );
        currentProgress.setVisibility(View.VISIBLE);
        currentLayout.setVisibility(View.GONE);
        model.getCurrentDataGeo(location.getLatitude(), location.getLongitude())
                .observe(this, response ->
                        showCurrent(response.getCurrentResponse(), response.getErrorMessage())
                );
    }

    private void fromLocation() {
        if(checkPermission()) {fusedLocationClient.getLastLocation()
                .addOnSuccessListener(getActivity(), location -> {
                    // Got last known location.
                    if (location != null) {
                        fetchDataFromLoc(location);
                    } else {
                        fetchData("Ikeja");
                    }
                });
        } else {
            Utils.showToast(getContext(),"No Internet connection, please Check and retry");
        }
    }


    private boolean checkPermission() {
        return (ContextCompat.checkSelfPermission(
                getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(
                        getContext(),
                        Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED);
    }

    private void requestPermission() {
        if (!checkPermission()) {
            if (getActivity() != null) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)) {

                } else {
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                                    Manifest.permission.ACCESS_COARSE_LOCATION},
                            LOCATION_REQUEST);

                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(grantResults.length > 0 ){
            if(requestCode == LOCATION_REQUEST && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (getActivity() != null) {
                    fromLocation();
                }
            } else {
                Utils.showToast(getContext(), "Location not active, Default data is been " +
                        "displayed, use search to get weather");

                fetchData("Ajah, NG");
            }
        } else
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
