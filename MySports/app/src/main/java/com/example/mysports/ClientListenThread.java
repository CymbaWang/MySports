package com.example.mysports;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mysports.activity.ChatActivity;
import com.example.mysports.activity.LoginActivity;
import com.example.mysports.activity.MainActivity;
import com.example.mysports.adapter.ChatAdapter;
import com.example.mysports.adapter.ContactAdapter;
import com.example.mysports.model.News;
import com.example.mysports.model.Request;
import com.example.mysports.model.User;
import com.example.mysports.pojo.ContactItem;
import com.example.mysports.pojo.MessageContent;
import com.example.mysports.util.ApplicationUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;
import java.lang.reflect.Type;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class ClientListenThread extends Thread {
    private Socket mSocket = null;
    private ObjectInputStream mOis;
    private InputStream inputStream;
    private MessageContent messageContent;
    private List<ContactItem> contactList = new ArrayList<>();

    private boolean isStart = true;

    final List<MessageContent> messageList = new ArrayList<MessageContent>();

    public ClientListenThread(Socket socket) {
        this.mSocket = socket;
//        try {
//            inputStream = mSocket.getInputStream();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

    }

    public void setSocket(Socket socket) {
        this.mSocket = socket;
    }


    public Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            ChatActivity.instance.showMessage(messageContent);
        }
    };


    public Handler conHandler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            MainActivity.instance.showContactList(contactList);
        }
    };



    @Override
    public void run() {

        try {
            isStart = true;
            while (isStart) {
                System.out.println("isStart");
                String line = null;
                inputStream = mSocket.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));


                //读取来自客户端的数据
                line = bufferedReader.readLine();
                String read = "";
                read = read + line;
                System.out.println("read  :" + read);

                Gson gson = new GsonBuilder()
                        .setDateFormat("yyyy-MM-dd HH:mm:ss")
                        .create();


                Request request = gson.fromJson(read, Request.class);

                if (request.getType() == 100) {
                    System.out.println(request.getData());
                    News news = gson.fromJson(request.getData().toString(), News.class);
                    showNews(news);
                    System.out.println("receive success:" + news.getSendContent());
                }

                else if(request.getType()==102){
                    System.out.println(request.getData());
                    contactList =  gson.fromJson(request.getData(),new TypeToken<List<ContactItem>>() {}.getType());
                    showCon(contactList);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showNews(News news) {
        messageContent = new MessageContent(1, "aa", news.getSendContent(), news.getSendTime(), news.getSenduserId(), news.getReceiveuserId());
        Message message = new Message();
        myHandler.sendMessage(message);
        System.out.println(messageContent.getContent());

    }

    private void showCon(List<ContactItem> contactItemList)
    {
        Message message = new Message();
        conHandler.sendMessage(message);
    }


}
