package com.example.mysports.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.mysports.R;
import com.example.mysports.adapter.PhotoAdapter;
import com.example.mysports.pojo.Photo;
import com.example.mysports.util.MyImageView;

import java.io.File;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class PhotoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        ImageView fanhui_button=(ImageView) findViewById(R.id.fanhui_button);
        fanhui_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        showImg();
    }

    //用于显示相册
    public void showImg() {
        final ArrayList<String> image = new ArrayList<String>();   //用于存放图片
        //显示我的照片
        if (getImagePathFromSD() != null) {
            for (int i = 0; i < getImagePathFromSD().size(); i++) {
                image.add(getImagePathFromSD().get(i));
            }
            GridView gridView = (GridView) findViewById(R.id.gridview);
            BaseAdapter adapter = new BaseAdapter() {
                @Override
                public int getCount() {
                    return image.size();
                }

                @Override
                public Object getItem(int position) {
                    return position;
                }

                @Override
                public long getItemId(int position) {
                    return position;
                }

                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    MyImageView imageView;
                    if (convertView == null) {
                        imageView = new MyImageView(PhotoActivity.this);
                        /**************设置图片的宽度和高度******************/
                        imageView.setAdjustViewBounds(true);
                        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        /**************************************************/
                        imageView.setPadding(1, 1, 1, 1);
                    } else {
                        imageView = (MyImageView) convertView;
                    }
                    Uri uri = Uri.fromFile(new File(image.get(position)));
                    imageView.setImageURI(uri);

                    return imageView;
                }
            };
            gridView.setAdapter(adapter);
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(getApplicationContext(),ShowPhotoActivity.class);
                    intent.putExtra("position",position);
                    intent.putStringArrayListExtra("imagePath",image);
                    startActivity(intent);
                }
            });
        }
    }
    /**
     * 从sd卡获取图片资源（私人相册）
     * @return
     */
    private List<String> getImagePathFromSD() {
        // 图片列表
        List<String> imagePathList = new ArrayList<String>();
        // 得到sd卡内image文件夹的路径   File.separator(/)
        String filePath = Environment.getExternalStorageDirectory().toString() + File.separator
                + "image";
        // 得到该路径文件夹下所有的文件
        File fileAll = new File(filePath);
        File[] files = null;
        if(fileAll.exists())
            files = fileAll.listFiles();
        else
            fileAll.mkdirs();
        // 将所有的文件存入ArrayList中
        if(fileAll.listFiles().length>0){
            for (int i = 0; i < files.length; i++) {
                File file = files[i];
                if (checkIsImageFile(file.getPath())) {
                    imagePathList.add(file.getPath());
                }
            }
            // 返回得到的图片列表
            return imagePathList;
        }
        else
            return null;
    }

    /**
     * 检查扩展名，得到图片格式的文件
     * @param fName  文件名
     * @return
     */
    private boolean checkIsImageFile(String fName) {
        boolean isImageFile = false;
        // 获取扩展名
        String FileEnd = fName.substring(fName.lastIndexOf(".") + 1,
                fName.length()).toLowerCase();
        if (FileEnd.equals("jpg") || FileEnd.equals("png") || FileEnd.equals("gif")
                || FileEnd.equals("jpeg")|| FileEnd.equals("bmp") ) {
            isImageFile = true;
        } else {
            isImageFile = false;
        }
        return isImageFile;
    }
}
