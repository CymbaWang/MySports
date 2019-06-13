package com.example.mysports.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mysports.activity.ChatActivity;
import com.example.mysports.model.User;
import com.example.mysports.pojo.ContactItem;
import com.example.mysports.pojo.Fensi;
import com.example.mysports.R;
import com.example.mysports.pojo.MessageContent;
import com.example.mysports.util.ApplicationUtil;
import com.example.mysports.util.BASE64Decoder;
import com.example.mysports.util.BASE64Encoder;
import com.example.mysports.util.MyImageButton;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;




public class ChatAdapter extends BaseAdapter {

    private Context context;
    private List<MessageContent> itemList;
    private User user;
    private String outOfFile = "/storage/emulated/0/path";

    public ChatAdapter(Context context, List<MessageContent> itemList, User user) {
        this.context = context;
        this.itemList = itemList;
        this.user = user;
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public Object getItem(int position) {
        return itemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        MessageContent message = itemList.get(position);
        ViewHolder holder = null;
        if (message.getType()==0) {
            if (message.getSenderId() == user.getUserId()) {
                convertView = inflater.inflate(R.layout.chat_dialog_right_item, parent, false);
                holder = new ViewHolder();
                holder.touxiangImage = convertView.findViewById(R.id.iv_chat_imagr_right);
                holder.content = convertView.findViewById(R.id.tv_chat_me_message);
                holder.nickName = convertView.findViewById(R.id.rightNickName);
                holder.date = convertView.findViewById(R.id.right_time);
            } else {
                convertView = inflater.inflate(R.layout.chat_dialog_left_item, parent, false);
                holder = new ViewHolder();
                holder.touxiangImage = convertView.findViewById(R.id.ivicon);
                holder.content = convertView.findViewById(R.id.tvname);
                holder.nickName = convertView.findViewById(R.id.leftNickName);
                holder.date = convertView.findViewById(R.id.left_time);
            }
            holder.content.setText(message.getContent());
        }
        else if(message.getType()==1) {
            if (message.getSenderId() == user.getUserId()) {
                convertView = inflater.inflate(R.layout.right_pic, parent, false);
                holder = new ViewHolder();
                holder.touxiangImage = convertView.findViewById(R.id.right_t1);
                holder.content2 = convertView.findViewById(R.id.right_pic);
                holder.nickName = convertView.findViewById(R.id.right_n1);
                holder.date = convertView.findViewById(R.id.right_t2);
            } else {
                convertView = inflater.inflate(R.layout.left_pic, parent, false);
                holder = new ViewHolder();
                holder.touxiangImage = convertView.findViewById(R.id.left_t1);
                holder.content2 = convertView.findViewById(R.id.left_n1);
                holder.nickName = convertView.findViewById(R.id.left_n2);
                holder.date = convertView.findViewById(R.id.left_t2);
            }

            String patth = stringSaveAsFile(message.getContent(),outOfFile);
            System.out.println("patth = "+patth);
            holder.content2.setImageURI(Uri.fromFile(new File(patth)));
//            Uri uri =getImageContentUri(this.context,translate(message));
//            holder.content2.setImageURI(uri);
        }
        else if(message.getType()==2){
            if (message.getSenderId() == user.getUserId()) {
                convertView = inflater.inflate(R.layout.right_pic, parent, false);
                holder = new ViewHolder();
                holder.touxiangImage = convertView.findViewById(R.id.right_t1);
                holder.content2 = convertView.findViewById(R.id.right_pic);
                holder.nickName = convertView.findViewById(R.id.right_n1);
                holder.date = convertView.findViewById(R.id.right_t2);
            } else {
                convertView = inflater.inflate(R.layout.left_pic, parent, false);
                holder = new ViewHolder();
                holder.touxiangImage = convertView.findViewById(R.id.left_t1);
                holder.content2 = convertView.findViewById(R.id.left_n1);
                holder.nickName = convertView.findViewById(R.id.left_n2);
                holder.date = convertView.findViewById(R.id.left_t2);
            }
//            Uri uri = Uri.parse(message.getContent());
//            System.out.println("the uri has been changed : " + uri.toString());

            String uripath = message.getContent().substring(1);
            System.out.println("the uri path is "+uripath);
            holder.content2.setImageURI(Uri.fromFile(new File(uripath)));
            System.out.println("in the getview the content is " + message.getContent().substring(1));
        }


        convertView.setTag(holder);
//        holder.touxiangImage.setImageResource(message.getHeadSculpture());
//        holder.content.setText(message.getContent());
        holder.touxiangImage.setImageResource(R.drawable.liweisi);
        holder.nickName.setText(message.getNickName());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String data = formatter.format(message.getTime());
        holder.date.setText(data);
        return convertView;
    }

    public File translate(MessageContent msg) {
        File file=null;
        try {
            String picm = msg.getContent();
            byte[] bytes;
            bytes = picm.getBytes("ISO8859-1");
            file = byte2image(bytes);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            System.out.println("UnsupportedEncodingException");
        }
        return file;
    }

    public File byte2image(byte[] data) {
        String filename = System.currentTimeMillis()+"";
        String path =  context.getFilesDir().getPath().toString()+filename+".jpg";
        System.out.println("path="+path);
        File img = new File(filename);
        try {
            if(!img.exists())
            {
                img.createNewFile();
            }
            FileOutputStream imageOutput = new FileOutputStream(img);
            imageOutput.write(data);
            imageOutput.close();
        } catch (Exception ex) {
            System.out.println("Exception: " + ex);
            ex.printStackTrace();
        }
        return img;
    }

    public static Uri getImageContentUri(Context context, File imageFile) {
        String filePath = imageFile.getAbsolutePath();
        Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[] { MediaStore.Images.Media._ID }, MediaStore.Images.Media.DATA + "=? ",
                new String[] { filePath }, null);
        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID));
            Uri baseUri = Uri.parse("content://media/external/images/media");
            return Uri.withAppendedPath(baseUri, "" + id);
        } else {
            if (imageFile.exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, filePath);
                return context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                return null;
            }
        }
    }

    //**************************************************************
    /**
            * summary:将字符串存储为文件 采用Base64解码

             */
    public String streamSaveAsFile(InputStream is, String outFileStr) {
        FileOutputStream fos = null;
        try {

            String filename = System.currentTimeMillis()+"";
            String path =  context.getFilesDir().getPath().toString()+filename+".jpg";
            outFileStr = path;
            System.out.println("new path is "+path);

            File file = new File(path);
            BASE64Decoder decoder = new BASE64Decoder();
            fos = new FileOutputStream(file);
            byte[] buffer = decoder.decodeBuffer(is);
            fos.write(buffer, 0, buffer.length);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            try {
                is.close();
                fos.close();
            } catch (Exception e2) {
                e2.printStackTrace();
                throw new RuntimeException(e2);
            }
        }
        return outFileStr;
    }

    /**
     *
     *
     * summary:将字符串存储为文件
     *

     */
    public String stringSaveAsFile(String fileStr, String outFilePath) {
        InputStream out = new ByteArrayInputStream(fileStr.getBytes());
        String path = streamSaveAsFile(out, outFilePath);
        return path;
    }

    /**
     * 将流转换成字符串 使用Base64加密

     */
    public static String streamToString(InputStream inputStream) throws IOException {
        byte[] bt = toByteArray(inputStream);
        inputStream.close();
        String out = new BASE64Encoder().encodeBuffer(bt);
        return out;
    }

    /**
     * 将流转换成字符串

     */
    public static String fileToString(String filePath) throws IOException {
        File file = new File(filePath);
        FileInputStream is = new FileInputStream(file);
        String fileStr = streamToString(is);
        return fileStr;
    }

    /**
     *
     * summary:将流转化为字节数组

     *
     */
    public static byte[] toByteArray(InputStream inputStream) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024 * 4];
        byte[] result = null;
        try {
            int n = 0;
            while ((n = inputStream.read(buffer)) != -1) {
                out.write(buffer, 0, n);
            }
            result = out.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            out.close();
        }
        return result;
    }

    //*************************************************




    static class ViewHolder {//复用机制
        ImageView touxiangImage;
        TextView content;
        TextView nickName;
        TextView date;
        ImageView content2;
    }

}
