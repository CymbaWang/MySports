package com.example.mysports.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mysports.R;
import com.example.mysports.pojo.Fensi;
import com.example.mysports.pojo.Trend;
import com.example.mysports.util.MyGridView;

import org.w3c.dom.Text;

import java.io.File;
import java.util.List;

public class TrendAdapter extends BaseAdapter {

    private Context context;
    private List<Trend> itemList;

    public TrendAdapter(Context context,List<Trend>itemList){
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
        Trend trend=itemList.get(position);
        TrendAdapter.ViewHolder holder=null;
        if(convertView==null){
            convertView=inflater.inflate(R.layout.dongtai_item,parent,false);
            holder=new TrendAdapter.ViewHolder();
            holder.touxiangImage=convertView.findViewById(R.id.touxiang_image);
            holder.nichengText=convertView.findViewById(R.id.nicheng_text);
            holder.sendtimeText=convertView.findViewById(R.id.sendtime_text);
            holder.trendcontentText=convertView.findViewById(R.id.trendcontent_text);
            holder.myGridView=convertView.findViewById(R.id.trend_gridView);
            holder.zhuanfaText = convertView.findViewById(R.id.zhuanfa_text);
            holder.pinglunText = convertView.findViewById(R.id.pinglun_text);
            holder.zanText = convertView.findViewById(R.id.zan_text);
            convertView.setTag(holder);
        }else{
            holder= (TrendAdapter.ViewHolder) convertView.getTag();
        }
        Uri uri = Uri.fromFile(new File(trend.getTouxiang_image()));
        holder.touxiangImage.setImageURI(uri);
        holder.nichengText.setText(trend.getNicheng_text());
        holder.sendtimeText.setText(trend.getSendtime_text());
        holder.trendcontentText.setText(trend.getTrendContent_text());
        holder.myGridView.setAdapter(new MyGridViewAdapter(context, trend.getTrend_image()));
        holder.zhuanfaText.setText(trend.getZhuanfa_text());
        holder.pinglunText.setText(trend.getPinglun_text());
        holder.zanText.setText(trend.getZan_text());
        return convertView;
    }

    static class ViewHolder{//复用机制
        ImageView touxiangImage;
        TextView nichengText;
        TextView sendtimeText;
        TextView trendcontentText;
        MyGridView myGridView;
        TextView zhuanfaText;
        TextView pinglunText;
        TextView zanText;
    }
}
