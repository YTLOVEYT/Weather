package com.example.weather.util;

import android.util.Log;

import com.example.weather.gson.Weather;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Administrator on 2017/5/14.
 */
public class ParseUtil
{
    private static final String TAG = "ParseUtil";
    /**解析天气数据*/
    public static Weather handleWeatherResponse(String response)
    {
        try
        {
            JSONObject object = new JSONObject(response);
            JSONArray array = object.getJSONArray("HeWeather");
            String weatherContent= array.getJSONObject(0).toString();
            return new Gson().fromJson(weatherContent, Weather.class);
        }
        catch (Exception e)
        {
        }
        return  null;
    }
}
