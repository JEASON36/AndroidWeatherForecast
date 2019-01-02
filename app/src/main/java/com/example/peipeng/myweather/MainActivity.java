package com.example.peipeng.myweather;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private List<City> cityList = new ArrayList<>();
    EditText input;
//    TextView responseText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LitePal.initialize(this);
        setContentView(R.layout.activity_main);
        initCities();
        input = findViewById(R.id.input_weather_id);
        input.setOnClickListener(this);
        Button send_id = findViewById(R.id.send_input);
        send_id.setOnClickListener(this);


        City_adapter adapter = new City_adapter(MainActivity.this,R.layout.city,cityList);
        ListView listView = findViewById(R.id.list_view);
        listView.setAdapter(adapter);
        AdapterView.OnItemClickListener itemClick = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this,ShowCities.class);
                City city = cityList.get(position);
                intent.putExtra("province_id1",city.getId()-1);
                startActivity(intent);
            }
        };

        listView.setOnItemClickListener(itemClick);
    }

    private void initCities() {
        List<City> cities = LitePal.findAll(City.class);
        //返回全部数据，在循环语句中做一个判断，对于pid== 0 才能显示出来
        for (City city:cities){
            if(city.getPid()==0) {
                cityList.add(city);
            }
        }
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.send_input:
                String id1 = input.getText().toString();
//                Toast.makeText(MainActivity.this,"the String id length is "+id1.length(),Toast.LENGTH_SHORT).show();
                int temp =0;
                List<City> cities = LitePal.findAll(City.class);
                for (City city1:cities){
                    if(city1.getCity_code().equals(id1)){
//                        Toast.makeText(MainActivity.this,"success",Toast.LENGTH_SHORT).show();
                        Intent intent1 = new Intent(MainActivity.this,Weather.class);
                        intent1.putExtra("city_code",id1);
                        startActivity(intent1);
                        temp = 1;
                        break;
                    }
                }
                if (temp ==0) {
                    Toast.makeText(MainActivity.this, "the Weather id is wrong,Please input again", Toast.LENGTH_SHORT).show();
                }
        }
    }

    private void sendRequestWithOkHttp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    OkHttpClient client = new OkHttpClient();

                    Request request = new Request.Builder().url("http://cdn.sojson.com/_city.json?attname=").build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();

//                   showResponse(responseData);

                    //解析数据
                    parseJSONWithGSON(responseData);//这个没有显示出来，出现问题
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void parseJSONWithGSON(final String Data) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //在这里进行UI操作
                Toast.makeText(MainActivity.this,"id is ",Toast.LENGTH_SHORT).show();
                Gson gson = new Gson();
                List<City> cities;
                cities = gson.fromJson(Data,new TypeToken<List<City>>(){}.getType());
                for (City city:cities){
//                    Log.d("MainActivity","id is "+city.getId());
                    city.save();//ok ,成功传入进去
                    Toast.makeText(MainActivity.this,"id is "+city.getId(),Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
}
