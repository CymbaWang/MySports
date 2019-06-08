package com.example.mysports.activity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mysports.R;
import com.example.mysports.adapter.PhotoAdapter;
import com.example.mysports.adapter.TrendAdapter;
import com.example.mysports.model.User;
import com.example.mysports.pojo.Photo;
import com.example.mysports.pojo.Trend;
import com.example.mysports.util.ApplicationUtil;
import com.example.mysports.util.CircleImageView;
import com.example.mysports.util.MyImageView;
import com.example.mysports.util.MyListView;

import java.io.File;
import java.sql.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class MeActivity extends AppCompatActivity implements android.view.View.OnClickListener{
    private TextView name_text, entertime_text;
    private ViewPager mViewPager;    //用来放置界面切换
    private PagerAdapter mPagerAdapter;     //初始化View适配器
    private List<View> mViews = new ArrayList<View>();
    //5个Tab，每个Tab包含一个按钮
    private LinearLayout mTabZhuye;
    private LinearLayout mTabDongtai;
    private LinearLayout mTabXiangce;
    private LinearLayout mTabShipin;
    private LinearLayout mTabWenzhang;
    //5个按钮
    private Button mZhuye;
    private Button mDongtai;
    private Button mXiangce;
    private Button mShipin;
    private Button mWenzhang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me);
        ImageButton fanhui_button=(ImageButton)findViewById(R.id.fanhui_button);
        fanhui_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ImageView imageView=(ImageView)findViewById(R.id.paizhao_image);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MeActivity.this, PublishedActivity.class);
                startActivity(intent);
            }
        });
        ApplicationUtil applicationUtil = (ApplicationUtil) getApplication();
        User user = applicationUtil.getUser();
        name_text = (TextView)findViewById(R.id.name_text);
        entertime_text = (TextView)findViewById(R.id.enter_time_text);
        name_text.setText(user.getUserName());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        entertime_text.setText(sdf.format(user.getUserRegtime())+"加入潮流运动");
        initView();
        initViewPage();
        initEvent();
    }
    private void initEvent(){
        mTabZhuye.setOnClickListener(this);
        mTabDongtai.setOnClickListener(this);
        mTabXiangce.setOnClickListener(this);
        mTabShipin.setOnClickListener(this);
        mTabWenzhang.setOnClickListener(this);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                int currentItem=mViewPager.getCurrentItem();
                switch (currentItem){
                    case 0:
                        resetImg();
                        mZhuye.setTextColor(Color.RED);
                        break;
                    case 1:
                        showTrend();
                        resetImg();
                        mDongtai.setTextColor(Color.RED);
                        break;
                    case 2:
                        showImg();
                        resetImg();
                        mXiangce.setTextColor(Color.RED);
                        break;
                    case 3:
                        resetImg();
                        mShipin.setTextColor(Color.RED);
                        break;
                    case 4:
                        resetImg();
                        mWenzhang.setTextColor(Color.RED);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    //初始化设置
    public void initView(){
        mViewPager = (ViewPager)findViewById(R.id.viewpage);
        //初始化5个LinearLayout
        mTabZhuye=(LinearLayout)findViewById(R.id.tab_zhuye);
        mTabDongtai=(LinearLayout)findViewById(R.id.tab_dongtai);
        mTabXiangce=(LinearLayout)findViewById(R.id.tab_xiangce);
        mTabShipin=(LinearLayout)findViewById(R.id.tab_shipin);
        mTabWenzhang=(LinearLayout)findViewById(R.id.tab_wenzhang);
        //初始化5个按钮
        mZhuye=(Button)findViewById(R.id.tab_zhuye_button);
        mDongtai=(Button)findViewById(R.id.tab_dongtai_button);
        mXiangce=(Button)findViewById(R.id.tab_xiangce_button);
        mShipin=(Button)findViewById(R.id.tab_shipin_button);
        mWenzhang=(Button)findViewById(R.id.tab_wenzhang_button);

        mZhuye.setTextColor(Color.RED);
    }

    //初始化ViewPage
    private void initViewPage(){

        //初始化5个布局
        LayoutInflater mLayoutInflater=LayoutInflater.from(this);
        View tab1=mLayoutInflater.inflate(R.layout.activity_zhuye,null);
        View tab2=mLayoutInflater.inflate(R.layout.activity_dongtai,null);
        View tab3=mLayoutInflater.inflate(R.layout.activity_xiangce,null);
        View tab4=mLayoutInflater.inflate(R.layout.activity_shipin,null);
        View tab5=mLayoutInflater.inflate(R.layout.activity_wenzhang,null);

        mViews.add(tab1);
        mViews.add(tab2);
        mViews.add(tab3);
        mViews.add(tab4);
        mViews.add(tab5);

        //适配器初始化并设置
        mPagerAdapter=new PagerAdapter() {
            @Override
            public void destroyItem(ViewGroup container, int position, Object object){
                container.removeView(mViews.get(position));
            }
            @Override
            public Object instantiateItem(ViewGroup container, int position){
                View view=mViews.get(position);
                container.addView(view);
                return view;
            }
            @Override
            public int getCount() {
                return mViews.size();
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
                return view==o;
            }
        };
        mViewPager.setAdapter(mPagerAdapter);
    }

    @Override
    public void onClick(View arg0){
        switch (arg0.getId()){
            case R.id.tab_zhuye:
                mViewPager.setCurrentItem(0);
                resetImg();
                mZhuye.setTextColor(Color.RED);
                break;
            case R.id.tab_dongtai:
                mViewPager.setCurrentItem(1);
                showTrend();
                resetImg();
                mDongtai.setTextColor(Color.RED);
                break;
            case R.id.tab_xiangce:
                mViewPager.setCurrentItem(2);
                showImg();
                resetImg();
                mXiangce.setTextColor(Color.RED);
                break;
            case R.id.tab_shipin:
                mViewPager.setCurrentItem(3);
                resetImg();
                mShipin.setTextColor(Color.RED);
                break;
            case R.id.tab_wenzhang:
                mViewPager.setCurrentItem(4);
                resetImg();
                mWenzhang.setTextColor(Color.RED);
                break;
            default:
                break;
        }
    }

    //用于显示动态
    public void showTrend(){
        MyListView trend_listView = (MyListView)findViewById(R.id.trend_listView);
        final List<Trend> trendList = new ArrayList<Trend>();
        final ArrayList<String> image = new ArrayList<String>();   //用于存放图片
        if(getImagePathFromSD2()!=null){
            if(getImagePathFromSD2().size()<=9){
                for(int i=0;i<getImagePathFromSD2().size();i++){
                    image.add(getImagePathFromSD2().get(i));
                }
            }
            else{
                for(int i=0;i<9;i++){
                    image.add(getImagePathFromSD2().get(i));
                }
            }
            trendList.add(new Trend(image.get(0),"mafiaboy","2019-05-04","我的世界黑暗之中的光芒，冰荒废土的战士们，起来吧！我的世界黑暗之中的光芒，冰荒废土的战士们，起来吧！",image,"10","200","10"));
            trendList.add(new Trend(image.get(0),"黑手党男孩","2019-05-04","我的世界黑暗之中的光芒，冰荒废土的战士们，起来吧！",image,"20","500","100"));
            TrendAdapter trendAdapter = new TrendAdapter(getApplicationContext(),trendList);
            trend_listView.setAdapter(trendAdapter);
        }
    }
    //用于显示相册
    public void showImg(){
        final ArrayList<String> image = new ArrayList<String>();   //用于存放图片
        List<Photo> photoList = new ArrayList<Photo>();
        //显示我的图片
        if(getImagePathFromSD2()!=null){
            GridView gridView1 = (GridView)findViewById(R.id.photo_gridview);
            photoList.add(new Photo(getImagePathFromSD2().get(0),"我的照片"));
            PhotoAdapter photoAdapter = new PhotoAdapter(getApplicationContext(),photoList);
            gridView1.setAdapter(photoAdapter);
            gridView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if(position ==0){
                        Intent intent = new Intent(getApplicationContext(),PhotoActivity.class);
                        startActivity(intent);
                    }
                }
            });
        }
        //显示发布的图片
        if(getImagePathFromSD()!=null)
        {
            for(int i=0;i<getImagePathFromSD().size();i++)
            {
                image.add(getImagePathFromSD().get(i));
            }
            GridView gridView = (GridView)findViewById(R.id.gridview);
            BaseAdapter adapter = new BaseAdapter() {
                @Override
                public int getCount() {
                    return image.size();
                }

                @Override
                public Object getItem(int position) {
                    return position;
                }

                @Override
                public long getItemId(int position) {
                    return position;
                }

                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    MyImageView imageView;
                    if(convertView == null){
                        imageView = new MyImageView(MeActivity.this);
                        /**************设置图片的宽度和高度******************/
                        imageView.setAdjustViewBounds(true);
                        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        /**************************************************/
                        imageView.setPadding(1, 1, 1, 1);
                    }
                    else {
                        imageView = (MyImageView)convertView;
                    }
                    Uri uri = Uri.fromFile(new File(image.get(position)));
                    imageView.setImageURI(uri);

                    return imageView;
                }
            };
            gridView.setAdapter(adapter);
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(getApplicationContext(),ShowPhotoActivity.class);
                    intent.putExtra("position",position);
                    intent.putStringArrayListExtra("imagePath",image);
                    startActivity(intent);
                }
            });
        }
    }
    //把所有按钮字的颜色变黑
    private void resetImg(){
        mZhuye.setTextColor(Color.BLACK);
        mDongtai.setTextColor(Color.BLACK);
        mXiangce.setTextColor(Color.BLACK);
        mShipin.setTextColor(Color.BLACK);
        mWenzhang.setTextColor(Color.BLACK);
    }

    /**
     * 从sd卡获取图片资源
     * @return
     */
    private List<String> getImagePathFromSD() {
        // 图片列表
        List<String> imagePathList = new ArrayList<String>();
        // 得到sd卡内image文件夹的路径   File.separator(/)
        String filePath = Environment.getExternalStorageDirectory().toString() + File.separator
                + "path";
        // 得到该路径文件夹下所有的文件
        File fileAll = new File(filePath);
        File[] files = null;
        if(fileAll.exists())
            files = fileAll.listFiles();
        else
            fileAll.mkdirs();
        // 将所有的文件存入ArrayList中
        if(fileAll.listFiles().length>0){
            for (int i = 0; i < files.length; i++) {
                File file = files[i];
                if (checkIsImageFile(file.getPath())) {
                    imagePathList.add(file.getPath());
                }
            }
            // 返回得到的图片列表
            return imagePathList;
        }
        else
            return null;
    }

    /**
     * 从sd卡获取图片资源（私人相册）
     * @return
     */
    private List<String> getImagePathFromSD2() {
        // 图片列表
        List<String> imagePathList = new ArrayList<String>();
        // 得到sd卡内image文件夹的路径   File.separator(/)
        String filePath = Environment.getExternalStorageDirectory().toString() + File.separator
                + "image";
        // 得到该路径文件夹下所有的文件
        File fileAll = new File(filePath);
        File[] files = null;
        if(fileAll.exists())
            files = fileAll.listFiles();
        else
            fileAll.mkdirs();
        // 将所有的文件存入ArrayList中
        if(fileAll.listFiles().length>0){
            for (int i = 0; i < files.length; i++) {
                File file = files[i];
                if (checkIsImageFile(file.getPath())) {
                    imagePathList.add(file.getPath());
                }
            }
            // 返回得到的图片列表
            return imagePathList;
        }
        else
            return null;
    }

    /**
     * 检查扩展名，得到图片格式的文件
     * @param fName  文件名
     * @return
     */
    private boolean checkIsImageFile(String fName) {
        boolean isImageFile = false;
        // 获取扩展名
        String FileEnd = fName.substring(fName.lastIndexOf(".") + 1,
                fName.length()).toLowerCase();
        if (FileEnd.equals("jpg") || FileEnd.equals("png") || FileEnd.equals("gif")
                || FileEnd.equals("jpeg")|| FileEnd.equals("bmp") ) {
            isImageFile = true;
        } else {
            isImageFile = false;
        }
        return isImageFile;
    }
}

