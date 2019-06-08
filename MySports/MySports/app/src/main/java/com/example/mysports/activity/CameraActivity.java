package com.example.mysports.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.media.Image;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mysports.R;
import com.lcw.library.imagepicker.ImagePicker;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.hardware.Camera.getCameraInfo;
import static android.hardware.Camera.getNumberOfCameras;

public class CameraActivity extends AppCompatActivity {

    private Camera mCamera; //定义相机对象
    private boolean mPreviewRunning = false;    //定义非预览状态

    private static final int PHOTO_REQUEST_CUT = 2;
    //临时图片路径
    private File tempFile = new File(Environment.getExternalStorageDirectory(), getPhotoFileName());

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);       //设置全屏显示
        if(!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            Toast.makeText(this,"请安装SD卡！",Toast.LENGTH_SHORT).show();   //提示安装SD卡
        }
        SurfaceView surfaceView = (SurfaceView)findViewById(R.id.surfaceView);
        final SurfaceHolder surfaceHolder = surfaceView.getHolder();
       // surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        ImageView camera_button = (ImageView) findViewById(R.id.camera);
        ImageView camera_turn_button = (ImageView)findViewById(R.id.camera_turn);

        camera_turn_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Preview(surfaceHolder);
            }
        });

        final Camera.PictureCallback jpeg = new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] data, Camera camera) {
                final Bitmap bm = BitmapFactory.decodeByteArray(data,0,data.length);
                camera.stopPreview();   //停止预览
                mPreviewRunning = false;
                Uri uri = Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(),bm,null,null));
                startPhotoZoom(uri,15);
            }
        };

        camera_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCamera.takePicture(null,null,jpeg);
            }
        });
    }
    //获得照片名字
    private String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
        return "IMG_" + dateFormat.format(date);
    }

    public void Preview(SurfaceHolder holder){
        if(!mPreviewRunning){
            mCamera = Camera.open();
            mPreviewRunning = true;
        }
        try{
            mCamera.setPreviewDisplay(holder);
            Camera.Parameters parameters = mCamera.getParameters();     //获取相机参数
            parameters.setPictureFormat(PixelFormat.JPEG);
            parameters.set("jpeg-quality",100);
            mCamera.setParameters(parameters);
            mCamera.startPreview(); //开始预览
            mCamera.autoFocus(null);    //设置自动对焦
        }catch (IOException e){
            e.printStackTrace();
        }
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
                case PHOTO_REQUEST_CUT:
                    //剪裁并设置头像
                    if (data != null)
                        setPicToView(data);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void onPause(){
        if(mCamera !=null){
            mCamera.setPreviewCallback(null) ;
            mCamera.setPreviewCallbackWithBuffer(null);
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
        super.onPause();
    }
}
