package com.example.weather.gson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2017/5/8.
 */
public class Weather
{
    private String status;
    private Basic basic;
    private Aqi aqi;
    private Now now;
    private Suggestion suggestion;
    @SerializedName("daily_forecast")
    private List<ForeCast> foreCasts;

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public Basic getBasic()
    {
        return basic;
    }

    public void setBasic(Basic basic)
    {
        this.basic = basic;
    }

    public Aqi getAqi()
    {
        return aqi;
    }

    public void setAqi(Aqi aqi)
    {
        this.aqi = aqi;
    }

    public Now getNow()
    {
        return now;
    }

    public void setNow(Now now)
    {
        this.now = now;
    }

    public Suggestion getSuggestion()
    {
        return suggestion;
    }

    public void setSuggestion(Suggestion suggestion)
    {
        this.suggestion = suggestion;
    }

    public List<ForeCast> getForeCasts()
    {
        return foreCasts;
    }

    public void setForeCasts(List<ForeCast> foreCasts)
    {
        this.foreCasts = foreCasts;
    }


    @Override
    public String toString()
    {
        return "Weather{" +
                "status='" + status + '\'' +
                ", basic=" + basic +
                ", aqi=" + aqi +
                ", now=" + now +
                ", suggestion=" + suggestion +
                ", foreCasts=" + foreCasts +
                '}';
    }
}
