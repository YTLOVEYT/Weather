package com.example.weather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/5/8.
 */
public class Suggestion
{
    @SerializedName("comf")
    private Comfort comfort;
    @SerializedName("cw")
    private CarWash carWash;
    @SerializedName("sport")
    private Sport sport;

    public class Comfort
    {
        @SerializedName("txt")
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

    public class CarWash
    {
        @SerializedName("txt")
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

    public class Sport
    {
        @SerializedName("txt")
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

    public Comfort getComfort()
    {
        return comfort;
    }

    public void setComfort(Comfort comfort)
    {
        this.comfort = comfort;
    }

    public CarWash getCarWash()
    {
        return carWash;
    }

    public void setCarWash(CarWash carWash)
    {
        this.carWash = carWash;
    }

    public Sport getSport()
    {
        return sport;
    }

    public void setSport(Sport sport)
    {
        this.sport = sport;
    }
}
