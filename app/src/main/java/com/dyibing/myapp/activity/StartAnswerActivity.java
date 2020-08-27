package com.dyibing.myapp.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.dyibing.myapp.R;
import com.dyibing.myapp.utils.tts.AudioUtils;

public class StartAnswerActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_answer);
        AudioUtils.getInstance().speakText(getString(R.string.start_answer_tip));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AudioUtils.getInstance().stopSpeaking();
    }
}