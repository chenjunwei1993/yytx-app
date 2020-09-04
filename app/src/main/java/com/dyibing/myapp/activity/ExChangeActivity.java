package com.dyibing.myapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.dyibing.myapp.R;
import com.dyibing.myapp.bean.FragmentBean;
import com.dyibing.myapp.bean.FragmentResult;
import com.dyibing.myapp.bean.UserInfoBean;
import com.dyibing.myapp.common.Constant;
import com.dyibing.myapp.mvp.presenter.FragmentPresenter;
import com.dyibing.myapp.mvp.presenter.UserInfoPresenter;
import com.dyibing.myapp.mvp.view.FragmentView;
import com.dyibing.myapp.mvp.view.UserInfoView;
import com.dyibing.myapp.net.HttpResult;
import com.dyibing.myapp.utils.SingleToast;
import com.dyibing.myapp.utils.Utils;
import com.dyibing.myapp.utils.tts.AudioUtils;
import com.dyibing.myapp.view.AmountView;
import com.dyibing.myapp.view.CircleImageView;
import com.google.gson.Gson;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class ExChangeActivity extends AppCompatActivity implements UserInfoView, FragmentView {
    @BindView(R.id.circle_avatar)
    CircleImageView circleAvatar;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_forest_coin_count)
    TextView tvForestCoinCount;
    @BindView(R.id.amount_view)
    AmountView amountView;
    @BindView(R.id.iv_fragment)
    ImageView ivFragment;
    @BindView(R.id.tv_total_forest)
    TextView tvTotalForest;

    private FragmentPresenter fragmentPresenter;
    private UserInfoPresenter userInfoPresenter;
    private int fragmentCount;
    private int userFragmentId;
    private String fragmentUrl;
    private int fragmentSaleCount = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange);
        init();
    }

    private void init() {
        ButterKnife.bind(this);
        fragmentPresenter = new FragmentPresenter(this, this);
        userInfoPresenter = new UserInfoPresenter(this, this);
        userInfoPresenter.getUserInfo();
        Intent intent = getIntent();
        fragmentCount = intent.getIntExtra(Constant.FRAGMENT_COUNT, 1);
        userFragmentId = intent.getIntExtra(Constant.USER_FRAGMENT_ID, 0);
        fragmentUrl = intent.getStringExtra(Constant.FRAGMENT_URL);

        if (!TextUtils.isEmpty(fragmentUrl)) {
            Glide.with(this).load(fragmentUrl).into(ivFragment);
        }
        amountView.setGoods_storage(fragmentCount);
        amountView.setOnAmountChangeListener((view, amount, position) -> {
            tvTotalForest.setText(getString(R.string.exchange_total, String.valueOf(amount * 5)));
            fragmentSaleCount = amount;
        });
    }

    @OnClick({R.id.iv_forest_coin, R.id.ll_back})
    public void onclick(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.iv_forest_coin:
                //兑换
                HashMap<String, Object> paramsMap = new HashMap<>();
                paramsMap.put("userFragmentId", userFragmentId);
                paramsMap.put("fragmentSaleCount", fragmentSaleCount);
                String strEntity = new Gson().toJson(paramsMap);
                RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), strEntity);
                fragmentPresenter.fragmentSale(body);
                break;
        }
    }

    @Override
    public void onUserInfo(UserInfoBean userInfoBean) {
        if (userInfoBean == null) {
            return;
        }
        Utils.setText(String.valueOf(userInfoBean.getForestCoinCount()), tvForestCoinCount);
        if (!TextUtils.isEmpty(userInfoBean.getAvatarUrl())) {
            Glide.with(this).load(userInfoBean.getAvatarUrl()).into(circleAvatar);
        }
        if (!TextUtils.isEmpty(userInfoBean.getNickName())) {
            tvName.setText(userInfoBean.getNickName());
        } else {
            tvName.setText(userInfoBean.getUserId());
        }
    }

    @Override
    public void getFragment(FragmentBean fragmentBean) {

    }

    @Override
    public void getFragmentList(FragmentResult fragmentResult) {

    }

    @Override
    public void fragmentSale(HttpResult httpResult) {
        if (null != httpResult) {
            if ("0000".equals(httpResult.getCode())) {
                SingleToast.showMsg("兑换成功");
                finish();
            }else {
                SingleToast.showMsg(httpResult.getMsg());
            }
        }
    }
}
