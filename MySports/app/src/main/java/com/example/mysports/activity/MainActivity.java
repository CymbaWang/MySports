package com.example.mysports.activity;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.example.mysports.ClientListenThread;
import com.example.mysports.R;
import com.example.mysports.adapter.ChatAdapter;
import com.example.mysports.adapter.ContactAdapter;
import com.example.mysports.model.Request;
import com.example.mysports.model.User;
import com.example.mysports.pojo.ContactItem;
import com.example.mysports.pojo.MessageContent;
import com.example.mysports.pojo.MsgRequest;
import com.example.mysports.util.ApplicationUtil;
import com.google.gson.Gson;
import com.lcw.library.imagepicker.ImagePicker;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements android.view.View.OnClickListener {
    private TextView username_text;
    //临时图片路径
    private File tempFile = new File(Environment.getExternalStorageDirectory(), getPhotoFileName());

    private static final int TAKE_PHOTO = 1;
    private static final int PHOTO_REQUEST_CUT = 2;
    private ViewPager mViewPager;    //用来放置界面切换
    private PagerAdapter mPagerAdapter;     //初始化View适配器
    private List<View> mViews = new ArrayList<View>();
    //5个Tab，每个Tab包含一个按钮
    private LinearLayout mTabYundong;
    private LinearLayout mTabShequ;
    private LinearLayout mTabFaxian;
    private LinearLayout mTabXiaoxi;
    private LinearLayout mTabMe;
    //5个按钮
    private ImageButton mYundong;
    private ImageButton mShequ;
    private ImageButton mFaxian;
    private ImageButton mXiaoxi;
    private ImageButton mMe;
    public static MainActivity instance = null;
    public static int clickConId=0;

    Socket socket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ApplicationUtil applicationUtil = (ApplicationUtil)getApplication();
        socket=applicationUtil.getSocket();
        instance = this;
        new ClientListenThread(socket,applicationUtil.getUser()).start();
        requestWindowFeature(Window.FEATURE_NO_TITLE);  //去掉程序原有的标题栏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏
        setContentView(R.layout.activity_main);
        initView();
        initViewPage();
        initEvent();
    }

    //拍照
    private void takePhoto() {
        try {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
            PackageManager packageManager = getApplicationContext().getPackageManager();
            if (packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
                startActivityForResult(intent, TAKE_PHOTO);
            }
        } catch (ActivityNotFoundException e) {
            Log.d("FirstActivity.this", "找不到该活动");
        }
    }

    //获得照片名字
    private String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
        return "IMG_" + dateFormat.format(date);
    }

    //开启剪裁
    private void startPhotoZoom(Uri uri, int size) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // crop为true是设置在开启的intent中设置显示的view可以剪裁
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX,outputY 是剪裁图片的宽高
        intent.putExtra("outputX", size);
        intent.putExtra("outputY", size);
        intent.putExtra("scale", true);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }

    //将进行剪裁后的图片显示到UI界面上
    private void setPicToView(Intent picdata) {
        Bundle bundle = picdata.getExtras();
        if (bundle != null) {
            Bitmap photo = bundle.getParcelable("data");
            SaveImage(photo);
        }
    }

    //保存拍照的图片
    public void SaveImage(Bitmap bitmap) {
        /*将bitmap保存下来*/
        /* 将Bitmap设定到ImageView */
        File filest = new File(Environment.getExternalStorageDirectory() + "/image");
        if (!filest.exists()) {
            boolean resyltBool = filest.mkdirs();
        }
        File file = new File(Environment.getExternalStorageDirectory() + "/image/" + getPhotoFileName() + ".jpg");
        try {
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bufferedOutputStream);
            bufferedOutputStream.flush();
            bufferedOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case TAKE_PHOTO:
                    //照相
                    startPhotoZoom(Uri.fromFile(tempFile), 15);
                    break;
                case PHOTO_REQUEST_CUT:
                    //剪裁并设置头像
                    if (data != null) {
                        setPicToView(data);
                        takePhoto();
                    }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void initEvent() {
        mTabYundong.setOnClickListener(this);
        mTabShequ.setOnClickListener(this);
        mTabFaxian.setOnClickListener(this);
        mTabXiaoxi.setOnClickListener(this);
        mTabMe.setOnClickListener(this);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                TextView textView1 = (TextView) findViewById(R.id.titleTv);
                int currentItem = mViewPager.getCurrentItem();
                switch (currentItem) {
                    case 0:
                        resetImg();
                        mYundong.setImageResource(R.drawable.yundong_pressed);
                        textView1.setText("运动");
                        break;
                    case 1:
                        resetImg();
                        mShequ.setImageResource(R.drawable.shequ_pressed);
                        textView1.setText("社区");
                        break;
                    case 2:
                        resetImg();
                        mFaxian.setImageResource(R.drawable.faxian_pressed);
                        textView1.setText("发现");
                        break;
                    case 3:
                        resetImg();
                        mXiaoxi.setImageResource(R.drawable.xiaoxi_pressed);
                        textView1.setText("消息");
                        break;
                    case 4:
                        resetImg();
                        mMe.setImageResource(R.drawable.me_pressed);
                        textView1.setText("我");
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    //初始化设置
    public void initView() {
        mViewPager = (ViewPager) findViewById(R.id.viewpage);
        //初始化5个LinearLayout
        mTabYundong = (LinearLayout) findViewById(R.id.tab_yundong);
        mTabShequ = (LinearLayout) findViewById(R.id.tab_shequ);
        mTabFaxian = (LinearLayout) findViewById(R.id.tab_faxian);
        mTabXiaoxi = (LinearLayout) findViewById(R.id.tab_xiaoxi);
        mTabMe = (LinearLayout) findViewById(R.id.tab_me);
        //初始化5个按钮
        mYundong = (ImageButton) findViewById(R.id.tab_yundong_img);
        mShequ = (ImageButton) findViewById(R.id.tab_shequ_img);
        mFaxian = (ImageButton) findViewById(R.id.tab_faxian_img);
        mXiaoxi = (ImageButton) findViewById(R.id.tab_xiaoxi_img);
        mMe = (ImageButton) findViewById(R.id.tab_me_img);
    }

    //初始化ViewPage
    private void initViewPage() {

        //初始化5个布局
        LayoutInflater mLayoutInflater = LayoutInflater.from(this);
        View tab1 = mLayoutInflater.inflate(R.layout.fragment_yundong, null);
        View tab2 = mLayoutInflater.inflate(R.layout.fragment_shequ, null);
        View tab3 = mLayoutInflater.inflate(R.layout.fragment_faxian, null);
        View tab4 = mLayoutInflater.inflate(R.layout.fragment_xiaoxi, null);
        View tab5 = mLayoutInflater.inflate(R.layout.fragment_me, null);

//        tab4.findViewById(R.id.).setOnClickListener(new View.OnClickListener() {
//            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, ChatActivity.class);
//                startActivity(intent);
//            }
//        });
        ListView lv = (ListView)tab4.findViewById(R.id.xiaoxi_list);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ApplicationUtil applicationUtil = (ApplicationUtil) getApplication();
                List<ContactItem> contactItemList = new ArrayList<>();
                contactItemList=applicationUtil.getList();
                System.out.println("this is click listener and you click the button just now ,which means that the position is " + position+" and the conid is "+contactItemList.get(position).getConId());
                clickConId=contactItemList.get(position).getConId();
                new GetMsgThread(applicationUtil.getUser().getUserId(),contactItemList.get(position).getConId()).start();
                Intent intent = new Intent(MainActivity.this, ChatActivity.class);
                startActivity(intent);
            }
        });

        tab5.findViewById(R.id.qiandao_button).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ImageButton qiandao_button = (ImageButton) findViewById(R.id.qiandao_button);
                qiandao_button.setImageResource(R.drawable.yiqiandao);
            }
        });

        tab5.findViewById(R.id.ziliao_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ZiliaoActivity.class);
                startActivity(intent);
            }
        });

        tab5.findViewById(R.id.guanzhu_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GuanzhuActivity.class);
                startActivity(intent);
            }
        });

        tab5.findViewById(R.id.touxiang_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MeActivity.class);
                startActivity(intent);
            }
        });

        tab5.findViewById(R.id.fensi_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FensiActivity.class);
                startActivity(intent);
            }
        });

        tab5.findViewById(R.id.paizhao_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhoto();
            }
        });
        mViews.add(tab1);
        mViews.add(tab2);
        mViews.add(tab3);
        mViews.add(tab4);
        mViews.add(tab5);

        //适配器初始化并设置
        mPagerAdapter = new PagerAdapter() {
            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(mViews.get(position));
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                View view = mViews.get(position);
                container.addView(view);
                return view;
            }

            @Override
            public int getCount() {
                return mViews.size();
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
                return view == o;
            }
        };
        mViewPager.setAdapter(mPagerAdapter);
    }

    @Override
    public void onClick(View arg0) {
        TextView textView1 = (TextView) findViewById(R.id.titleTv);
        switch (arg0.getId()) {
            case R.id.tab_yundong:
                mViewPager.setCurrentItem(0);
                resetImg();
                mYundong.setImageResource(R.drawable.yundong_pressed);
                textView1.setText("运动");
                break;
            case R.id.tab_shequ:
                mViewPager.setCurrentItem(1);
                resetImg();
                mShequ.setImageResource(R.drawable.shequ_pressed);
                textView1.setText("社区");
                break;
            case R.id.tab_faxian:
                mViewPager.setCurrentItem(2);
                resetImg();
                mFaxian.setImageResource(R.drawable.faxian_pressed);
                textView1.setText("发现");
                break;
            case R.id.tab_xiaoxi:
                mViewPager.setCurrentItem(3);
                new GetConList().start();
                resetImg();
                mXiaoxi.setImageResource(R.drawable.xiaoxi_pressed);
                textView1.setText("消息");
                break;
            case R.id.tab_me:
                mViewPager.setCurrentItem(4);
                resetImg();
                mMe.setImageResource(R.drawable.me_pressed);
                ApplicationUtil applicationUtil = (ApplicationUtil) getApplication();
                User user = applicationUtil.getUser();
                username_text = (TextView) findViewById(R.id.username_text);
                if (user != null)
                    username_text.setText(user.getUserName());
                textView1.setText("我");
                break;
            default:
                break;
        }
    }

    //显示联系人列表
    public void showContactList(List<ContactItem> contactList) {
        ApplicationUtil applicationUtil = (ApplicationUtil)getApplication();
        applicationUtil.setList(contactList);
        ContactAdapter adapter = new ContactAdapter(this.getApplicationContext(), R.layout.contact_item, contactList);
        ListView listView = findViewById(R.id.xiaoxi_list);
        listView.setAdapter(adapter);
    }

