package com.gigi.mobile.giditestjava.ui;

import dagger.android.support.DaggerAppCompatActivity;

import android.os.Bundle;

import com.gigi.mobile.giditestjava.R;
import com.gigi.mobile.giditestjava.ui.fragment.WeatherFragment;

public class MainActivity extends DaggerAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadFragment();
    }

    private void loadFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, new WeatherFragment())
                .commit();
    }
}
