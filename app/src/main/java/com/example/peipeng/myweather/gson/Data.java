package com.example.peipeng.myweather.gson;

import org.litepal.crud.LitePalSupport;

import java.util.List;

public class Data extends LitePalSupport {
    public String shidu;
    public double pm25;
    public double pm10;
    public String quelity;
    public String wendu;
    public String ganmao;
    public Day yesterday;
    public List<Day> forecast;

    public String getShidu() {
        return shidu;
    }

    public void setShidu(String shidu) {
        this.shidu = shidu;
    }

    public double getPm25() {
        return pm25;
    }

    public void setPm25(double pm25) {
        this.pm25 = pm25;
    }

    public double getPm10() {
        return pm10;
    }

    public void setPm10(double pm10) {
        this.pm10 = pm10;
    }

    public String getQuelity() {
        return quelity;
    }

    public void setQuelity(String quelity) {
        this.quelity = quelity;
    }

    public String getWendu() {
        return wendu;
    }

    public void setWendu(String wendu) {
        this.wendu = wendu;
    }

    public String getGanmao() {
        return ganmao;
    }

    public void setGanmao(String ganmao) {
        this.ganmao = ganmao;
    }

    public Day getYesterday() {
        return yesterday;
    }

    public void setYesterday(Day yesterday) {
        this.yesterday = yesterday;
    }

    public List<Day> getForecast() {
        return forecast;
    }

    public void setForecast(List<Day> forecast) {
        this.forecast = forecast;
    }


}
