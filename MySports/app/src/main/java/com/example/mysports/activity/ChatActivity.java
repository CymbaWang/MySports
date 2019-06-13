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
import com.example.mysports.pojo.MsgNews;
import com.example.mysports.util.ApplicationUtil;
import com.example.mysports.util.BASE64Decoder;
import com.example.mysports.util.BASE64Encoder;
import com.example.mysports.util.GlideLoader;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lcw.library.imagepicker.ImagePicker;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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
import java.util.zip.GZIPOutputStream;

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
    public static ChatActivity instance = null;
    final List<MessageContent> messageList = new ArrayList<MessageContent>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        conId = MainActivity.clickConId;
        setContentView(R.layout.activity_chat);
        applicationUtil = (ApplicationUtil) getApplication();
        User user = applicationUtil.getUser();
        ListView listView = (ListView) findViewById(R.id.lv_chat_dialog);
        ChatAdapter chatAdapter = new ChatAdapter(this.getApplicationContext(), messageList, user);
        listView.setAdapter(chatAdapter);

        content_text = (EditText) findViewById(R.id.et_chat_message);
        send_button = (Button) findViewById(R.id.btn_chat_message_send);
        getPic = (Button) findViewById(R.id.getpic);

        socket = applicationUtil.getSocket();
    }

    public Handler imgHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            ChatActivity.instance.showIMG(messageList);
        }
    };


    public void showMessage(List<MessageContent> mc) {
        ListView listView = (ListView) findViewById(R.id.lv_chat_dialog);
        User user = applicationUtil.getUser();
        for (int i = 0; i < mc.size(); i++) {
            messageList.add(new MessageContent(R.drawable.liweisi, mc.get(i).getNickName(), mc.get(i).getContent(), mc.get(i).getTime(), mc.get(i).getSenderId(), mc.get(i).getReceiveId(), mc.get(i).getType()));
        }
        ChatAdapter chatAdapter = new ChatAdapter(ChatActivity.this, messageList, user);
        listView.setAdapter(chatAdapter);
    }

    public void onStart() {
        super.onStart();
        send_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                content = content_text.getText().toString();
                if (content.equals(""))
                    Toast.makeText(ChatActivity.this, "NULL", Toast.LENGTH_SHORT).show();
                else {
                    ListView listView = (ListView) findViewById(R.id.lv_chat_dialog);
                    User user = applicationUtil.getUser();
                    messageList.add(new MessageContent(R.drawable.liweisi, user.getUserName(), content, new Date(), user.getUserId(), conId, 0));
                    ChatAdapter chatAdapter = new ChatAdapter(ChatActivity.this, messageList, user);
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
                        .setTitle("选择图片")//设置标题
                        .showCamera(true)//设置是否显示拍照按钮
                        .showImage(true)//设置是否展示图片
                        .showVideo(true)//设置是否展示视频
                        .setMaxCount(1)//设置最大选择图片数目(默认为1，单选)
                        .setImageLoader(new GlideLoader())//设置自定义图片加载器
                        .start(ChatActivity.this, 2);//REQEST_SELECT_IMAGES_CODE为Intent调用的requestCode


                new ImgViewAndStart().start();

            }
        });

    }

    private void showIMG(List<MessageContent> messageList) {
        ListView listView = (ListView) findViewById(R.id.lv_chat_dialog);
        User user = applicationUtil.getUser();
        ChatAdapter chatAdapter = new ChatAdapter(ChatActivity.this, messageList, user);
        listView.setAdapter(chatAdapter);
    }

    public class ImgViewAndStart extends Thread {
        @Override
        public void run() {
            boolean temp = true;
            while (temp) {
                if (mImagePaths != null) {
                    String imgp = "";
                    imgp=mImagePaths;
                    User user = applicationUtil.getUser();
                    messageList.add(new MessageContent(R.drawable.liweisi, user.getUserName(), imgp, new Date(), user.getUserId(), conId, 2));


                    Message message = new Message();
                    imgHandler.sendMessage(message);

                    new SendPic().start();

                    temp = false;
                }
            }
        }
    }

    public class SendPic extends Thread {
        @Override
        public void run() {
            try {
//                File img = new File(mImagePaths);
                String content = fileToString(mImagePaths);

                mImagePaths=null;

                ApplicationUtil applicationUtil = (ApplicationUtil) getApplication();
                if (applicationUtil.getSocket() == null)
                    applicationUtil.init();
                socket = applicationUtil.getSocket();

                outputStream = applicationUtil.getOutputStream();
                User user = applicationUtil.getUser();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String data = formatter.format(System.currentTimeMillis());

//                MessageContent messageContent = new MessageContent(1,user.getUserName(),content,new Date(),user.getUserId(),1);
                News news = new News(user.getUserId(), conId, content, new Date(), true);

                String jsonStu = JSON.toJSONString(news);
                Request request = new Request(108, jsonStu);
                Gson gson = new Gson();
                String result = gson.toJson(request) + "\n";
                outputStream.write(result.getBytes("UTF-8"));
                outputStream.flush();


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private byte[] loadImage(File file) {
        //用于返回的字节数组
        byte[] data = null;
        //打开文件输入流
        FileInputStream fin = null;
        //打开字节输出流
        ByteArrayOutputStream bout = null;
        try {
            //文件输入流获取对应文件
            fin = new FileInputStream(file);
            //输出流定义缓冲区大小
            bout = new ByteArrayOutputStream((int) file.length());
            //定义字节数组，用于读取文件流
            byte[] buffer = new byte[1024];
            //用于表示读取的位置
            int len = -1;
            //开始读取文件
            while ((len = fin.read(buffer)) != -1) {
                //从buffer的第0位置开始，读取至第len位置，结果写入bout
                bout.write(buffer, 0, len);
            }
            //将输出流转为字节数组
            data = bout.toByteArray();
            //关闭输入输出流
            fin.close();
            bout.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }


    private String byteToString(byte[] data) {
        String dataString = null;
        try {
            //将字节数组转为字符串，编码格式为ISO-8859-1
            dataString = new String(data, "ISO-8859-1");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataString;
    }


    private String compress(String data) {
        String finalData = null;
        try {
            //打开字节输出流
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            //打开压缩用的输出流,压缩后的结果放在bout中
            GZIPOutputStream gout = new GZIPOutputStream(bout);
            //写入待压缩的字节数组
            gout.write(data.getBytes("ISO-8859-1"));
            //完成压缩写入
            gout.finish();
            //关闭输出流
            gout.close();
            finalData = bout.toString("ISO-8859-1");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return finalData;
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

//    public void onDestroy() {
//        super.onDestroy();
//        messageList.clear();
//    }


    class ChatThread extends Thread {
        public String content;

        public ChatThread(String content) {
            this.content = content;
        }

        @Override
        public void run() {
            Message msg = new Message();
            msg.what = 0x11;
            Bundle bundle = new Bundle();
            bundle.clear();
            try {
                ApplicationUtil applicationUtil = (ApplicationUtil) getApplication();
                if (applicationUtil.getSocket() == null)
                    applicationUtil.init();
                socket = applicationUtil.getSocket();

                outputStream = applicationUtil.getOutputStream();
                User user = applicationUtil.getUser();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String data = formatter.format(System.currentTimeMillis());

//                MessageContent messageContent = new MessageContent(1,user.getUserName(),content,new Date(),user.getUserId(),1);
                News news = new News(user.getUserId(), conId, content, new Date(), false);

                String jsonStu = JSON.toJSONString(news);
                Request request = new Request(100, jsonStu);
                Gson gson = new Gson();
                String result = gson.toJson(request) + "\n";
                outputStream.write(result.getBytes("UTF-8"));
                outputStream.flush();

            } catch (SocketTimeoutException aa) {
                bundle.putString("msg", "connectError");
                msg.setData(bundle);
//                myHandler.sendMessage(msg);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception ee) {
                ee.printStackTrace();
            }
        }
    }

    //**************************************************************
    /**
     * summary:将字符串存储为文件 采用Base64解码

     */
    public static void streamSaveAsFile(InputStream is, String outFileStr) {
        FileOutputStream fos = null;
        try {
            File file = new File(outFileStr);
            BASE64Decoder decoder = new BASE64Decoder();
            fos = new FileOutputStream(file);
            byte[] buffer = decoder.decodeBuffer(is);
            fos.write(buffer, 0, buffer.length);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            try {
                is.close();
                fos.close();
            } catch (Exception e2) {
                e2.printStackTrace();
                throw new RuntimeException(e2);
            }
        }
    }

    /**
     *
     *
     * summary:将字符串存储为文件
     *

     */
    public static void stringSaveAsFile(String fileStr, String outFilePath) {
        InputStream out = new ByteArrayInputStream(fileStr.getBytes());
        streamSaveAsFile(out, outFilePath);
    }

    /**
     * 将流转换成字符串 使用Base64加密

     */
    public static String streamToString(InputStream inputStream) throws IOException {
        byte[] bt = toByteArray(inputStream);
        inputStream.close();
        String out = new BASE64Encoder().encodeBuffer(bt);
        return out;
    }

    /**
     * 将流转换成字符串

     */
    public static String fileToString(String filePath) throws IOException {
        File file = new File(filePath);
        FileInputStream is = new FileInputStream(file);
        String fileStr = streamToString(is);
        return fileStr;
    }

    /**
     *
     * summary:将流转化为字节数组

     *
     */
    public static byte[] toByteArray(InputStream inputStream) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024 * 4];
        byte[] result = null;
        try {
            int n = 0;
            while ((n = inputStream.read(buffer)) != -1) {
                out.write(buffer, 0, n);
            }
            result = out.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            out.close();
        }
        return result;
    }

    //*************************************************

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

}
