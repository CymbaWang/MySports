package com.example.mysports.adapter;

import android.os.AsyncTask;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mysports.R;
import com.github.chrisbanes.photoview.PhotoView;
import com.github.chrisbanes.photoview.PhotoViewAttacher;
import com.lcw.library.imagepicker.utils.ImageLoader;

import java.io.InputStream;
import java.util.List;

public class ImageBrowseAdapter extends PagerAdapter {

    private AppCompatActivity activity;
    private List<String> imagePath;

    public ImageBrowseAdapter(AppCompatActivity activity, List<String> urls) {
        this.activity = activity;
        this.imagePath = urls;
    }

    @Override
    public int getCount() {
        return imagePath.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == o;
    }

    //销毁对象
    @Override
    public void destroyItem(ViewGroup view, int position, Object object) {
        view.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {

        PhotoView photoView = new PhotoView(activity);

        photoView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        display(activity,photoView,imagePath.get(position));
        //把图片添加到view中
        view.addView(photoView);
        photoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        });
        return photoView;
    }

    public static void display(AppCompatActivity activity, ImageView imageView,String url){
        Glide
                .with(activity)
                .load(url)
                .into(imageView);
    }

}
