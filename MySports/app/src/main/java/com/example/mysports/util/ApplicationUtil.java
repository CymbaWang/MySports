package com.example.mysports.util;

import android.app.Application;

import com.example.mysports.model.News;
import com.example.mysports.model.User;
import com.example.mysports.pojo.MessageContent;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.List;
import java.util.Map;


public class ApplicationUtil extends Application {

    public static final String ADDRESS = "192.168.123.244";
    public static final int PORT = 8080;

    private Socket socket;
    private OutputStream outputStream;
    private InputStream inputStream;
    private User user;
    private MessageContent messageContent;
    private List<MessageContent> mMessageContent;
    private Map<Integer,List<News>> mNews;
    private static ApplicationUtil applicationUtil;
    private android.os.Handler messageHandler;

    public void init() {
         try{
             socket = new Socket();
             socket.connect(new InetSocketAddress(ADDRESS,PORT), 5000);
             outputStream = socket.getOutputStream();
            inputStream = socket.getInputStream();

             }catch (IOException e){
                 e.printStackTrace();
            }
            catch (Exception ee){
             ee.printStackTrace();
         }
    }

    public static ApplicationUtil getInstance(){
        if(applicationUtil==null){
            applicationUtil=new ApplicationUtil();
        }
        return applicationUtil;
    }

    public void messageHandle(News news)
    {
        int sender = news.getSenduserId();
        for(int i=0;i<mMessageContent.size();i++){
            MessageContent mContent = mMessageContent.get(i);
            if(mContent.getSenderId()==sender)
                mContent.setSenderId(0);
            else
                mContent.setSenderId(sender);
            mContent.setContent(news.getSendContent());
            mContent.setHeadSculpture(1);
            mContent.setNickName("aa");
            mContent.setReceiveId(news.getReceiveuserId());
            mContent.setTime(news.getSendTime());

        }

    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public OutputStream getOutputStream() {
        return outputStream;
    }

    public void setOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public MessageContent getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(MessageContent messageContent) {
        this.messageContent = messageContent;
    }


    public List<MessageContent> getmMessageContent() {
        return mMessageContent;
    }

    public void setmMessageContent(List<MessageContent> mMessageContent) {
        this.mMessageContent = mMessageContent;
    }


    public Map<Integer, List<News>> getmNews() {
        return mNews;
    }

    public void setmNews(Map<Integer, List<News>> mNews) {
        this.mNews = mNews;
    }

}
