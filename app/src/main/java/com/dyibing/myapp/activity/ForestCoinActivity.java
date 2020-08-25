package com.dyibing.myapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.dyibing.myapp.R;
import com.dyibing.myapp.bean.DataCenter;
import com.dyibing.myapp.bean.FinishStatusBean;
import com.dyibing.myapp.mvp.presenter.ForestCoinPresenter;
import com.dyibing.myapp.mvp.view.ForestCoinView;
import com.dyibing.myapp.net.HttpResult;
import com.dyibing.myapp.utils.SingleToast;
import com.dyibing.myapp.utils.tts.AudioUtils;
import com.google.gson.Gson;

import java.util.HashMap;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class ForestCoinActivity extends AppCompatActivity implements ForestCoinView {
    @BindView(R.id.iv_finish_status)
    ImageView ivFinishStatus;
    @BindView(R.id.btn_getslb)
    Button btnGetslb;
    private String finishStatus;

    private ForestCoinPresenter forestCoinPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forest_coin);
        init();
    }

    private void init(){
        ButterKnife.bind(this);
        forestCoinPresenter = new ForestCoinPresenter(this,this);
        forestCoinPresenter.getUserFinishTaskStatus();
    }

    @OnClick({R.id.btn_getslb})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_getslb:
                HashMap<String, Object> paramsMap = new HashMap<>();
                paramsMap.put("userId", DataCenter.getInstance().getUserId());
                if ("finished".equals(finishStatus)) {
                    paramsMap.put("forestCoinCount", 3);
                } else {
                    paramsMap.put("forestCoinCount", 1);
                }
                String strEntity = new Gson().toJson(paramsMap);
                RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), strEntity);
                forestCoinPresenter.receiveForestCoin(body);
                break;
        }
    }

    @Override
    public void onUserFinishTaskStatus(FinishStatusBean finishStatusBean) {
        if (finishStatusBean != null) {
            finishStatus = finishStatusBean.getFinishTaskStatus();
            if ("finished".equals(finishStatusBean.getFinishTaskStatus())) {
                ivFinishStatus.setImageDrawable(getResources().getDrawable(R.mipmap.five));
                btnGetslb.setText("领3个森林币");
                String bobao = "亲爱的朋友，谢谢你的帮助，你昨天完成了任务并获得森林币，真的太棒了，每天登录养成，完成任务，养成良好的习惯哦。";
                AudioUtils.getInstance().speakText(bobao); //播放语音
            } else {
                ivFinishStatus.setImageDrawable(getResources().getDrawable(R.mipmap.four));
                btnGetslb.setText("领1个森林币");
                String bobao = "嘿！伙计，昨天你没有完成任务获得森林币，怎么了，别忘了丫丫还在等我们的森林币帮助他重建家园呢，快去做任务，获得更多的森林币吧，一起帮丫丫回家。";
                AudioUtils.getInstance().speakText(bobao); //播放语音
            }
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
    protected void onDestroy() {
        super.onDestroy();
        AudioUtils.getInstance().stopSpeaking();
    }
}
