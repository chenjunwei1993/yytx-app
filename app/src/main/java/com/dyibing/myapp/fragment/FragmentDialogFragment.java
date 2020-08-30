package com.dyibing.myapp.fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ScreenUtils;
import com.bumptech.glide.Glide;
import com.dyibing.myapp.R;

import java.util.Random;

public class FragmentDialogFragment extends BaseDialogFragment {
    private String fragmentUrl;

    public void setFragmentUrl(String fragmentUrl) {
        this.fragmentUrl = fragmentUrl;
    }

    @Override
    protected View getContentView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_fragment_fragment, null);
        //按照比例布局 适配
        LinearLayout ll_fragment = view.findViewById(R.id.ll_fragment);
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) ll_fragment.getLayoutParams();
        lp.width = (int) (ScreenUtils.getScreenWidth() * 0.714);
        lp.height = (int) (ScreenUtils.getScreenWidth() * 0.714 * 0.87);
        ll_fragment.setLayoutParams(lp);

        ImageView iv_fragment = view.findViewById(R.id.iv_fragment);
        LinearLayout.LayoutParams lp2 = (LinearLayout.LayoutParams) iv_fragment.getLayoutParams();
        lp2.width = (int) (ScreenUtils.getScreenWidth() * 0.288);
        lp2.height = (int) (ScreenUtils.getScreenWidth() * 0.288);
        iv_fragment.setLayoutParams(lp2);
        if (!TextUtils.isEmpty(fragmentUrl)) {
            Glide.with(mContext).load(fragmentUrl).into(iv_fragment);
        }

        return view;
    }
}
