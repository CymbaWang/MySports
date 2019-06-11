package com.example.mysports.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.example.mysports.ClientListenThread;
import com.example.mysports.R;
import com.example.mysports.model.Request;
import com.example.mysports.model.User;
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

public class LoginActivity extends AppCompatActivity {

    Socket socket;
    String buffer;
    EditText account_text;
    EditText password_text;
    Button login_button;
    Button register_button;
    String account;
    String password;
    InputStream inputStream;
    OutputStream outputStream;
//    MessageListenerThread messageListenerThread;


    public Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            System.out.println("handling");
            if (msg.what == 0x11) {
                Bundle bundle = msg.getData();
                if(bundle.getString("msg").equals("connectError")){
                    Toast.makeText(LoginActivity.this,"未连接网络",Toast.LENGTH_LONG).show();
                }
                else if(bundle.getString("msg").equals("loginfail")){
                    Toast.makeText(LoginActivity.this,"用户名或密码错误",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                    Gson gson = new GsonBuilder()
                            .setDateFormat("yyyy-MM-dd")
                            .create();
                    User user = gson.fromJson(bundle.getString("msg"),User.class);
                    ApplicationUtil applicationUtil = (ApplicationUtil) getApplication();
                    applicationUtil.setUser(user);
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);

                    startActivity(intent);

                }
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        account_text = (EditText)findViewById(R.id.account_text);
        password_text = (EditText)findViewById(R.id.password_text);
        login_button = (Button)findViewById(R.id.login_button);
        register_button = (Button)findViewById(R.id.register_button);
    }

    public void onStart(){
        super.onStart();
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                account=account_text.getText().toString();
                password = password_text.getText().toString();
                if(account.equals("")||password.equals("")){
                    Toast.makeText(LoginActivity.this,"用户名或密码为空",Toast.LENGTH_SHORT).show();
                }else{
                    new LoginThread(account, password).start();

//                    new MessageListenerThread(socket).start();
                }
            }
        });
        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
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

    class LoginThread extends Thread{
        public String account;
        public String password;
        public LoginThread(String account, String password){
            this.account = account;
            this.password = password;
        }
        @Override
        public void run(){
            //定义消息
            Message msg = new Message();
            msg.what = 0x11;
            Bundle bundle = new Bundle();
            bundle.clear();
            try {
                //连接服务器 并设置连接超时为5秒
                ApplicationUtil applicationUtil = (ApplicationUtil) getApplication();
                if(applicationUtil.getSocket()==null)
                    applicationUtil.init();
                socket = applicationUtil.getSocket();
                //获取输入输出流
                inputStream = applicationUtil.getInputStream();
                outputStream = applicationUtil.getOutputStream();

                //向服务器发送信息
                User user = new User(account, password);
                SimplePropertyPreFilter filter = new SimplePropertyPreFilter(User.class, "userAccount","userPassword");
                String jsonStu = JSON.toJSONString(user,filter);
                Request request = new Request(1,jsonStu);
                Gson gson = new Gson();
                String result = gson.toJson(request) +"\n";
                if(outputStream!=null)
                  outputStream.write(result.getBytes("UTF-8"));
              else
                {
                    outputStream=applicationUtil.getSocket().getOutputStream();
                    outputStream.write(result.getBytes("UTF-8"));
                }
                outputStream.flush();
                //半关闭socket(不加此话发不出数据)
                //socket.shutdownOutput();

                //读取发来服务器信息
                BufferedReader bff = new BufferedReader(new InputStreamReader(inputStream));
                String line = null;
                buffer="";
                line = bff.readLine();
                buffer = line + buffer;
                bundle.putString("msg", buffer);
                msg.setData(bundle);
                //发送消息 修改UI线程中的组件
                System.out.println("msg-r");
//                bundle.putString("msg","r");
//                msg.setData(bundle);
                myHandler.sendMessage(msg);
                //bff.close();
            } catch (SocketTimeoutException aa) {
                //连接超时 在UI界面显示消息
                bundle.putString("msg", "connectError");
                msg.setData(bundle);
                //发送消息 修改UI线程中的组件
                myHandler.sendMessage(msg);
            }
            catch (IOException e) {
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
