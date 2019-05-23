package com.gigi.mobile.giditestjava.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gigi.mobile.giditestjava.R;
import com.gigi.mobile.giditestjava.models.Data;
import com.gigi.mobile.giditestjava.utils.Constants;
import com.gigi.mobile.giditestjava.utils.GlideApp;
import com.gigi.mobile.giditestjava.utils.Utils;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.ViewHolder> {
    private List<Data> data;
    public WeatherAdapter(List<Data> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.weather_list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView forecastTime, forecastTemp;
        private ImageView forecastImage;
        ViewHolder(View v) {
            super(v);
            forecastImage = v.findViewById(R.id.forecastImage);
            forecastTime = v.findViewById(R.id.forecastTime);
            forecastTemp = v.findViewById(R.id.forecastTemp);
        }

        void bind(Data data) {
            GlideApp.with(forecastImage)
                    .load(Constants.IMAGE_URL + data.getWeather().get(0).getIcon() + ".png")
                    .fitCenter()
                    .into(forecastImage);
            forecastTemp.setText(String.valueOf(data.getMain().getTemp() + "°C"));
            forecastTime.setText(Utils.getTimeString(data.getDtTxt()));
        }
    }
}
