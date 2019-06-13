package com.example.mysports;


import android.os.Handler;
import android.os.Message;

import com.example.mysports.activity.ChatActivity;
import com.example.mysports.activity.MainActivity;
import com.example.mysports.model.News;
import com.example.mysports.model.Request;
import com.example.mysports.model.User;
import com.example.mysports.pojo.ContactItem;
import com.example.mysports.pojo.ContactTemp;
import com.example.mysports.pojo.MessageContent;
import com.example.mysports.pojo.MsgNews;
import com.example.mysports.pojo.MsgNewsTemp;
import com.example.mysports.pojo.NewsTemp;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.lang.reflect.Type;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class ClientListenThread extends Thread {
    private User user = null;
    private Socket mSocket = null;
    private ObjectInputStream mOis;
    private InputStream inputStream;
    private MessageContent messageContent = null;
    private List<ContactItem> contactList = new ArrayList<>();

    private boolean isStart = true;

    final List<MessageContent> messageList = new ArrayList<MessageContent>();

    public ClientListenThread(Socket socket, User user) {
        this.user = user;
        this.mSocket = socket;
    }


    public Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            ChatActivity.instance.showMessage(messageList);
        }
    };


    public Handler conHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
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
//                System.out.println("read  :" + read);

                Gson gson = new GsonBuilder()
                        .setDateFormat("yyyy-MM-dd HH:mm:ss")
                        .create();

                Request request = gson.fromJson(read, Request.class);

                if (request.getType() == 100) {
//                    System.out.println(request.getData());
                    MsgNewsTemp msgNewsTemp = gson.fromJson(request.getData().toString(), MsgNewsTemp.class);
                    MsgNews msgNews = new MsgNews();
                    NewsTemp newsTemp = new NewsTemp();
                    newsTemp = msgNewsTemp.getNews();
                    News news = new News();

                    news.setNewsId(newsTemp.getNewsId());
                    news.setNewsState(newsTemp.isNewsState());
                    news.setReceiveuserId(newsTemp.getReceiveuserId());
                    news.setSendContent(newsTemp.getSendContent());
                    news.setSenduserId(newsTemp.getSenduserId());
                    Date date = new Date(newsTemp.getSendTime());
                    news.setSendTime(date);
                    msgNews.setNews(news);
                    msgNews.setReceiveName(msgNewsTemp.getReceiveName());
                    msgNews.setSendName(msgNewsTemp.getSendName());

                    showNews(msgNews);

                    Message message = new Message();
                    myHandler.sendMessage(message);

                    System.out.println("this is request function and i execute the 100");
//                    System.out.println("receive success:" + msgNewsTemp.getNews().getSendContent());
                } else if (request.getType() == 102) {
//                    System.out.println(request.getData());

                    Gson gson2 = new GsonBuilder()
                            .setDateFormat("yyyy-MM-dd HH:mm:ss")
                            .create();

                    Type listType = new TypeToken<List<ContactTemp>>() {
                    }.getType();
                    String str = request.getData();
                    List<ContactTemp> contactTempList = new ArrayList<>();
                    contactTempList = gson2.fromJson(request.getData(), listType);
                    ContactItem contactItem = null;
                    for (int i = 0; i < contactTempList.size(); i++) {
                        contactItem = new ContactItem();
//                        contactItem.setUserId(contactTempList.get(i).getUserId());
                        contactItem.setConId(contactTempList.get(i).getConId());
                        contactItem.setContent(contactTempList.get(i).getContent());
                        contactItem.setHeadSculpture(contactTempList.get(i).getHeadSculpture());
                        contactItem.setNickName(contactTempList.get(i).getNickName());
                        Date date = new Date(contactTempList.get(i).getTime());
                        contactItem.setTime(date);
                        contactList.add(contactItem);
                    }
                    showCon(contactList);
                } else if (request.getType() == 106) {
//                    System.out.println(request.getData());
                    List<MsgNewsTemp> msgNewsTempList = new ArrayList<MsgNewsTemp>();
                    msgNewsTempList = gson.fromJson(request.getData().toString(), new TypeToken<List<MsgNewsTemp>>() {
                    }.getType());
                    News news;
                    NewsTemp newsTemp;
                    MsgNews msgNews;
                    for (int i = 0; i < msgNewsTempList.size(); i++) {
                        news = new News();
                        newsTemp = new NewsTemp();
                        msgNews = new MsgNews();
                        newsTemp = msgNewsTempList.get(i).getNews();
                        news.setNewsId(newsTemp.getNewsId());
                        news.setNewsState(newsTemp.isNewsState());
                        news.setReceiveuserId(newsTemp.getReceiveuserId());
                        news.setSendContent(newsTemp.getSendContent());
                        news.setSenduserId(newsTemp.getSenduserId());
                        Date date = new Date(newsTemp.getSendTime());
                        news.setSendTime(date);
                        msgNews.setNews(news);
                        msgNews.setReceiveName(msgNewsTempList.get(i).getReceiveName());
                        msgNews.setSendName(msgNewsTempList.get(i).getSendName());
                        showNews(msgNews);
                    }

                    Message message = new Message();
                    myHandler.sendMessage(message);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showNews(MsgNews msgNews) {
        messageContent = new MessageContent();

        messageContent.setTime(msgNews.getNews().getSendTime());
        messageContent.setReceiveId(msgNews.getNews().getReceiveuserId());
        messageContent.setNickName(msgNews.getSendName());
        messageContent.setHeadSculpture(1);
        messageContent.setContent(msgNews.getNews().getSendContent());
        messageContent.setSenderId(msgNews.getNews().getSenduserId());
        if(msgNews.getNews().isNewsState())
        {
            System.out.println("the status is true and it's pic");
            messageContent.setType(1);
        }

        else
        {
            System.out.println("the status is false and it's string");
            messageContent.setType(0);
        }

//        messageContent.setType(msgNews.getNews().isNewsState());


        messageList.add(messageContent);

    }

    private void showCon(List<ContactItem> contactItemList) {
        Message message = new Message();
        conHandler.sendMessage(message);
    }

}
