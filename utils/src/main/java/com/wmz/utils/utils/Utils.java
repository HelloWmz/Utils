package com.wmz.utils.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import com.wmz.utils.bean.WeekDay;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;


public class Utils {

    @SuppressLint("StaticFieldLeak")
    private static Context context;

    private Utils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 初始化工具类
     *
     * @param context 上下文
     */
    public static void init(@NonNull final Context context) {
        Utils.context = context.getApplicationContext();
    }

    /**
     * 获取ApplicationContext
     *
     * @return ApplicationContext
     */
    public static Context getContext() {
        if (context != null) return context;
        throw new NullPointerException("u should init first");
    }

    public static List<WeekDay> getWeekDay() {
        Calendar calendar = Calendar.getInstance();
        // 获取本周的第一天
        //int firstDayOfWeek = calendar.getFirstDayOfWeek();

        //获取当前时间为本周的第几天
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        Log.e("test","当前时间为本周的第"+day+"天");
        List<WeekDay> list = new ArrayList<>();
        for (int i = 0; i < 7-day+1; i++) {
            calendar.set(Calendar.DAY_OF_WEEK, day + i);
            WeekDay weekDay = new WeekDay();
            // 获取星期的显示名称，例如：周一、星期一、Monday等等
            weekDay.week = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.ENGLISH);
            weekDay.day = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());

            list.add(weekDay);
        }

        return list;
    }

}
