package com.example.mysports.util;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatImageButton;
import android.util.AttributeSet;
import android.widget.TextView;

import com.example.mysports.R;

public class MyImageButton extends AppCompatImageButton {
    public MyImageButton(Context context){
        super(context);
    }

    public MyImageButton(Context context, AttributeSet attrs){
        super(context, attrs);

        TextView textView1 = (TextView)findViewById(R.id.nicheng_text) ;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyImageButton);
        for(int i=0;i<typedArray.getIndexCount();i++) {
            int attr = typedArray.getIndex(i); //属性的id
            switch (attr) {
                case R.styleable.MyImageButton_userId2:
                    textView1.setText(typedArray.getString(attr));
                    break;
            }
        }
    }

    public MyImageButton(Context context, AttributeSet attrs, int defStyleAttr){
        super(context, attrs, defStyleAttr);
    }
}
