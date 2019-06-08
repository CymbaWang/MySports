package com.example.mysports.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.example.mysports.R;
import com.example.mysports.adapter.GridViewAddImageAdapter;
import com.example.mysports.util.GlideLoader;
import com.example.mysports.util.MyDialog;
import com.lcw.library.imagepicker.ImagePicker;

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

public class PublishedActivity extends Activity {

    private Button fabu_button; //发布按钮
    private GridView noScrollgridview;
    private GridViewAddImageAdapter gridViewAddImageAdapter;

    private ArrayList<String> mImagePaths;  //用于存放选择图片的路径
    private List<Map<String, Object>> datas =new ArrayList<Map<String,Object>>();   //用于存放选择的所有图片

//临时图片路径
    private File tempFile = new File(Environment.getExternalStorageDirectory(), getPhotoFileName());

    private static final int TAKE_PHOTO = 1;
    private static final int CHOOSE_PHOTO = 2;
    private static final int PHOTO_REQUEST_CUT = 3;

    private static final String TAG = PublishedActivity.class.getSimpleName();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_published);

        ImageView fanhui_button=(ImageView) findViewById(R.id.fanhui_button);
        fanhui_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        noScrollgridview = (GridView)findViewById(R.id.noScrollgridview);

        gridViewAddImageAdapter = new GridViewAddImageAdapter(datas, getApplicationContext());
        noScrollgridview.setAdapter(gridViewAddImageAdapter);
        noScrollgridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                showDialog();
            }
        });

        fabu_button = (Button)findViewById(R.id.fabu_button);
        fabu_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File filest = new File(Environment.getExternalStorageDirectory() + "/path");
                if (!filest.exists()) {
                    boolean resyltBool = filest.mkdirs();
                }
                for(int i=0;i<datas.size();i++)
                {
                    File file = new File(Environment.getExternalStorageDirectory() + "/path/" + getPhotoFileName() +i+ ".jpg");
                    try {
                        Uri uri = Uri.fromFile(new File(datas.get(i).get("path").toString()));
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), uri);
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
            }
        });
    }

    public void showDialog() {
        View localView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_popupwindows,null);
        final MyDialog dialog = new MyDialog(PublishedActivity.this,R.style.Dialog);
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
                takePhoto();
            }
        });

        dialog.getWindow().findViewById(R.id.tv_gallery).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                // 从系统相册选取照片
                if(datas.size()<9) {
                    ImagePicker.getInstance()
                            .setTitle("发动态")//设置标题
                            .showCamera(true)//设置是否显示拍照按钮
                            .showImage(true)//设置是否展示图片
                            .showVideo(true)//设置是否展示视频
                            .setMaxCount(9-datas.size())//设置最大选择图片数目(默认为1，单选)
                            .setImageLoader(new GlideLoader())//设置自定义图片加载器
                            .start(PublishedActivity.this, CHOOSE_PHOTO);//REQEST_SELECT_IMAGES_CODE为Intent调用的requestCode
                }
                dialog.dismiss();
            }
        });
    }
    //拍照
    private void takePhoto() {
        try{
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
            PackageManager packageManager = getApplicationContext().getPackageManager();
            if (packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
                startActivityForResult(intent, TAKE_PHOTO);
            }
        }
        catch(ActivityNotFoundException e){
            Log.d("FirstActivity.this","找不到该活动");
        }
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
    //保存拍照的图片
    public void SaveImage(Bitmap bitmap) {
        /*将bitmap保存下来*/
        /* 将Bitmap设定到ImageView */
        File filest = new File(Environment.getExternalStorageDirectory() + "/image");
        if (!filest.exists()) {
            boolean resyltBool = filest.mkdirs();
        }
        File file = new File(Environment.getExternalStorageDirectory() + "/image/" + getPhotoFileName() + ".jpg");
        Log.d(TAG, "SaveImage: file:" + file.getName());
        Log.d(TAG, "SaveImage: file:" + file.getAbsolutePath());
        Map<String, Object> map = new HashMap<>();
        map.put("path", file.getAbsolutePath());
        datas.add(map);
        gridViewAddImageAdapter.notifyDataSetChanged(datas);
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
                    mImagePaths = data.getStringArrayListExtra(ImagePicker.EXTRA_SELECT_IMAGES);
                    for (int i = 0; i < mImagePaths.size(); i++) {
                        Map<String, Object> map = new HashMap<>();
                        map.put("path", mImagePaths.get(i));
                        datas.add(map);
                        gridViewAddImageAdapter.notifyDataSetChanged(datas);
                    }
                    //相册
                    break;
                case PHOTO_REQUEST_CUT:
                    //剪裁并设置头像
                    if (data != null)
                        setPicToView(data);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
