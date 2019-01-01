package com.example.peipeng.myweather;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.peipeng.myweather.gson.Content;

import java.util.List;

public class Content_adapter extends ArrayAdapter<Content> {
    private int resorceId;
    public Content_adapter(Context context, int textVIewResorceId, List<Content>objects){
        super(context,textVIewResorceId,objects);
        resorceId = textVIewResorceId;
    }
    @Override
    public View getView(int position, View converView, ViewGroup parent){
        Content content = getItem(position);

        View view = LayoutInflater.from(getContext()).inflate(resorceId,parent,false);
        TextView content_Time = view.findViewById(R.id.content_time);
        content_Time.setText(content.getTime());
        TextView content_data = view.findViewById(R.id.content_date);
        content_data.setText(content.getDate());
        TextView content_message = view.findViewById(R.id.content_message);
        content_message.setText(content.getMessage());
        return view;
    }
}
