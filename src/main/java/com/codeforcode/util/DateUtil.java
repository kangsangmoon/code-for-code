package com.codeforcode.util;

import java.time.LocalDateTime;
import java.util.Date;

public class DateUtil {
    public static Date getDate(){
        return new Date();
    }

    public static LocalDateTime getLocalDateTime(){
        return LocalDateTime.now();
    }

    public static Date getTokenValidTime(Date date, Long validTime){
        return new Date(date.getTime() + validTime);
    }
}