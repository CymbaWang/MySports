package com.example.mysports.util;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.ScrollView;

public class PullScrollView1 extends ScrollView {
    public PullScrollView1(Context context, AttributeSet attrs){
        super(context,attrs);
    }

    public PullScrollView1(Context context,AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    //布局初始化
    @Override
    protected void onFinishInflate(){
        mNormalView = getChildAt(0);
        super.onFinishInflate();
    }

    private View mNormalView;
    //原始根视图的位置
    private Rect mNormalRect;
    //让布局跟随手指移动
    private int mpreY=0;
    public boolean onTouchEvent(MotionEvent event){
        float curY= event.getY();
        switch ((event.getAction())){
            case MotionEvent.ACTION_DOWN:{
                //保存视图的初始位置
                if( mNormalView!=null){
                    mNormalRect = new Rect( mNormalView.getLeft(), mNormalView.getTop(), mNormalView.getRight(), mNormalView.getBottom());
                }
            }
            break;
            case MotionEvent.ACTION_MOVE:{
                int delta = (int)((curY - mpreY)*0.25);
                if (delta>0) {
                    mNormalView.layout(mNormalView.getLeft(), mNormalView.getTop()+delta, mNormalView.getRight(), mNormalView.getBottom()+delta);
                }
                else{
                    mNormalView.layout(mNormalView.getLeft(), mNormalView.getTop()+delta, mNormalView.getRight(), mNormalView.getBottom()+delta);
                }
            }
            break;

            case MotionEvent.ACTION_UP: {
                //反弹
                int curTop = mNormalView.getTop();
                mNormalView.layout( mNormalRect.left, mNormalRect.top, mNormalRect.right, mNormalRect.bottom);
                    TranslateAnimation animation = new TranslateAnimation(0, 0, curTop - mNormalRect.top, 0);
                    animation.setDuration(200);
                    mNormalView.startAnimation(animation);
            }
            break;
        }
        mpreY = (int)curY;
        return super.onTouchEvent(event);
    }
}
