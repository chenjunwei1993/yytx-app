package com.dyibing.myapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.bumptech.glide.Glide;
import com.dyibing.myapp.R;
import com.dyibing.myapp.activity.ExChangeActivity;
import com.dyibing.myapp.bean.FragmentBean;
import com.dyibing.myapp.common.Constant;
import com.dyibing.myapp.listener.OnMultiClickListener;
import com.dyibing.myapp.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentAdapter extends RecyclerView.Adapter {
    private final Context mContext;
    private List<FragmentBean> fragmentBeans = new ArrayList<>();
    private int imageWidth;

    public FragmentAdapter(Context mContext) {
        this.mContext = mContext;
        imageWidth = (ScreenUtils.getScreenWidth() - SizeUtils.dp2px(70)) / 3;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_fragment, parent, false);
        return new RecyclerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        FragmentBean fragmentBean = fragmentBeans.get(position);
        RecyclerHolder recyclerHolder = (RecyclerHolder) holder;

        RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams) recyclerHolder.rlFragmentItem.getLayoutParams();
        lp.width = imageWidth;
        lp.height = imageWidth;
        recyclerHolder.rlFragmentItem.setLayoutParams(lp);
        if (fragmentBean.getFragmentCount() > 0) {
            recyclerHolder.tvFragmentCount.setVisibility(View.VISIBLE);
            Utils.setText(String.valueOf(fragmentBean.getFragmentCount()), recyclerHolder.tvFragmentCount);
        } else {
            recyclerHolder.tvFragmentCount.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(fragmentBean.getFragmentUrl())) {
            Glide.with(mContext).load(fragmentBean.getFragmentUrl()).into(recyclerHolder.ivFragmentItem);
        }
        holder.itemView.setOnClickListener(new OnMultiClickListener() {
            @Override
            public void onMultiClick(View v) {
                if(fragmentBean.getFragmentCount() > 0){
                    Intent intent = new Intent(mContext, ExChangeActivity.class);
                    intent.putExtra(Constant.FRAGMENT_COUNT, fragmentBean.getFragmentCount());
                    intent.putExtra(Constant.USER_FRAGMENT_ID, fragmentBean.getUserFragmentId());
                    intent.putExtra(Constant.FRAGMENT_URL, fragmentBean.getFragmentUrl());
                    mContext.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return fragmentBeans.size();
    }

    public void setData(List<FragmentBean> fragmentBeans) {
        if (this.fragmentBeans != null) {
            this.fragmentBeans.clear();
            this.fragmentBeans.addAll(fragmentBeans);
        } else {
            this.fragmentBeans.clear();
        }
        notifyDataSetChanged();
    }

    class RecyclerHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_fragment_item)
        ImageView ivFragmentItem;
        @BindView(R.id.tv_fragment_count)
        TextView tvFragmentCount;
        @BindView(R.id.rl_fragment_item)
        RelativeLayout rlFragmentItem;

        private RecyclerHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