//    private void initContactList(List<ContactItem> contactList) {
//
//        ContactItem m1 = new ContactItem(R.drawable.liweisi, "AZ", "AZ", new Date("2019/5/8 01:23:11"));
//        ContactItem m2 = new ContactItem(R.drawable.liweisi, "DS", "QWERT", new Date("2019/5/8 22:00:56"));
//        ContactItem m3 = new ContactItem(R.drawable.liweisi, "BA", "CVBNM", new Date("2019/5/8 12:14:05"));
//        ContactItem m4 = new ContactItem(R.drawable.liweisi, "QQS", "QAZWSX", new Date("2019/5/8 10:41:54"));
//        contactList.add(m1);
//        contactList.add(m2);
//        contactList.add(m3);
//        contactList.add(m4);
//
//    }

    class GetConList extends Thread{
        @Override
        public void run(){
            ApplicationUtil applicationUtil = (ApplicationUtil)getApplication();
            socket = applicationUtil.getSocket();
            try {
                OutputStream outputStream = socket.getOutputStream();
                String jsonStu = JSON.toJSONString(applicationUtil.getUser().getUserId());
                Request request = new Request(102,jsonStu);
                Gson gson = new Gson();
                String result = gson.toJson(request)+"\n";
                outputStream.write(result.getBytes("UTF-8"));
                outputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    class GetMsgThread extends Thread{
        private int sendId;
        private int receiveId;
        public GetMsgThread(int sendId,int receiveId)
        {
            this.sendId=sendId;
            this.receiveId=receiveId;
        }
        @Override
        public void run(){
            ApplicationUtil applicationUtil = (ApplicationUtil)getApplication();
            socket = applicationUtil.getSocket();
            try {
                OutputStream outputStream = socket.getOutputStream();
                MsgRequest msgRequest = new MsgRequest();
                msgRequest.setSendId(sendId);
                msgRequest.setReceiveId(receiveId);
                String jsonStu = JSON.toJSONString(msgRequest);
                Request request = new Request(101,jsonStu);
                Gson gson = new Gson();
                String result = gson.toJson(request)+"\n";
                System.out.println("click the button and the gson is "+result);
                outputStream.write(result.getBytes("UTF-8"));
                outputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    //把所有图片变暗
    private void resetImg() {
        mYundong.setImageResource(R.drawable.yundong_normal);
        mShequ.setImageResource(R.drawable.shequ_normal);
        mFaxian.setImageResource(R.drawable.faxian_normal);
        mXiaoxi.setImageResource(R.drawable.xiaoxi_normal);
        mMe.setImageResource(R.drawable.me_normal);
    }

}
