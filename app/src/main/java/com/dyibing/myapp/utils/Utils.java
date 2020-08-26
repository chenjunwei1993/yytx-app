package com.dyibing.myapp.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.TextView;

public class Utils {
    //    当天的星期：monday：星期一，tuesday：星期二，wednesday：星期三，thursday：星期四，friday：星期五，saturday：星期六，sunday：星期日
    public static String getWeekString(String str) {
        String week = "";
        switch (str) {
            case "monday":
                week = "星期一";
                break;
            case "tuesday":
                week = "星期二";
                break;
            case "wednesday":
                week = "星期三";
                break;
            case "thursday":
                week = "星期四";
                break;
            case "friday":
                week = "星期五";
                break;
            case "saturday":
                week = "星期六";
                break;
            case "sunday":
                week = "星期日";
                break;
        }
        return week;
    }

    public static int dp2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public static boolean isReceiveForestCoin(String receiveForestCoinStatus) {
        if (TextUtils.equals("noReceive", receiveForestCoinStatus)) {
            return true;
        }
        return false;
    }

    public static void setText(String text, TextView textView) {
        if (TextUtils.isEmpty(text)) {
            textView.setText("");
        }
        textView.setText(text);
    }
}
