package com.example.weather.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.weather.Const;
import com.example.weather.MainActivity;
import com.example.weather.R;
import com.example.weather.gson.ForeCast;
import com.example.weather.gson.Weather;
import com.example.weather.service.AutoUpdateService;
import com.example.weather.util.HttpUtil;
import com.example.weather.util.ParseUtil;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class WeatherActivity extends AppCompatActivity
{
    private static final String TAG = "WeatherActivity";

    @Bind(R.id.title_city)
    TextView titleCity;
    @Bind(R.id.title_update_time)
    TextView titleUpdateTime;
    @Bind(R.id.degree_text)
    TextView degreeText;
    @Bind(R.id.weather_info_text)
    TextView weatherInfoText;
    @Bind(R.id.forecast)
    TextView forecast;
    @Bind(R.id.forecast_layout)
    LinearLayout forecastLayout;
    @Bind(R.id.aqi_text)
    TextView aqiText;
    @Bind(R.id.pm25_text)
    TextView pm25Text;
    @Bind(R.id.comfort_text)
    TextView comfortText;
    @Bind(R.id.car_wash_text)
    TextView carWashText;
    @Bind(R.id.sport_text)
    TextView sportText;
    @Bind(R.id.weather_layout)
    ScrollView weatherLayout;
    @Bind(R.id.back_tv)
    TextView backTv;
    @Bind(R.id.refresh)
    public SwipeRefreshLayout refresh;
    @Bind(R.id.draw)
    public DrawerLayout draw;
    @Bind(R.id.ll)
    LinearLayout ll;
    @Bind(R.id.bg)
    ImageView bg;

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21)
        {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_weather);
        ButterKnife.bind(this);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String pic = preferences.getString("pic", null);
        if (pic != null)
        {
            Glide.with(this).load(pic).into(bg);
        } else
        {
            getByPic();
        }
        final String id = getIntent().getStringExtra("weather_id");
        preferences.edit().putString("weather_id", id).apply();

        String weather = preferences.getString(id, null);
        if (weather != null)
        {
            //解析天气数据
            Weather w = ParseUtil.handleWeatherResponse(weather);
            ShowWeather(w);
        } else
        {
            //去服务器请求天气
            weatherLayout.setVisibility(View.INVISIBLE);
            RequestWeather(id);
        }

        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                RequestWeather(id);
            }
        });
    }

//    private SimpleTarget<Bitmap> target = new SimpleTarget<Bitmap>()
//    {
//        @Override
//        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation)
//        {
//            ll.setBackground(new BitmapDrawable(resource));
//        }
//    };

    private void getByPic()
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
                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {

                        preferences.edit().putString("pic", string).apply();

                        Glide.with(WeatherActivity.this).load(string).into(bg);
                    }
                });

            }
        });
    }

    /**
     * 去服务器请求天气信息
     */
    public void RequestWeather(final String weatherId)
    {
        String url = Const.MAIN + Const.WEATHER + "?cityid=" + weatherId + "&key=" + Const.KEY;
        Log.e(TAG, "RequestWeather: " + url);
        HttpUtil.sendOKHttpRequest(url, new Callback()
        {
            @Override
            public void onFailure(Call call, IOException e)
            {
                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        Toast.makeText(WeatherActivity.this, "获取天气信息失败2", Toast.LENGTH_SHORT).show();
                        refresh.setRefreshing(false);
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException
            {
                final String string = response.body().string();
                final Weather weather = ParseUtil.handleWeatherResponse(string);
                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        if (weather != null && "ok".equals(weather.getStatus()))
                        {
                            preferences.edit().putString(weatherId, string).apply();
                            ShowWeather(weather);
                        } else
                        {
                            Toast.makeText(WeatherActivity.this, "获取天气信息失败1", Toast.LENGTH_SHORT).show();
                        }
                        refresh.setRefreshing(false);
                    }
                });
            }
        });
    }

    /**
     * 显示天气信息
     */
    private void ShowWeather(Weather w)
    {
        if (w!=null && "ok".equals(w.getStatus()))
        {

            String cityName = w.getBasic().getCityName();
            String updateTime = w.getBasic().getUpdate().getUpdateTime().split(" ")[1];
            String degree = w.getNow().getTemperature() + "℃";
            String info = w.getNow().getMore().getInfo();
            titleCity.setText(cityName);
            titleUpdateTime.setText(updateTime);
            degreeText.setText(degree);
            weatherInfoText.setText(info);
            forecastLayout.removeAllViews();
            for (ForeCast forcast : w.getForeCasts())
            {
                View view = LayoutInflater.from(this).inflate(R.layout.forecast_item, forecastLayout, false);
                TextView dateText = (TextView) view.findViewById(R.id.date_text);
                TextView infoText = (TextView) view.findViewById(R.id.info_text);
                TextView maxText = (TextView) view.findViewById(R.id.max_text);
                TextView minText = (TextView) view.findViewById(R.id.min_text);
                dateText.setText(forcast.getDate() + "");
                infoText.setText(forcast.getMore().getInfo() + "");
                maxText.setText(forcast.getTemperature().getMax());
                minText.setText(forcast.getTemperature().getMin());
                forecastLayout.addView(view);
            }
            if (w.getAqi() != null)
            {
                aqiText.setText(w.getAqi().getCity().getAqi());
                pm25Text.setText(w.getAqi().getCity().getPm25());
            }
            comfortText.setText("舒适度：" + w.getSuggestion().getComfort().getInfo());
            carWashText.setText("洗车指数：" + w.getSuggestion().getCarWash().getInfo());
            sportText.setText("运动建议：" + w.getSuggestion().getSport().getInfo());
            weatherLayout.setVisibility(View.VISIBLE);

            startService(new Intent(WeatherActivity.this, AutoUpdateService.class));
        }
    }

    @OnClick(R.id.back_tv)
    public void onViewClicked()
    {
        startActivity(new Intent(WeatherActivity.this, MainActivity.class));
    }
}
