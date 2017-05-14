package com.example.weather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/5/8.
 */
public class ForeCast
{
    @SerializedName("date")
    private String date;

    @SerializedName("tmp")
    private Temperature temperature;
    @SerializedName("cond")
    private  More more;

    public class Temperature
    {
        private String max;
        private String min;

        public String getMax()
        {
            return max;
        }

        public void setMax(String max)
        {
            this.max = max;
        }

        public String getMin()
        {
            return min;
        }

        public void setMin(String min)
        {
            this.min = min;
        }
    }
    public class More
    {
        @SerializedName("txt_d")
        private String info;

        public String getInfo()
        {
            return info;
        }

        public void setInfo(String info)
        {
            this.info = info;
        }
    }

    public String getDate()
    {
        return date;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    public Temperature getTemperature()
    {
        return temperature;
    }

    public void setTemperature(Temperature temperature)
    {
        this.temperature = temperature;
    }

    public More getMore()
    {
        return more;
    }

    public void setMore(More more)
    {
        this.more = more;
    }
}
