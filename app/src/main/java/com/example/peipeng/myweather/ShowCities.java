package com.example.peipeng.myweather;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class ShowCities extends AppCompatActivity {

    int province_id;
    private List<City> cityList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_cities);

        final Intent intent = getIntent();
        province_id = intent.getIntExtra("province_id1",0);//默认值是零 说明传递过来的名字没有找到吗
//        Toast.makeText(ShowCities.this,"province id is "+province_id,Toast.LENGTH_SHORT).show();
        initCities();
        City_adapter adapter = new City_adapter(ShowCities.this,R.layout.city,cityList);
        ListView listView = findViewById(R.id.list_view);
        listView.setAdapter(adapter);
        AdapterView.OnItemClickListener itemClick = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent1 = new Intent(ShowCities.this,Weather.class);

                City city = cityList.get(position);
//                Toast.makeText(ShowCities.this,"the city name is "+city.getCity_name(),Toast.LENGTH_LONG).show();
                intent1.putExtra("city_code",city.getCity_code());//直接传递ID 到weather中在通过ID进行查找
                startActivity(intent1);
            }
        };
        listView.setOnItemClickListener(itemClick);
    }

    private void initCities() {
        List<City> cities = LitePal.findAll(City.class);
        //返回全部数据，在循环语句中做一个判断，对于pid== 0 才能显示出来
        for (City city:cities){
            if(city.getPid()==province_id) {
                cityList.add(city);
            }
        }
    }
}
