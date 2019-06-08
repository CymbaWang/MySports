package com.example.mysports.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mysports.R;
import com.example.mysports.pojo.Guanzhu;

import java.util.List;

public class GuanzhuAdapter extends BaseAdapter {

    private Context context;
    private List<Guanzhu> itemList;

    public GuanzhuAdapter(Context context,List<Guanzhu>itemList){
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
        Guanzhu guanzhu=itemList.get(position);
        ViewHolder holder=null;
        if(convertView==null){
            convertView=inflater.inflate(R.layout.guanzhu_item,parent,false);
            holder=new ViewHolder();
            holder.touxiangImage=convertView.findViewById(R.id.touxiang_image);
            holder.xingbieImage=convertView.findViewById(R.id.xingbie_image);
            holder.diandiandianImage=convertView.findViewById(R.id.guanzhu_button);
            holder.nichengText=convertView.findViewById(R.id.nicheng_text);
            holder.jieshaoText=convertView.findViewById(R.id.jieshao_text);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        holder.touxiangImage.setImageResource(guanzhu.getTouxiangImage());
        holder.xingbieImage.setImageResource(guanzhu.getXingbieImage());
        holder.diandiandianImage.setImageResource(guanzhu.getDiandiandianImage());
        holder.nichengText.setText(guanzhu.getNichengText());
        holder.jieshaoText.setText(guanzhu.getJieshaoText());

        return convertView;
    }

    static class ViewHolder{//复用机制
        ImageView touxiangImage;
        ImageView xingbieImage;
        ImageButton diandiandianImage;
        TextView nichengText;
        TextView jieshaoText;
    }

}
