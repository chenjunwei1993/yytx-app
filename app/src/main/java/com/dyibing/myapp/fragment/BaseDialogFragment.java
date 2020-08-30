package com.dyibing.myapp.fragment;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.Nullable;

import com.dyibing.myapp.utils.Utils;


/**
 * @author chenjunwei
 * @desc
 * @date 2019/8/9
 */
public abstract class BaseDialogFragment extends DialogFragment {
  protected Context mContext;

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
    if (getDialog() != null) {
      Window window = getDialog().getWindow();
      if(null != window){
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.addFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
//        getDialog().setOnShowListener(dialog -> {
//          window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
//          Utils.fullScreen(window);
//        });
      }
    }
    return super.onCreateView(inflater, container, savedInstanceState);
  }

  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    mContext = getActivity();
    Dialog dialog = new Dialog(mContext);
    dialog.setContentView(getContentView());
    dialog.setCanceledOnTouchOutside(false);
    dialog.setCancelable(true);
    return dialog;
  }

  protected abstract View getContentView();

}
