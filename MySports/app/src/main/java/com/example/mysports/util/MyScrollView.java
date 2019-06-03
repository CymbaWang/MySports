package com.example.mysports.util;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.AbsListView;
import android.widget.ScrollView;
import android.widget.Scroller;

import java.util.jar.Attributes;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class MyScrollView extends ScrollView {
   private AbsListView.OnScrollListener onScrollListener;
    //主要是用在用户手指离开MyScrollView，MyScrollView还在继续滑动，我们用来保存Y的距离，然后作比较
    private int lateScrollY;

    public MyScrollView(Context context){
        super(context,null);
    }

    public MyScrollView(Context context, AttributeSet attrs){
        super(context,attrs,0);
    }

    public MyScrollView(Context context,AttributeSet attrs,int defStyle){
        super(context,attrs,defStyle);
    }

    //设置滚动接口
    public void setOnScrollListener(AbsListView.OnScrollListener onScrollListener){
        this.onScrollListener=onScrollListener;
    }

    //用于用户手指离开MyScrollView的时候获取MyScrollView滚动的Y距离，然后毁掉给onScroll方法中
   /*  private Handler handler = new Handler() {

        public void handleMessage(android.os.Message msg){
            int scrollY=MyScrollView.this.getScrollY();

            //此时的距离和记录下距离不相等，在隔5毫秒给handler发送消息
            if(lateScrollY !=scrollY){
                lateScrollY = scrollY;
                handler.sendMessageDelayed(handler.obtainMessage(),5);
            }
            if(onScrollListener!=null)
            {
                onScrollListener.onScroll(scrollY);
            }
        }
        @Override
        public void publish(LogRecord record) {

        }

        @Override
        public void flush() {

        }

        @Override
        public void close() throws SecurityException {

        }
    };
    public boolean onTouchEvent(MotionEvent event){
        if(onScrollListener !=null){
            onScrollListener.onScroll(lateScrollY=this.getScrollY());
        }
        switch (event.getAction()){
            case MotionEvent.ACTION_UP:
                handler.sendMessageDelayed(handler.obtainMessage(),5);
                break;
        }
        return super.onTouchEvent(event);
    }

    public interface OnScrollListener{
        public void onScroll(int scrollY);
    }*/
}
