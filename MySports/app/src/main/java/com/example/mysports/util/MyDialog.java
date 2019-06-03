package com.example.mysports.util;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.example.mysports.R;

public class MyDialog extends Dialog {
        public MyDialog(Context context) {
            super(context);
        }

        public MyDialog(Context context, int themeResId) {
            super(context, themeResId);
        }

        protected MyDialog(Context context, boolean cancelable
                , OnCancelListener cancelListener) {
            super(context, cancelable, cancelListener);
        }
        protected void onCreate(Bundle saveInstanceState){
            super.onCreate(saveInstanceState);
            View view = View.inflate(getContext(), R.layout.item_popupwindows,null);
            setContentView(view);

            setCanceledOnTouchOutside(true);

            Window win = getWindow();
            WindowManager.LayoutParams lp = win.getAttributes();
            win.setGravity(Gravity.BOTTOM);
            win.setAttributes(lp);
        }
}

