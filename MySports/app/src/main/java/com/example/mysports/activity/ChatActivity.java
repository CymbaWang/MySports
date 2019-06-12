package com.example.mysports.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
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
import com.example.mysports.util.GlideLoader;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lcw.library.imagepicker.ImagePicker;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatActivity extends AppCompatActivity {

    Socket socket;

    EditText content_text;
    Button send_button;
    String content;
    Button getPic;
    OutputStream outputStream;
    ApplicationUtil applicationUtil;
    String mImagePaths;
    int conId;
    public static ChatActivity instance=null;
    final List<MessageContent> messageList = new ArrayList<MessageContent>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance=this;
        conId = MainActivity.clickConId;
        setContentView(R.layout.activity_chat);
        applicationUtil = (ApplicationUtil)getApplication();
        User user = applicationUtil.getUser();
        ListView listView = (ListView)findViewById(R.id.lv_chat_dialog);
        ChatAdapter chatAdapter = new ChatAdapter(this.getApplicationContext(),messageList,user);
        listView.setAdapter(chatAdapter);

        content_text = (EditText)findViewById(R.id.et_chat_message);
        send_button = (Button)findViewById(R.id.btn_chat_message_send);
        getPic=(Button)findViewById(R.id.getpic);


    }

    public void showMessage(List<MessageContent> mc){
        ListView listView = (ListView)findViewById(R.id.lv_chat_dialog);
        User user = applicationUtil.getUser();
        for(int i=0;i<mc.size();i++)
        {
            messageList.add(new MessageContent(R.drawable.liweisi,mc.get(i).getNickName(),mc.get(i).getContent(),mc.get(i).getTime(),mc.get(i).getSenderId(),mc.get(i).getReceiveId()));
        }
        ChatAdapter chatAdapter = new ChatAdapter(ChatActivity.this,messageList,user);
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
                    messageList.add(new MessageContent(R.drawable.liweisi,user.getUserName(),content,new Date(),user.getUserId(),conId));
                    ChatAdapter chatAdapter = new ChatAdapter(ChatActivity.this,messageList,user);
                    listView.setAdapter(chatAdapter);
                }
                new ChatThread(content).start();

                content_text.setText("");
            }
        });

        getPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.getInstance()
                        .setTitle("发动态")//设置标题
                        .showCamera(true)//设置是否显示拍照按钮
                        .showImage(true)//设置是否展示图片
                        .showVideo(true)//设置是否展示视频
                        .setMaxCount(1)//设置最大选择图片数目(默认为1，单选)
                        .setImageLoader(new GlideLoader())//设置自定义图片加载器
                        .start(ChatActivity.this, 2);//REQEST_SELECT_IMAGES_CODE为Intent调用的requestCode

                while(mImagePaths!=null)
                {
                    new SendPic().start();
                }

            }
        });

    }

    public class SendPic extends Thread
    {
        @Override
        public void run()
        {
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.qt);
            imageView02.setImageBitmap(bitmap);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            //读取图片到ByteArrayOutputStream
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] bytes = baos.toByteArray();
            out.write(bytes);
        }

    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case 2:
                    mImagePaths = data.getStringArrayListExtra(ImagePicker.EXTRA_SELECT_IMAGES).get(0);
                    System.out.println(mImagePaths);
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }



//    public void tryit(){
//        ListView listView = (ListView)findViewById(R.id.lv_chat_dialog);
//    }

    public void onDestroy(){
        super.onDestroy();
        try{
            //关闭各种输入输出流
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
                User user = applicationUtil.getUser();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String data = formatter.format(System.currentTimeMillis());

//                MessageContent messageContent = new MessageContent(1,user.getUserName(),content,new Date(),user.getUserId(),1);
                News news = new News(user.getUserId(),conId,content,new Date(),false);

                String jsonStu = JSON.toJSONString(news);
                Request request = new Request(100,jsonStu);
                Gson gson = new Gson();
                String result = gson.toJson(request)+"\n";
                outputStream.write(result.getBytes("UTF-8"));
                outputStream.flush();

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
