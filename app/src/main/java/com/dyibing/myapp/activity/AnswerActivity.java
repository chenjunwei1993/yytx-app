package com.dyibing.myapp.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.dyibing.myapp.R;
import com.dyibing.myapp.adapter.AnswerAdapter;
import com.dyibing.myapp.bean.DataCenter;
import com.dyibing.myapp.bean.QuestionBean;
import com.dyibing.myapp.mvp.presenter.AnswerPresenter;
import com.dyibing.myapp.mvp.view.AnswerView;
import com.dyibing.myapp.net.HttpResult;
import com.dyibing.myapp.utils.SingleToast;
import com.dyibing.myapp.utils.Utils;
import com.google.gson.Gson;

import java.util.HashMap;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class AnswerActivity extends AppCompatActivity implements AnswerView {
    @BindView(R.id.rv_answer)
    RecyclerView rvAnswer;
    @BindView(R.id.tv_question_content)
    TextView tvQuestionContent;

    private AnswerPresenter answerPresenter;
    private AnswerAdapter answerAdapter;
    private String examLogId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);
        init();
    }

    private void init(){
        ButterKnife.bind(this);
        answerAdapter = new AnswerAdapter(this);
        rvAnswer.setAdapter(answerAdapter);
        answerAdapter.setOnItemClickListener(answer -> {
            HashMap<String, Object> paramsMap = new HashMap<>();
            paramsMap.put("examLogId", examLogId);
            paramsMap.put("answer", answer);
            String strEntity = new Gson().toJson(paramsMap);
            RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), strEntity);
            answerPresenter.submitQuestion(body);
        });
        LinearLayoutManager manager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rvAnswer.setLayoutManager(manager);

        answerPresenter = new AnswerPresenter(this,this);
        answerPresenter.getQuestionInfo(DataCenter.getInstance().getUserGrade(),"");
    }

    @Override
    public void getQuestionInfo(QuestionBean questionBean) {
        Utils.setText(questionBean.getQuestionContent(),tvQuestionContent);
        answerAdapter.setData(questionBean.getAnswerList());
        examLogId = questionBean.getExamLogId();
    }

    @Override
    public void submitQuestion(HttpResult httpResult) {
        if (httpResult != null) {
            if ("0000".equals(httpResult.getCode())) {
                //获取下一道题
                answerPresenter.getQuestionInfo(DataCenter.getInstance().getUserGrade(),"");
            } else {
                SingleToast.showMsg(httpResult.getMsg());
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        answerPresenter.onDestory();
    }
}
