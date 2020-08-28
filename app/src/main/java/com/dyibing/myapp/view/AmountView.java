package com.dyibing.myapp.view;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dyibing.myapp.R;

public class AmountView extends LinearLayout implements View.OnClickListener, TextWatcher {
    private int position = -1;

    private int amount = 0; //购买数量
    private int goods_storage = 100; //商品库存
    private EditText etAmount;
    private TextView tvDecrease;
    private TextView tvIncrease;

    public AmountView(Context context) {
        this(context, null);
    }

    public AmountView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //组件布局
        LayoutInflater.from(context).inflate(R.layout.view_amount, this);
        etAmount = (EditText) findViewById(R.id.etAmount);
        tvDecrease = (TextView) findViewById(R.id.tv_decrease);
        tvIncrease = (TextView) findViewById(R.id.tv_increase);
        tvDecrease.setOnClickListener(this);
        tvIncrease.setOnClickListener(this);
        etAmount.addTextChangedListener(this);
        etAmount.setFocusable(false);
    }


    /**
     * 位置
     *
     * @param position
     */
    public void setPosition(int position) {
        this.position = position;
    }

    /**
     * 设置库存方法
     *
     * @param goods_storage
     */
    public void setGoods_storage(int goods_storage) {
        this.goods_storage = goods_storage;
    }

    /**
     * 获取数量
     *
     * @return
     */
    public int getAmount() {
        return amount;
    }

    /**
     * 设置数量
     *
     * @param amount
     */
    public void setAmount(int amount) {
        this.amount = amount;
        etAmount.setText(this.amount + "");
        if (this.amount >= 0) {
            etAmount.setVisibility(VISIBLE);
            tvDecrease.setVisibility(VISIBLE);
        }
    }

    /**
     * 增加，减少事件监听
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        int i = v.getId();
        switch (i) {
            case R.id.tv_decrease:
                amount--;
                if (amount <= 1) {
                    amount = 1;
                }
                etAmount.setText(amount + "");
                break;
            case R.id.tv_increase:
                amount++;
                if (amount >= goods_storage) {
                    amount = goods_storage;
                }
                etAmount.setText(amount + "");
                break;
        }
        etAmount.clearFocus();

        changeStatus();

        if (mListener != null) {
            mListener.onAmountChange(this, amount, position);
        }
    }

    private void changeStatus() {
        if (amount >= goods_storage) {
            tvIncrease.setBackground(getResources().getDrawable(R.drawable.bg_amount_unable));
        } else {
            tvIncrease.setBackground(getResources().getDrawable(R.drawable.bg_amount_enable));
        }
        if (amount <= 1) {
            tvDecrease.setBackground(getResources().getDrawable(R.drawable.bg_amount_unable));
        } else {
            tvDecrease.setBackground(getResources().getDrawable(R.drawable.bg_amount_enable));
        }
    }

    /**
     * 数量变化监听
     *
     * @param s
     * @param start
     * @param count
     * @param after
     */
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (s.toString().isEmpty())
            return;
        amount = Integer.valueOf(s.toString());
        if (amount > goods_storage) {
            amount = goods_storage;
            etAmount.setText(goods_storage + "");
            changeStatus();
            return;
        }

    }

    /**
     * 自定义接口 监听数量变化，
     */
    private OnAmountChangeListener mListener;

    public interface OnAmountChangeListener {
        void onAmountChange(View view, int amount, int position);
    }

    public void setOnAmountChangeListener(OnAmountChangeListener onAmountChangeListener) {
        this.mListener = onAmountChangeListener;
    }
}
