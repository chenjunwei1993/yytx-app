package com.dyibing.myapp.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.blankj.utilcode.util.ScreenUtils;
import com.dyibing.myapp.R;

public class ForestDialogFragment extends BaseDialogFragment {

    @Override
    protected View getContentView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_fragment_forest, null);
        //按照比例布局 适配
        LinearLayout ll_dice = view.findViewById(R.id.ll_dice);
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) ll_dice.getLayoutParams();
        lp.width = (int) (ScreenUtils.getScreenWidth() * 0.69);
        lp.height = (int) (ScreenUtils.getScreenWidth() * 0.69 * 0.89);
        ll_dice.setLayoutParams(lp);
        view.setOnClickListener(v -> dismiss());
        return view;
    }
}
