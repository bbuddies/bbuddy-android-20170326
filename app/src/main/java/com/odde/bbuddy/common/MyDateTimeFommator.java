package com.odde.bbuddy.common;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by lizz on 2017/3/27.
 */

public class MyDateTimeFommator {
    public String getNowString(){
        SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date dt=new Date();
        return formatter.format(dt);
    }
}
