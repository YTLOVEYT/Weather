package com.example.weather.gson;

/**
 * Created by Administrator on 2017/5/8.
 */
public class Aqi
{
    private AqiCity city;
    public class AqiCity
    {
        private String aqi;
        private String pm25;

        public String getAqi()
        {
            return aqi;
        }

        public void setAqi(String aqi)
        {
            this.aqi = aqi;
        }

        public String getPm25()
        {
            return pm25;
        }

        public void setPm25(String pm25)
        {
            this.pm25 = pm25;
        }
    }

    public AqiCity getCity()
    {
        return city;
    }

    public void setCity(AqiCity city)
    {
        this.city = city;
    }
}
