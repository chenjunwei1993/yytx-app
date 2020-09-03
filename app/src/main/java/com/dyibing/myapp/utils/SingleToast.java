package com.dyibing.myapp.utils;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dyibing.myapp.MyApplication;
import com.dyibing.myapp.R;

/**
 * Created by archar on 18-2-8.
 */

public class SingleToast {

    private static final int MIN_DELAY_TIME = 2000;  // 两次间隔
    private static long lastClickTime;
    private static String lastStr = "";

    public static void showMsg(String msg) {
        if (isNeedCheck() && lastStr.equals(msg)) {
            return;
        }
        lastStr = msg;
        //使用布局加载器，将编写的toast_layout布局加载进来
        View view = LayoutInflater.from(MyApplication.getContext()).inflate(R.layout.toast_layout, null);
        //获取TextView
        TextView title = (TextView) view.findViewById(R.id.tv_toast);
        //设置显示的内容
        title.setText(msg);
        Toast mToast = new Toast(MyApplication.getContext());
        //设置Toast要显示的位置，水平居中并在底部，X轴偏移0个单位，Y轴偏移70个单位，
        mToast.setGravity(Gravity.CENTER, 0, 0);
        //设置显示时间
        mToast.setDuration(Toast.LENGTH_SHORT);

        mToast.setView(view);
        mToast.show();
    }


    private static boolean isNeedCheck() {
        boolean flag = true;
        long currentClickTime = System.currentTimeMillis();
        if ((currentClickTime - lastClickTime) >= MIN_DELAY_TIME) {
            flag = false;
        }
        lastClickTime = currentClickTime;
        return flag;
    }
}
