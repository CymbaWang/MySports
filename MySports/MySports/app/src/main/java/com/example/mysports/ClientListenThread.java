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
import com.example.mysports.model.News;
import com.example.mysports.model.Request;
import com.example.mysports.model.User;
import com.example.mysports.pojo.MessageContent;
import com.example.mysports.util.ApplicationUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

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
//                Gson gson ;


                Gson gson = new GsonBuilder()
                        .setDateFormat("yyyy-MM-dd HH:mm:ss")
                        .create();


//                GsonBuilder builder = new GsonBuilder();
//                builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
//                    public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
//                        return new Date(json.getAsJsonPrimitive().getAsLong());
//                    }
//                });
//                gson = builder.create();


                Request request = gson.fromJson(read, Request.class);

                if (request.getType() == 100) {
                    System.out.println(request.getData());
//                try{
//                    InputStream inputStream = mSocket.getInputStream();
//                    mOis = new ObjectInputStream(inputStream);
//                }catch (StreamCorruptedException e){
//                    e.printStackTrace();
//                }catch (IOException e){
//                    e.printStackTrace();
//                }
//                News news;
//                Object obj = mOis.readObject();
//                news = (News)obj;


                    News news = gson.fromJson(request.getData().toString(), News.class);
                    showNews(news);
                    System.out.println("receive success:" + news.getSendContent());
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
//        catch (ClassNotFoundException e){
//            e.printStackTrace();
//        }
    }

    public void showNews(News news) {
        messageContent = new MessageContent(1, "aa", news.getSendContent(), news.getSendTime(), news.getSenduserId(), news.getReceiveuserId());
        Message message = new Message();
        myHandler.sendMessage(message);
        System.out.println(messageContent.getContent());

    }


}
