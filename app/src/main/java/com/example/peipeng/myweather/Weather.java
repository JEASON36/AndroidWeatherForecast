package com.example.peipeng.myweather;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.peipeng.myweather.gson.Content;
import com.example.peipeng.myweather.gson.Day;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;
import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.example.peipeng.myweather.gson.Utility.handleWeatherResponse;

public class Weather extends AppCompatActivity {

    public DrawerLayout drawerLayout;

    public SwipeRefreshLayout swipeRefresh;

    private ScrollView weatherLayout;

    private Button navButton;

    private TextView titleCity;

    private TextView titleUpdateTime;

    private TextView degreeText;

    private TextView weatherInfoText;

    private LinearLayout forecastLayout;

    private TextView aqiText;

    private TextView pm25Text;

    private TextView comfortText;

    private String city_code;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        weatherLayout = (ScrollView) findViewById(R.id.weather_layout);
        titleCity = (TextView) findViewById(R.id.title_city);
        titleUpdateTime = (TextView) findViewById(R.id.title_update_time);
        degreeText = (TextView) findViewById(R.id.degree_text);
        weatherInfoText = (TextView) findViewById(R.id.weather_info_text);
        forecastLayout = (LinearLayout) findViewById(R.id.forecast_layout);
        aqiText = (TextView) findViewById(R.id.aqi_text);
        pm25Text = (TextView) findViewById(R.id.pm25_text);
        comfortText = (TextView) findViewById(R.id.comfort_text);
        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navButton = (Button) findViewById(R.id.nav_button);//设置为刷新按钮

        Intent intent = getIntent();
        city_code = intent.getStringExtra("city_code");



        SharedPreferences prefs = getSharedPreferences("data",MODE_PRIVATE);
        String weatherString = prefs.getString("weather", null);
//        Toast.makeText(Weather.this,"直接使用缓存"+weatherString,Toast.LENGTH_SHORT).show();
        Content content1 = handleWeatherResponse(weatherString);
//        Toast.makeText(Weather.this,"缓存的城市名称"+content1.cityInfo.city,Toast.LENGTH_SHORT).show();


//这里出现问题，解析的结果没错，试试输出
//         && content1.cityInfo.cityId == city_code
        if (weatherString != null && content1.cityInfo.cityId.equals(city_code)) {
            showWeatherInfo(content1);
            Toast.makeText(Weather.this,"直接使用缓存",Toast.LENGTH_SHORT).show();
        } else {
            sendRequestWithOkHttp();
        }

    }

    private void sendRequestWithOkHttp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder().url("http://t.weather.sojson.com/api/weather/city/"+city_code).build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    parseJSONWithGSON(responseData);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void parseJSONWithGSON(final String Data1) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Gson gson = new Gson();
                Content content = gson.fromJson(Data1,Content.class);
//                增加存储按钮，将数据存储到share中

                if (content != null && "Success !".equals(content.message)) {
//                    SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(Weather.this).edit();
                    SharedPreferences.Editor editor = getSharedPreferences("data",MODE_PRIVATE).edit();
                    editor.putString("weather", Data1);
                    editor.apply();
//                    Toast.makeText(Weather.this, "存入缓存", Toast.LENGTH_SHORT).show();
                    showWeatherInfo(content);
                } else {
                    Toast.makeText(Weather.this, "获取天气信息失败", Toast.LENGTH_SHORT).show();
                }


//                showWeatherInfo(content);
            }
        });
    }

    private void showWeatherInfo(Content content) {
        navButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Weather.this,Weather.class);
                intent.putExtra("city_code",""+city_code);
                startActivity(intent);
            }
        });
        String cityName =content.cityInfo.city;
        String updateTime = content.cityInfo.updataTime;
        String degree = content.data.wendu + "℃";
        String weatherInfo = content.data.ganmao;
        titleCity.setText(cityName);
        titleUpdateTime.setText(updateTime);
        degreeText.setText(degree);
        weatherInfoText.setText(weatherInfo);
        forecastLayout.removeAllViews();
        for (Day forecast : content.data.forecast) {
            View view = LayoutInflater.from(this).inflate(R.layout.forecast_item, forecastLayout, false);
            TextView dateText = (TextView) view.findViewById(R.id.date_text);
            TextView infoText = (TextView) view.findViewById(R.id.info_text);
            TextView maxText = (TextView) view.findViewById(R.id.max_text);
            TextView minText = (TextView) view.findViewById(R.id.min_text);
            dateText.setText(forecast.date);
            infoText.setText(forecast.getNotice());
            maxText.setText(forecast.high);
            minText.setText(forecast.low);
            forecastLayout.addView(view);
        }
        if (content.data.yesterday.aqi != 0) {
            aqiText.setText(""+content.data.yesterday.aqi);
            pm25Text.setText(""+content.data.pm25);
        }
        String comfort = "舒适度：" + content.data.ganmao;
        comfortText.setText(comfort);
    }


}
