package com.example.weather;

import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

import com.example.weather.view.WeatherActivity;

public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String weather_id = PreferenceManager.getDefaultSharedPreferences(this).getString("weather_id", null);
        if (weather_id!=null)
        {
            startActivity(new Intent(MainActivity.this,WeatherActivity.class).putExtra("weather_id",weather_id));
        }

    }
}
