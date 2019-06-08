package com.example.mysports.adapter;

import android.content.Context;
import android.net.Uri;
import android.provider.ContactsContract;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.mysports.util.MyGridView;
import com.example.mysports.util.MyImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MyGridViewAdapter extends BaseAdapter {
    private Context context;
    private List<String> trend_image = new ArrayList<String>();
    public MyGridViewAdapter(Context context){
        this.context = context;
    }
    public MyGridViewAdapter(Context context, List<String>trend_image){
        this.context = context;
        for(int i=0;i<trend_image.size();i++){
            this.trend_image.add(trend_image.get(i));
        }
    }
    public int getCount(){
        return trend_image.size();
    }
    public Object getItem(int position){
        return position;
    }
    public long getItemId(int position){
        return position;
    }
    public View getView(int position, View convertView, ViewGroup parent){
        MyImageView imageView;
        if(convertView == null){
            imageView = new MyImageView(context);

            imageView.setAdjustViewBounds(true);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            /**************************************************/
            imageView.setPadding(1, 1, 1, 1);
        }else {
            imageView = (MyImageView)convertView;
        }
        Uri uri = Uri.fromFile(new File(trend_image.get(position)));
        imageView.setImageURI(uri);
        return imageView;
    }
}
