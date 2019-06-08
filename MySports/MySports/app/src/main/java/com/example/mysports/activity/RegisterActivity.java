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
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.example.mysports.R;
import com.example.mysports.model.Request;
import com.example.mysports.model.User;
import com.example.mysports.util.ApplicationUtil;
import com.example.mysports.util.DataUtil;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Date;

public class RegisterActivity extends AppCompatActivity {

    Socket socket;
    String buffer;  //用于读取来自服务器的信息
    EditText account_text, password_text,passwordTwice_text ,name_text, phone_text, email_text;
    RadioGroup sex;
    Button register_button;
    ImageView fanhui_button;
    String account, password,passwordTwice, name, phone, email;
    boolean sexy;
    InputStream inputStream;
    OutputStream outputStream;
    public Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0x11) {
                Bundle bundle = msg.getData();
                if(bundle.getString("msg").equals("connectError")){
                    Toast.makeText(RegisterActivity.this,"未连接网络",Toast.LENGTH_LONG).show();
                }
                else if(bundle.getString("msg").equals("registersuccess")){
                    Toast.makeText(RegisterActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                    startActivity(intent);
                }else if(bundle.getString("msg").equals("accountTwice")){
                    Toast.makeText(RegisterActivity.this,"账号重复注册",Toast.LENGTH_SHORT).show();
                }else if(bundle.getString("msg").equals("nameTwice")){
                    Toast.makeText(RegisterActivity.this,"昵称重复，换个昵称吧",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(RegisterActivity.this,"账号注册失败",Toast.LENGTH_SHORT).show();
                }
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        account_text = (EditText)findViewById(R.id.account_text);
        password_text = (EditText)findViewById(R.id.password_text);
        passwordTwice_text = (EditText)findViewById(R.id.passwordTwice_text);
        name_text = (EditText)findViewById(R.id.name_text);
        sex = (RadioGroup)findViewById(R.id.radioGroup1);
        phone_text = (EditText)findViewById(R.id.phone_text);
        email_text = (EditText)findViewById(R.id.email_text);
        register_button = (Button)findViewById(R.id.register_button);
        fanhui_button = (ImageView)findViewById(R.id.fanhui_button);
    }

    public void onStart(){
        super.onStart();
        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                account=account_text.getText().toString();
                password = password_text.getText().toString();
                passwordTwice = passwordTwice_text.getText().toString();
                name = name_text.getText().toString();
                for(int i=0;i<sex.getChildCount();i++)
                {
                    RadioButton radioButton = (RadioButton)sex.getChildAt(i);
                    if(radioButton.isChecked()){
                        if(radioButton.getText().toString().equals("男")){
                            sexy = true;
                        }else {
                            sexy = false;
                        }
                        break;
                    }
                }
                phone = phone_text.getText().toString();
                email = email_text.getText().toString();
                if(account.equals("")||password.equals("")||passwordTwice.equals("")||name.equals("")||phone.equals("")||email.equals(""))
                    Toast.makeText(RegisterActivity.this, "信息未填写完整",Toast.LENGTH_SHORT).show();
                else if(!(password.equals(passwordTwice))){
                    Toast.makeText(RegisterActivity.this, "两次密码不一致",Toast.LENGTH_SHORT).show();
                }else if(!(phone.length() == 11)){
                    Toast.makeText(RegisterActivity.this, "手机号码必须为11位",Toast.LENGTH_SHORT).show();
                }
                else
                    new RegisterThread(account, password, name, sexy, phone, email).start();
            }
        });
        fanhui_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
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

    class RegisterThread extends Thread{
        public String account;
        public String password;
        public String name;
        public boolean sexy;
        public String phone;
        public String email;
        public RegisterThread(String account, String password, String name, boolean sexy, String phone, String email){
            this.account = account;
            this.password = password;
            this.name = name;
            this.sexy = sexy;
            this.phone = phone;
            this.email = email;
        }
        @Override
        public void run(){
            //定义消息
            Message msg = new Message();
            msg.what = 0x11;
            Bundle bundle = new Bundle();
            bundle.clear();
            try {
                ApplicationUtil applicationUtil = (ApplicationUtil)getApplication();
                if(applicationUtil.getSocket()==null)
                    applicationUtil.init();
                socket = applicationUtil.getSocket();
                //获取输入输出流
                outputStream = applicationUtil.getOutputStream();
                inputStream = applicationUtil.getInputStream();

                //向服务器发送信息
                DataUtil dataUtil = new DataUtil();
                User user = new User(account, password, name, sexy, phone, email, dataUtil.getDate(new Date(System.currentTimeMillis())));
                SimplePropertyPreFilter filter = new SimplePropertyPreFilter(User.class, "userAccount","userPassword","userName","userSex","userPhone","userEmail","userRegtime");
                String jsonStu = JSON.toJSONString(user,filter);
                Request request = new Request(0,jsonStu);
                Gson gson = new Gson();
                String result = gson.toJson(request)+"\n";
                outputStream.write(result.getBytes("UTF-8"));
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
                myHandler.sendMessage(msg);
//                bff.close();
//                inputStream.close();
//                outputStream.close();
            } catch (SocketTimeoutException aa) {
                //连接超时 在UI界面显示消息
                bundle.putString("msg", "connectError");
                msg.setData(bundle);
                //发送消息 修改UI线程中的组件
                myHandler.sendMessage(msg);
            }
            catch (IOException e) {
                e.printStackTrace();
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
