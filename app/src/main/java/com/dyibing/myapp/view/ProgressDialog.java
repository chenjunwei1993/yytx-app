package com.dyibing.myapp.view;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;

import androidx.annotation.Nullable;


import com.blankj.utilcode.util.LogUtils;
import com.dyibing.myapp.R;

/**
 * @Descripttion: 加载框
 * @Author: chenjunwei
 * @Date: 2020/8/19
 */
public class ProgressDialog extends DialogFragment {

    private static final String TAG = ProgressDialog.class.getSimpleName();
    private DialogInterface.OnCancelListener onCancelListener;

    public void setOnCancelListener(DialogInterface.OnCancelListener onCancelListener) {
        this.onCancelListener = onCancelListener;
    }

    public static ProgressDialog newInstance() {
        ProgressDialog f = new ProgressDialog();
        return f;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        if (getDialog() == null) {  // Returns mDialog
            LogUtils.dTag(TAG, "ProgressDialog dialog is null", Log.ERROR);
        }
        super.onActivityCreated(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Dialog dialog = getDialog();
        if (dialog == null) {
            return null;
        }
        if(null != onCancelListener){
            dialog.setOnCancelListener(onCancelListener);
        }
        Window window = dialog.getWindow();
        if (window == null) {
            return null;
        }
        window.requestFeature(Window.FEATURE_NO_TITLE);
        window.setBackgroundDrawableResource(android.R.color.transparent);

        View view = inflater.inflate(R.layout.dialog_progress_layout, container, false);
        ImageView loading = view.findViewById(R.id.dialog_progress_img);
        AnimationDrawable animationDrawable = (AnimationDrawable) loading.getDrawable();
        animationDrawable.start();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Dialog dialog = getDialog();
        if (dialog == null) {
            return;
        }
        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }
        int width = getResources().getDimensionPixelSize(R.dimen.dialog_progress_width);
        int height = getResources().getDimensionPixelSize(R.dimen.dialog_progress_height);
        window.setLayout(width, height);
        window.setGravity(Gravity.CENTER);
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }
}
