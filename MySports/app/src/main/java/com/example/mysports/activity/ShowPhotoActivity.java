package com.example.mysports.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.example.mysports.R;
import com.example.mysports.adapter.ImageBrowseAdapter;

import java.util.List;

public class ShowPhotoActivity extends AppCompatActivity {

    // ViewPager对象
    private ViewPager mViewPager;
    // 原图url路径List
    private List<String> imagePath;
    // 当前显示的位置
    private int position;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_photo);
        // 获取参数
        this.position = getIntent().getIntExtra("position",0);
        this.imagePath = getIntent().getStringArrayListExtra("imagePath");
        mViewPager = (ViewPager) findViewById(R.id.images_view);
        // 设置左右两列缓存的数目
        mViewPager.setOffscreenPageLimit(2);
        // 添加Adapter
        PagerAdapter adapter = new ImageBrowseAdapter(this, imagePath);
        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(position);

        final TextView photo_number =(TextView)findViewById(R.id.photo_number);
        photo_number.setText(position+1+"/"+imagePath.size());
        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int i) {
                super.onPageSelected(i);
                photo_number.setText(i+1+"/"+imagePath.size());
            }
        });
    }
}
