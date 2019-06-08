package com.example.mysports.util;

import java.util.Date;
import java.text.SimpleDateFormat;

public class DataUtil {
    public Date getDate(Date date){
        try{
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date1 = simpleDateFormat.parse(simpleDateFormat.format(date));
            return date1;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
