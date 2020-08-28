package com.dyibing.myapp.utils;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

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

    public static List<String> getGradeList() {
        List<String> gradeList = new ArrayList<>();
        gradeList.add("幼儿园");
        gradeList.add("一年级");
        gradeList.add("二年级");
        gradeList.add("三年级");
        gradeList.add("四年级");
        gradeList.add("五年级");
        gradeList.add("六年级");
        return gradeList;
    }

    public static String getGrade(String grade) {
        String gradeStr = "";
        switch (grade) {
            case "kinderGarten":
                gradeStr = "幼儿园";
                break;
            case "firstGrade":
                gradeStr = "一年级";
                break;
            case "secondGrade":
                gradeStr = "二年级";
                break;
            case "threeGrade":
                gradeStr = "三年级";
                break;
            case "fourthGrade":
                gradeStr = "四年级";
                break;
            case "fifthGrade":
                gradeStr = "五年级";
                break;
            case "sixGrade":
                gradeStr = "六年级";
                break;
            default:
                break;
        }
        return gradeStr;
    }

    public static String getRequestGrade(String grade) {
        String gradeStr = "";
        switch (grade) {
            case "幼儿园":
                gradeStr = "kinderGarten";
                break;
            case "一年级":
                gradeStr = "firstGrade";
                break;
            case "二年级":
                gradeStr = "secondGrade";
                break;
            case "三年级":
                gradeStr = "threeGrade";
                break;
            case "四年级":
                gradeStr = "fourthGrade";
                break;
            case "五年级":
                gradeStr = "fifthGrade";
                break;
            case "六年级":
                gradeStr = "sixGrade";
                break;
            default:
                break;
        }
        return gradeStr;
    }

    public static String getQuestionIds(List<String> strings) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < strings.size(); i++) {
            if (i == strings.size() - 1) {
                stringBuilder.append(strings.get(i));
            } else {
                stringBuilder.append(strings.get(i) + ",");
            }
        }
        return stringBuilder.toString();
    }

    public static void fullScreen(Window window) {
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        window.getDecorView().setSystemUiVisibility(uiOptions);
    }
}
