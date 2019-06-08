package com.example.mysports.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.ArrayList;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.mysports.R;
import com.example.mysports.util.CircleImageView;
import com.example.mysports.util.GlideLoader;
import com.example.mysports.util.MyDialog;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lcw.library.imagepicker.ImagePicker;

public class ZiliaoActivity extends AppCompatActivity {

    private static final int REQUEST_SELECT_IMAGES_CODE = 0x04;
    private ImageView touxiang_image;
    private ArrayList<String> mImagePaths;
    private List<Map<String, Object>> datas =new ArrayList<Map<String,Object>>();
    //临时图片路径
    private File tempFile = new File(Environment.getExternalStorageDirectory(), getPhotoFileName());

    private static final int TAKE_PHOTO = 1;
    private static final int CHOOSE_PHOTO = 2;
    private static final int PHOTO_REQUEST_CUT = 3;

    private static final String TAG = PublishedActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ziliao);
        CircleImageView touxiang_image = (CircleImageView)findViewById(R.id.touxiang_image);
        Button touxiang_button = (Button)findViewById(R.id.touxiang_button);
        Button beijing_button = (Button)findViewById(R.id.beijing_button);
        touxiang_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

        ImageButton fanhui_button=(ImageButton)findViewById(R.id.fanhui_button);
        fanhui_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void showDialog() {
        View localView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_popupwindows,null);
        final MyDialog dialog = new MyDialog(ZiliaoActivity.this,R.style.Dialog);
        dialog.show();

        //取消
        dialog.getWindow().findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                dialog.dismiss();
            }
        });

        dialog.getWindow().findViewById(R.id.tv_camera).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
                // 拍照
                //takePhoto();
            }
        });

        dialog.getWindow().findViewById(R.id.tv_gallery).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                // 从系统相册选取照片
//                Intent intent = new Intent(Intent.ACTION_PICK, null);
//                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
//                startActivityForResult(intent, CHOOSE_PHOTO);
                ImagePicker.getInstance()
                        .setTitle("我的资料")//设置标题
                        .showCamera(true)//设置是否显示拍照按钮
                        .showImage(true)//设置是否展示图片
                        .showVideo(true)//设置是否展示视频
                        .setMaxCount(1)//设置最大选择图片数目(默认为1，单选)
                        .setImageLoader(new GlideLoader())//设置自定义图片加载器
                        .start(ZiliaoActivity.this, REQUEST_SELECT_IMAGES_CODE);//REQEST_SELECT_IMAGES_CODE为Intent调用的requestCode
                dialog.dismiss();
            }
        });
    }

    //拍照
    private void takePhoto() {
        //Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE,null);
//        Intent intent = new Intent();
//        intent.setAction("android.media.action.IMAGE_CAPTURE");
//        intent.addCategory("android.intent.category.DEFAULT");

        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
        startActivityForResult(intent, TAKE_PHOTO);
    }
    //获得照片名字
    private String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
        return "IMG_" + dateFormat.format(date);
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
    //保存图片
    public void SaveImage(Bitmap bitmap) {
        /*将bitmap保存下来*/
        /* 将Bitmap设定到ImageView */
        File filest = new File(Environment.getExternalStorageDirectory() + "/path");
        if (!filest.exists()) {
            boolean resyltBool = filest.mkdirs();
        }
        File file = new File(Environment.getExternalStorageDirectory() + "/path/" + getPhotoFileName() + ".jpg");
        Log.d(TAG, "SaveImage: file:" + file.getName());
        Log.d(TAG, "SaveImage: file:" + file.getAbsolutePath());
        Map<String, Object> map = new HashMap<>();
        map.put("path", file.getAbsolutePath());
        datas.add(map);
        //gridViewAddImageAdapter.notifyDataSetChanged(datas);
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
                case TAKE_PHOTO:
                    //照相
                    startPhotoZoom(Uri.fromFile(tempFile), 15);
                    break;
                case CHOOSE_PHOTO:
                    if (data != null)
                        startPhotoZoom(data.getData(), 150);
                    //相册
                    break;
                case PHOTO_REQUEST_CUT:
                    //剪裁并设置头像
                    if (data != null)
                        setPicToView(data);
                    break;
                case REQUEST_SELECT_IMAGES_CODE:
                   touxiang_image = (ImageView)findViewById(R.id.touxiang_image);
                    mImagePaths = data.getStringArrayListExtra(ImagePicker.EXTRA_SELECT_IMAGES);
                    Uri uri = Uri.fromFile(new File(mImagePaths.get(0)));
                    touxiang_image.setImageURI(uri);
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
