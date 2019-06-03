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

import com.example.mysports.R;

public class PullScrollView extends ScrollView {
    public PullScrollView(Context context, AttributeSet attrs){
        super(context,attrs);
    }

    public PullScrollView(Context context,AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setmHeaderView(View view){
        mHeaderView = view;
    }
    /*
     *   mHeaderView：表示头部的图片的VIEW
     *   mContentView:表示PullScrollView的子控件，这里是TableLayout控件对应的VIEW
     *
     *   另外是三个初始化的位置：
     *   mTouchPoint:表示用户在滑动手指的初始点击位置。用来计算手指的移动距离的
     *   mHeadInitRect:用来保存头部View初始化位置。用来找到回弹的位置用。
     *   mContentInitRect:保存ContentView的初始化位置。跟mHeadInitRect一样，也是回弹用。
    */
    //底部图片View
    int mHeaderCurTop;
    int mHeaderCurBottom;
    int mContentTop;
    int mContentBottom;
    boolean mIsMoving = true;
    private View mHeaderView;
    //头部图片的初始位置
    private Rect mHeadInitRect = new Rect();

    //底部View
    private View mContentView;
    //contentView的初始位置
    private Rect mContentInitRect = new Rect();

    //初始点击位置
    private Point mTouchPoint = new Point();

    //布局初始化
    @Override
    protected void onFinishInflate(){
        mContentView = getChildAt(0);
        super.onFinishInflate();
    }

    //原始根视图的位置
    private Rect mNormalRect;
    //让布局跟随手指移动
    private int mpreY=0;
    public boolean onTouchEvent(MotionEvent event){
        float curY= event.getY();
        switch ((event.getAction())){
            case MotionEvent.ACTION_DOWN:{
                mTouchPoint.set((int)event.getX(),(int)event.getY());
                setmHeaderView(findViewById(R.id.background_image1));
                mHeadInitRect.set(mHeaderView.getLeft(),mHeaderView.getTop(),mHeaderView.getRight(),mHeaderView.getBottom());
                mContentInitRect.set(mContentView.getLeft(),mContentView.getTop(),mContentView.getRight(),mContentView.getBottom());
                //保存视图的初始位置
                if( mContentView!=null){
                    mNormalRect = new Rect( mContentView.getLeft(), mContentView.getTop(), mContentView.getRight(), mContentView.getBottom());
                }
            }
            break;
            case MotionEvent.ACTION_MOVE:{
                int deltaY =(int)event.getY() - mTouchPoint.y;
                deltaY = deltaY > mHeaderView.getHeight() ? mHeaderView.getHeight() : deltaY;
                if (deltaY > 0 && deltaY >= getScrollY()) {
                    float headerMoveHeight = deltaY * 0.5f ;
                    mHeaderCurTop = (int) (mHeadInitRect.top + headerMoveHeight);
                    mHeaderCurBottom = (int) (mHeadInitRect.bottom + headerMoveHeight);

                    float contentMoveHeight = deltaY ;
                    mContentTop = (int) (mContentInitRect.top + contentMoveHeight);
                    mContentBottom = (int) (mContentInitRect.bottom + contentMoveHeight);

                    if (mContentTop <= mHeaderCurBottom) {
                        mHeaderView.layout(mHeadInitRect.left, mHeaderCurTop, mHeadInitRect.right, mHeaderCurBottom);
                        mContentView.layout(mContentInitRect.left, mContentTop, mContentInitRect.right, mContentBottom);
                    }
                }
            }
            break;

            case MotionEvent.ACTION_UP: {
                //反弹
                if (mIsMoving) {
                    mHeaderView.layout(mHeadInitRect.left, mHeadInitRect.top, mHeadInitRect.right, mHeadInitRect.bottom);
                    TranslateAnimation headAnim = new TranslateAnimation(0, 0, mHeaderCurTop - mHeadInitRect.top, 0);
                    headAnim.setDuration(200);
                    mHeaderView.startAnimation(headAnim);

                    mContentView.layout(mContentInitRect.left, mContentInitRect.top, mContentInitRect.right, mContentInitRect.bottom);
                    TranslateAnimation contentAnim = new TranslateAnimation(0, 0, mContentTop - mContentInitRect.top, 0);
                    contentAnim.setDuration(200);
                    mContentView.startAnimation(contentAnim);
                    mIsMoving = false;
                }
            }
            break;

        }
        mpreY = (int)curY;
        return super.onTouchEvent(event);
    }
}
