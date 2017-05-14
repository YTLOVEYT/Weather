package com.example.weather.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.example.weather.Const;
import com.example.weather.gson.Weather;
import com.example.weather.util.HttpUtil;
import com.example.weather.util.ParseUtil;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class AutoUpdateService extends Service
{
    public AutoUpdateService()
    {
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        throw null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {

        UpdateWeather();
        UpdatePic();

        AlarmManager manager= (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        long time=3*60*60*1000;
        long triggle= SystemClock.elapsedRealtime()+time;
        Intent i=new Intent(this,AutoUpdateService.class);
        PendingIntent pi=PendingIntent.getService(this,0,i,0);
        manager.cancel(pi);
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,triggle,pi);
        return super.onStartCommand(intent, flags, startId);
    }

    private void UpdatePic()
    {
            String url = Const.MAIN + "/bing_pic";
            HttpUtil.sendOKHttpRequest(url, new Callback()
            {
                @Override
                public void onFailure(Call call, IOException e)
                {

                }
                @Override
                public void onResponse(Call call, Response response) throws IOException
                {
                    final String string = response.body().string();

                            PreferenceManager.getDefaultSharedPreferences(AutoUpdateService.this)
                                    .edit().putString("pic", string).apply();
                }
            });

    }

    private void UpdateWeather()
    {
        final String weather_id = PreferenceManager.getDefaultSharedPreferences(this).getString("weather_id", null);
        if (weather_id != null)
        {
            String url = Const.MAIN + Const.WEATHER + "?cityid=" + weather_id + "&key=" + Const.KEY;

            HttpUtil.sendOKHttpRequest(url, new Callback()
            {
                @Override
                public void onFailure(Call call, IOException e)
                {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException
                {
                    String string = response.body().string();
                    Weather weather = ParseUtil.handleWeatherResponse(string);
                    if (weather!=null && weather.getStatus().equals("ok"))
                    {
                        PreferenceManager.getDefaultSharedPreferences(AutoUpdateService.this)
                                .edit().putString(weather_id,string).apply();
                    }
                }
            });
        }
    }
}
