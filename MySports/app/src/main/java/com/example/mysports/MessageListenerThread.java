package com.example.mysports;

import com.example.mysports.model.News;
import com.example.mysports.util.ApplicationUtil;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class MessageListenerThread extends Thread{

    private Socket mSocket = null;
    private ObjectInputStream mOis;

    public MessageListenerThread(Socket socket){
        this.mSocket=socket;
        try {
            mOis = new ObjectInputStream(mSocket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run()
    {
        try{
            while(true){
                News news = null;
                news = (News)mOis.readObject();
                ApplicationUtil.getInstance().messageHandle(news);
            }
        }catch (IOException e){
            e.printStackTrace();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }
}
