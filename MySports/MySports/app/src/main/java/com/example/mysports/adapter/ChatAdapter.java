package com.example.mysports.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mysports.activity.ChatActivity;
import com.example.mysports.pojo.Fensi;
import com.example.mysports.R;
import com.example.mysports.pojo.MessageContent;
import com.example.mysports.util.ApplicationUtil;
import com.example.mysports.util.MyImageButton;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.List;

public class ChatAdapter extends BaseAdapter {

    private Context context;
    private List<MessageContent> itemList;

    public ChatAdapter(Context context, List<MessageContent> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public Object getItem(int position) {
        return itemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        MessageContent message = itemList.get(position);
        ViewHolder holder = null;

        if (message.getSenderId()==1) {
            convertView = inflater.inflate(R.layout.chat_dialog_right_item, parent, false);
            holder = new ViewHolder();
            holder.touxiangImage = convertView.findViewById(R.id.iv_chat_imagr_right);
            holder.content = convertView.findViewById(R.id.tv_chat_me_message);
            holder.nickName = convertView.findViewById(R.id.rightNickName);
            holder.date = convertView.findViewById(R.id.right_time);
        } else {
            convertView = inflater.inflate(R.layout.chat_dialog_left_item, parent, false);
            holder = new ViewHolder();
            holder.touxiangImage = convertView.findViewById(R.id.ivicon);
            holder.content = convertView.findViewById(R.id.tvname);
            holder.nickName = convertView.findViewById(R.id.leftNickName);
            holder.date = convertView.findViewById(R.id.left_time);
        }
//        holder.touxiangImage.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View view){
//                Intent intent = new Intent(context,ChatActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("key", (Serializable) itemList.get(position));
//                intent.putExtra(bundle);
//                context.startActivities(intent);
//            }
//        });
        convertView.setTag(holder);
        holder.touxiangImage.setImageResource(message.getHeadSculpture());
        holder.content.setText(message.getContent());
        holder.nickName.setText(message.getNickName());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String data = formatter.format(message.getTime());
        holder.date.setText(data);
        return convertView;
    }

    static class ViewHolder {//复用机制
        ImageView touxiangImage;
        TextView content;
        TextView nickName;
        TextView date;
    }

}
