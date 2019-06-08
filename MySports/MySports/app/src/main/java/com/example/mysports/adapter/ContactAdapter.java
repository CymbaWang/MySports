package com.example.mysports.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mysports.R;
import com.example.mysports.pojo.ContactItem;

import java.text.SimpleDateFormat;
import java.util.List;

public class ContactAdapter extends ArrayAdapter<ContactItem> {
    private int resourceId;
    public ContactAdapter(Context context, int textViewResourceId, List<ContactItem> objects){
        super(context,textViewResourceId,objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ContactItem contactItem = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        ImageView imageView = (ImageView)view.findViewById(R.id.headSculpture);
        TextView nameView = (TextView)view.findViewById(R.id.nickName);
        TextView textView = (TextView)view.findViewById(R.id.messageContent);
        TextView timeView = (TextView)view.findViewById(R.id.lastMessageTime);
        imageView.setImageResource(contactItem.getHeadSculpture());
        nameView.setText(contactItem.getNickName());
        textView.setText(contactItem.getContent());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String data = formatter.format(contactItem.getTime());
        timeView.setText(data);
        return view;
    }
}
