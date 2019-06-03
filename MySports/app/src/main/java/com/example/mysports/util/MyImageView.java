package com.example.mysports.util;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;


public class MyImageView extends AppCompatImageView {

    public MyImageView(Context context){
        super(context);
    }

    public MyImageView(Context context, AttributeSet attrs){
        super(context,attrs);
    }

    public MyImageView(Context context, AttributeSet attrs,int defStyle){
        super(context,attrs,defStyle);
    }

    public void onMeasure(int widthMeasureSpec,int heightMeasureSpec){
        super.onMeasure(widthMeasureSpec,heightMeasureSpec);

        if(widthMeasureSpec<heightMeasureSpec){
            setMeasuredDimension(getMeasuredHeight(),getMeasuredHeight());
        }
        else
        {
            setMeasuredDimension(getMeasuredWidth(),getMeasuredWidth());
        }
    }
}
