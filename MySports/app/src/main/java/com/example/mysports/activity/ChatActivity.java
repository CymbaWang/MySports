package com.example.mysports.activity;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.example.mysports.R;
import com.example.mysports.adapter.ChatAdapter;
import com.example.mysports.model.News;
import com.example.mysports.model.Request;
import com.example.mysports.model.User;
import com.example.mysports.pojo.MessageContent;
import com.example.mysports.util.ApplicationUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    Socket socket;
    String buffer;
    EditText content_text;
    Button send_button;
    String content;
    InputStream inputStream;
    OutputStream outputStream;
    ApplicationUtil applicationUtil;
    public static ChatActivity instance=null;
    final List<MessageContent> messageList = new ArrayList<MessageContent>();

//    public Handler myHandler = new Handler(){
//        @Override
//        public void handleMessage(Message msg){
//            if(msg.what==0x11){
//                Bundle bundle = msg.getData();
//                if(bundle.getString("msg").equals("connectError")){
//                    Toast.makeText(ChatActivity.this,"Send Fail",Toast.LENGTH_LONG).show();
//                }
//                else{
//                    Gson gson = new GsonBuilder()
//                            .setDateFormat("yyyy-MM-dd")
//                            .create();
//                    MessageContent messageContent = gson.fromJson(bundle.getString("msg"),MessageContent.class);
//                    ApplicationUtil applicationUtil = (ApplicationUtil) getApplication();
//                    applicationUtil.setMessageContent(messageContent);
//
//                }
//            }
//        }
//    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance=this;
        setContentView(R.layout.activity_chat);
        applicationUtil = (ApplicationUtil)getApplication();
        User user = applicationUtil.getUser();
        ListView listView = (ListView)findViewById(R.id.lv_chat_dialog);

//        messageList.add(new MessageContent(R.drawable.liweisi,"zzy","asndnasd",new Date("2019/5/9 19:27:15"),user.getUserAccount()));
//        messageList.add(new MessageContent(R.drawable.liweisi,"aaa","qqqeerr",new Date("2019/5/9 19:28:45"),user.getUserAccount()));
        ChatAdapter chatAdapter = new ChatAdapter(this.getApplicationContext(),messageList);
        listView.setAdapter(chatAdapter);

        content_text = (EditText)findViewById(R.id.et_chat_message);
        send_button = (Button)findViewById(R.id.btn_chat_message_send);


    }

    public void showMessage(MessageContent mc){
        ListView listView = (ListView)findViewById(R.id.lv_chat_dialog);
        User user = applicationUtil.getUser();
        messageList.add(new MessageContent(R.drawable.liweisi,mc.getNickName(),mc.getContent(),mc.getTime(),mc.getSenderId(),mc.getReceiveId()));
        ChatAdapter chatAdapter = new ChatAdapter(ChatActivity.this,messageList);
        listView.setAdapter(chatAdapter);
    }

    public void onStart(){
        super.onStart();
        send_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                content = content_text.getText().toString();
                if(content.equals(""))
                    Toast.makeText(ChatActivity.this,"NULL",Toast.LENGTH_SHORT).show();
                else
                {
                    ListView listView = (ListView)findViewById(R.id.lv_chat_dialog);
                    User user = applicationUtil.getUser();
                    messageList.add(new MessageContent(R.drawable.liweisi,"aa",content,new Date("2019/5/9 19:27:15"),1,2));
//                    showMessage(new MessageContent(R.drawable.liweisi,"aa",content,new Date("2019/5/9 19:27:15"),1,2));
                    ChatAdapter chatAdapter = new ChatAdapter(ChatActivity.this,messageList);
                    listView.setAdapter(chatAdapter);
                }
                new ChatThread(content).start();
            }
        });
    }



    public void tryit(){
        ListView listView = (ListView)findViewById(R.id.lv_chat_dialog);
    }

    public void onDestroy(){
        super.onDestroy();
        try{
            //关闭各种输入输出流
            inputStream.close();
            outputStream.close();
            socket.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    class ChatThread extends Thread{
        public String content;
        public ChatThread(String content){
            this.content=content;
        }
        @Override
        public void run(){
            Message msg = new Message();
            msg.what = 0x11;
            Bundle bundle = new Bundle();
            bundle.clear();
            try{
                ApplicationUtil applicationUtil = (ApplicationUtil)getApplication();
                if(applicationUtil.getSocket()==null)
                    applicationUtil.init();
                socket = applicationUtil.getSocket();

                outputStream = applicationUtil.getOutputStream();
                inputStream = applicationUtil.getInputStream();
                User user = applicationUtil.getUser();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String data = formatter.format(System.currentTimeMillis());

//                MessageContent messageContent = new MessageContent(1,user.getUserName(),content,new Date(),user.getUserId(),1);
                News news = new News(1,2,content,new Date(),false);

                String jsonStu = JSON.toJSONString(news);
                Request request = new Request(100,jsonStu);
                Gson gson = new Gson();
                String result = gson.toJson(request)+"\n";
                outputStream.write(result.getBytes("UTF-8"));
                outputStream.flush();
                //
//                BufferedReader bff = new BufferedReader(new InputStreamReader(inputStream));
//                String line = null;
//                buffer = "";
//                line = bff.readLine();
//                buffer = line + buffer;
//                bundle.putString("msg",buffer);
//                msg.setData(bundle);
//                myHandler.sendMessage(msg);
            }catch (SocketTimeoutException aa){
                bundle.putString("msg","connectError");
                msg.setData(bundle);
//                myHandler.sendMessage(msg);
            }catch (IOException e){
                e.printStackTrace();
            }catch (Exception ee){
                ee.printStackTrace();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

}
