package com.gigi.mobile.giditestjava.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gigi.mobile.giditestjava.R;
import com.gigi.mobile.giditestjava.models.Data;
import com.gigi.mobile.giditestjava.models.DataGroup;
import com.gigi.mobile.giditestjava.utils.Constants;
import com.gigi.mobile.giditestjava.utils.GlideApp;
import com.gigi.mobile.giditestjava.utils.Utils;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ViewHolder> {

    private DataGroup data;

    public ForecastAdapter() {
        data = new DataGroup();
    }


    public ForecastAdapter(DataGroup data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.forecast_list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        List<Data> datum = data.getDataGroup().get(position);
        holder.bind(datum);
    }

    @Override
    public int getItemCount() {
        return data.getDataGroup().size();
    }

    public void updateData(DataGroup data) {
        this.data = data;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView day, weather, humidity, temperature;
        private ImageView weatherImage;
        private RecyclerView weatherList;
        ViewHolder(View v) {
            super(v);
            day = v.findViewById(R.id.day);
            weather = v.findViewById(R.id.weather);
            humidity = v.findViewById(R.id.humidity);
            temperature = v.findViewById(R.id.temperature);
            weatherImage = v.findViewById(R.id.weatherImage);
            weatherList = v.findViewById(R.id.weatherList);
        }

        void bind(List<Data> datum) {
            if (datum.size() > 0) {
                GlideApp.with(weatherImage)
                        .load(Constants.IMAGE_URL + datum.get(0).getWeather().get(0).getIcon() + ".png")
                        .fitCenter()
                        .into(weatherImage);
                day.setText(Utils.getDay(datum.get(0).getDtTxt()));
                weather.setText(datum.get(0).getWeather().get(0).getMain());
                humidity.setText(String.valueOf(datum.get(0).getMain().getHumidity() + "%"));
                temperature.setText(String.valueOf(datum.get(0).getMain().getTemp() + "°C"));
                weatherList.setLayoutManager(new LinearLayoutManager(weatherList.getContext(), RecyclerView.HORIZONTAL, false));
                weatherList.setHasFixedSize(true);
                weatherList.setAdapter(new WeatherAdapter(datum));
            }
        }
    }
}

