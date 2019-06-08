package com.example.mysports.adapter;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mysports.pojo.Fensi;
import com.example.mysports.R;
import com.example.mysports.util.MyImageButton;

import java.util.List;

public class FensiAdapter extends BaseAdapter {

    private Context context;
    private List<Fensi> itemList;

    public FensiAdapter(Context context,List<Fensi>itemList){
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
        Fensi fensi=itemList.get(position);
        ViewHolder holder=null;
        if(convertView==null){
            convertView=inflater.inflate(R.layout.fensi_item,parent,false);
            holder=new ViewHolder();
            holder.touxiangImage=convertView.findViewById(R.id.touxiang_image);
            holder.xingbieImage=convertView.findViewById(R.id.xingbie_image);
            holder.guanzhuImage=convertView.findViewById(R.id.guanzhu_button);
            holder.nichengText=convertView.findViewById(R.id.nicheng_text);
            holder.jieshaoText=convertView.findViewById(R.id.jieshao_text);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        holder.touxiangImage.setImageResource(fensi.getTouxiangImage());
        holder.xingbieImage.setImageResource(fensi.getXingbieImage());
        holder.guanzhuImage.setImageResource(fensi.getGuanzhuImage());
        holder.nichengText.setText(fensi.getNichengText());
        holder.jieshaoText.setText(fensi.getJieshaoText());

        return convertView;
    }

    static class ViewHolder{//复用机制
        ImageView touxiangImage;
        ImageView xingbieImage;
        ImageButton guanzhuImage;
        TextView nichengText;
        TextView jieshaoText;
    }

}
