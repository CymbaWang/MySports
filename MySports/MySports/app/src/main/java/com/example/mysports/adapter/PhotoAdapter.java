package com.example.mysports.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mysports.R;
import com.example.mysports.activity.PhotoActivity;
import com.example.mysports.pojo.Photo;

import java.io.File;
import java.util.List;

public class PhotoAdapter extends BaseAdapter {
    private Context context;
    private List<Photo> itemList;

    public PhotoAdapter(Context context,List<Photo>itemList){
        this.context=context;
        this.itemList=itemList;
    }

    @Override
    public int getCount(){return itemList.size();}

    @Override
    public Object getItem(int position){return itemList.get(position);}

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater=LayoutInflater.from(context);
        Photo photo=itemList.get(position);
        PhotoAdapter.ViewHolder holder=null;
        if(convertView==null){
            convertView=inflater.inflate(R.layout.photo_item,parent,false);
            holder=new PhotoAdapter.ViewHolder();
            holder.photo_image=convertView.findViewById(R.id.photo_image);
            holder.photo_text=convertView.findViewById(R.id.photo_text);
            convertView.setTag(holder);
        }else{
            holder= (PhotoAdapter.ViewHolder) convertView.getTag();
        }
        Uri uri = Uri.fromFile(new File(photo.getPhoto_image()));
        holder.photo_image.setImageURI(uri);
        /**************设置图片的宽度和高度******************/
        holder.photo_image.setAdjustViewBounds(true);
        /**************************************************/
        holder.photo_image.setPadding(5,5,5,5);
        holder.photo_text.setText(photo.getPhoto_text());
        return convertView;
    }

    static class ViewHolder{//复用机制
        ImageView photo_image;
        TextView photo_text;
    }
}
