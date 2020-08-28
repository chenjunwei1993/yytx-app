package com.dyibing.myapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.dyibing.myapp.R;
import com.dyibing.myapp.utils.tts.AudioUtils;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Descripttion: 开始答题 提示页
 * @Author: 陈俊伟
 * @Date: 2020/8/28
 */
public class StartAnswerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_answer);
        ButterKnife.bind(this);
        AudioUtils.getInstance().speakText(getString(R.string.start_answer_tip));
    }

    @OnClick({R.id.iv_start_answer})
    public void onclick(View view) {
        switch (view.getId()) {
            case R.id.iv_start_answer:
                startActivity(new Intent(this, AnswerActivity.class));
                finish();
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        AudioUtils.getInstance().stopSpeaking();
    }
}
