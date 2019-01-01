package com.example.peipeng.myweather;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class City_adapter extends ArrayAdapter<City> {
    private int resorceId;//这个不确定有没有用
    public City_adapter(Context context, int textViewResorceId, List<City>objects){
        super(context,textViewResorceId,objects);
        resorceId = textViewResorceId;
    }

    @Override
    public View getView(int position, View converView, ViewGroup parent){
        City city = getItem(position);//获取当前位置的实例
        View view = LayoutInflater.from(getContext()).inflate(resorceId,parent,false);
        TextView citesText = view.findViewById(R.id.city_name);
        citesText.setText(city.getCity_name());
        return view;
    }
}
