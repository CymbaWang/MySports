package com.example.mysports;

import android.widget.ListView;

import com.example.mysports.model.News;
import com.example.mysports.model.Request;
import com.example.mysports.pojo.MessageContent;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientListenThread extends Thread{
    private Socket mSocket = null;
    private ObjectInputStream mOis;

    private boolean isStart = true;

    final List<MessageContent> messageList = new ArrayList<MessageContent>();

    public ClientListenThread(Socket socket)
    {
        this.mSocket = socket;
        try{
            mOis = new ObjectInputStream(mSocket.getInputStream());
        }catch (StreamCorruptedException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void setSocket(Socket socket){
        this.mSocket=socket;
    }

    @Override
    public void run(){
        try{
            isStart=true;
            String str = null;
            str = (String)mOis.readObject();
//            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(input));
            Gson gson =  new Gson();

            Request request = gson.fromJson(str, Request.class);
            while(isStart){
                if(request.getType()==100)
                {
                    News news = gson.fromJson(request.getData(),News.class);
                    System.out.println("receive success:"+news.getSendContent());
                }

            }
        }catch (IOException e){
            e.printStackTrace();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public void showNews(News news)
    {
        MessageContent messageContent = new MessageContent(1,"aa",news.getSendContent(),news.getSendTime(),news.getSenduserId(),news.getReceiveuserId());


    }

}
