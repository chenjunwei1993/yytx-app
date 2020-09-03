package com.dyibing.myapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.dyibing.myapp.R;
import com.dyibing.myapp.bean.DataCenter;
import com.dyibing.myapp.bean.ForestCoinBean;
import com.dyibing.myapp.mvp.presenter.ForestCoinPresenter;
import com.dyibing.myapp.mvp.view.ForestCoinView;
import com.dyibing.myapp.net.HttpResult;
import com.dyibing.myapp.utils.SingleToast;
import com.dyibing.myapp.utils.tts.AudioUtils;
import com.google.gson.Gson;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class ForestCoinActivity extends AppCompatActivity implements ForestCoinView {
    @BindView(R.id.btn_getslb)
    Button btnGetslb;

    private ForestCoinPresenter forestCoinPresenter;
    private int forestCoinAmount = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forest_coin);
        init();
    }

    private void init() {
        ButterKnife.bind(this);
        forestCoinPresenter = new ForestCoinPresenter(this, this);
        forestCoinPresenter.receiveForestCoinStatus();
    }

    @OnClick({R.id.btn_getslb})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_getslb:
                HashMap<String, Object> paramsMap = new HashMap<>();
                paramsMap.put("userId", DataCenter.getInstance().getUserId());
                paramsMap.put("forestCoinCount", forestCoinAmount);
                String strEntity = new Gson().toJson(paramsMap);
                RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), strEntity);
                forestCoinPresenter.receiveForestCoin(body);
                break;
        }
    }

    @Override
    public void onReceiveForestCoin(HttpResult httpResult) {
        if (httpResult != null) {
            if ("0000".equals(httpResult.getCode())) {
                SingleToast.showMsg("领取成功！");
            } else {
                SingleToast.showMsg(httpResult.getMsg());
            }
        }
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public void onReceiveForestCoinStatus(ForestCoinBean forestCoinBean) {
        forestCoinAmount = forestCoinBean.getForestCoinAmount();
        btnGetslb.setText(getString(R.string.receiver_forest_coin, forestCoinAmount + ""));
    }

    @Override
    public void onUseForestCoin(HttpResult httpResult) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AudioUtils.getInstance().stopSpeaking();
    }
}
