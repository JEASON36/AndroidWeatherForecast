package com.example.peipeng.myweather.gson;

import org.litepal.crud.LitePalSupport;

public class CityInfo extends LitePalSupport {
    public String city;
    public String cityId;
    public String parent;
    public String updataTime;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getUpdataTime() {
        return updataTime;
    }

    public void setUpdataTime(String updataTime) {
        this.updataTime = updataTime;
    }
}
