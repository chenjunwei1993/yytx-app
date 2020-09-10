package com.dyibing.myapp.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dyibing.myapp.R;
import com.dyibing.myapp.bean.GiftBean;
import com.dyibing.myapp.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CashGiftAdapter extends RecyclerView.Adapter {
    private final Context mContext;
    private List<GiftBean> giftBeans = new ArrayList<>();

    public CashGiftAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_cash_gift, parent, false);
        return new RecyclerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        GiftBean giftBean = giftBeans.get(position);
        RecyclerHolder recyclerHolder = (RecyclerHolder) holder;

        Utils.setText("名称：" + giftBean.getGiftName(), recyclerHolder.tvGiftName);
        Utils.setText("一句话简介：" + giftBean.getGiftDescribe(), recyclerHolder.tvGiftDescribe);
        if (!TextUtils.isEmpty(giftBean.getGiftUrl())) {
            Glide.with(mContext).load(giftBean.getGiftUrl()).into(recyclerHolder.ivCashGift);
        }
    }

    @Override
    public int getItemCount() {
        return giftBeans.size();
    }

    public void setData(List<GiftBean> giftBeans) {
        if (this.giftBeans != null) {
            this.giftBeans.clear();
            this.giftBeans.addAll(giftBeans);
        } else {
            this.giftBeans.clear();
        }
        notifyDataSetChanged();
    }

    class RecyclerHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_cash_gift)
        ImageView ivCashGift;
        @BindView(R.id.tv_gift_name)
        TextView tvGiftName;
        @BindView(R.id.tv_gift_describe)
        TextView tvGiftDescribe;

        private RecyclerHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

