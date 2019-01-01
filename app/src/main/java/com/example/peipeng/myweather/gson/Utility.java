package com.example.peipeng.myweather.gson;

import com.google.gson.Gson;

public class Utility {


    public static Content handleWeatherResponse(String response) {

        try {
            Gson gson = new Gson();
            Content content = gson.fromJson(response, Content.class);
            return content;


//            JSONObject jsonObject = new JSONObject(response);
//            JSONArray jsonArray = jsonObject.getJSONArray("HeWeather");
//            String weatherContent = jsonArray.getJSONObject(0).toString();
//            return new Gson().fromJson(weatherContent, Weather.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;


    }
}
