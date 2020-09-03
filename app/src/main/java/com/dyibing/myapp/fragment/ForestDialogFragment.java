package com.dyibing.myapp.fragment;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.blankj.utilcode.util.ScreenUtils;
import com.dyibing.myapp.R;
import com.dyibing.myapp.activity.MainActivity;
import com.dyibing.myapp.activity.StartAnswerActivity;

public class ForestDialogFragment extends BaseDialogFragment {

    @Override
    protected View getContentView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_fragment_forest, null);
        //按照比例布局 适配
        LinearLayout ll_warning = view.findViewById(R.id.ll_warning);
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) ll_warning.getLayoutParams();
        lp.width = (int) (ScreenUtils.getScreenWidth() * 0.69);
        lp.height = (int) (ScreenUtils.getScreenWidth() * 0.69 * 0.89);
        ll_warning.setLayoutParams(lp);
        ImageView iv_go_gold = view.findViewById(R.id.iv_go_gold);
        iv_go_gold.setOnClickListener(v -> {
            dismiss();
            startActivity(new Intent(mContext, StartAnswerActivity.class));
        });
        return view;
    }
}
