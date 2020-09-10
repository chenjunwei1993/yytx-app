package com.dyibing.myapp.activity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dyibing.myapp.R;
import com.dyibing.myapp.adapter.AnswerAdapter;
import com.dyibing.myapp.bean.DataCenter;
import com.dyibing.myapp.bean.ForestCoinBean;
import com.dyibing.myapp.bean.QuestionBean;
import com.dyibing.myapp.bean.SubmitQuestionBean;
import com.dyibing.myapp.fragment.StartAnswerDialogFragment;
import com.dyibing.myapp.listener.OnDismissListener;
import com.dyibing.myapp.mvp.presenter.AnswerPresenter;
import com.dyibing.myapp.mvp.view.AnswerView;
import com.dyibing.myapp.net.HttpResult;
import com.dyibing.myapp.utils.SingleToast;
import com.dyibing.myapp.utils.Utils;
import com.dyibing.myapp.utils.tts.AudioUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class AnswerActivity extends AppCompatActivity implements AnswerView {
    @BindView(R.id.rv_answer)
    RecyclerView rvAnswer;
    @BindView(R.id.tv_question_content)
    TextView tvQuestionContent;


    private AnswerPresenter answerPresenter;
    private AnswerAdapter answerAdapter;
    private List<String> questionIds = new ArrayList<>();
    private String examLogId;
    private StringBuilder stringBuilder = new StringBuilder();
    private String batchNumber;
    private String sort = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);
        init();
    }

    @OnClick({R.id.iv_play, R.id.tv_end_answer})
    public void onclick(View view) {
        switch (view.getId()) {
            case R.id.iv_play:
                AudioUtils.getInstance().speakText(stringBuilder.toString());
                break;
            case R.id.tv_end_answer:
                if (!TextUtils.isEmpty(batchNumber)) {
                    answerPresenter.queryForestCoinCountByBatchNumber(batchNumber);
                } else {
                    finish();
                }
                break;
        }
    }

    private void init() {
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
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvAnswer.setLayoutManager(manager);

        answerPresenter = new AnswerPresenter(this, this);
        answerPresenter.getQuestionInfo(DataCenter.getInstance().getUserGrade(), "", "");
    }

    @Override
    public void getQuestionInfo(QuestionBean questionBean) {
        if (null != questionBean) {
            if (TextUtils.isEmpty(batchNumber)) {
                //第一次获取batchNumber答题批次号
                batchNumber = questionBean.getBatchNumber();
            }
            Utils.setText(questionBean.getQuestionContent(), tvQuestionContent);
            answerAdapter.setData(questionBean.getAnswerList());
            examLogId = questionBean.getExamLogId();
            questionIds.add(examLogId);
            setSpeckContent(questionBean);
        } else {
            SingleToast.showMsg("未能获取到题目哦！请充足题目哦！");
        }

    }

    private void setSpeckContent(QuestionBean questionBean) {
        stringBuilder.append(questionBean.getQuestionContent());
        if (null != questionBean.getAnswerList() && questionBean.getAnswerList().size() > 0) {
            for (int i = 0; i < questionBean.getAnswerList().size(); i++) {
                String string = questionBean.getAnswerList().get(i);
                stringBuilder.append(sort.charAt(i % sort.length()) + string + " ");
            }
        }
    }

    @Override
    public void submitQuestion(HttpResult<SubmitQuestionBean> httpResult) {
        if (httpResult != null) {
            if (httpResult.getData().getAnswerNum() == 1) {
                if ("0000".equals(httpResult.getCode())) {
                    //获取下一道题
                    answerPresenter.getQuestionInfo(DataCenter.getInstance().getUserGrade(), Utils.getQuestionIds(questionIds), batchNumber);
                } else {
                    showAnswerDialogFragment(StartAnswerDialogFragment.FIRST_ERROR_TYPE);
                }
            } else if (httpResult.getData().getAnswerNum() == 2) {
                if ("0000".equals(httpResult.getCode())) {
                    showAnswerDialogFragment(StartAnswerDialogFragment.SECOND_OK_TYPE);
                } else {
                    showAnswerDialogFragment(StartAnswerDialogFragment.SECOND_ERROR_TYPE, httpResult.getData().getCorrectAnswer());
                }
            }
        }
    }

    @Override
    public void queryForestCoinCountByBatchNumber(ForestCoinBean forestCoinBean) {
        showAnswerDialogFragment(StartAnswerDialogFragment.END_ANSWER_TYPE, "", String.valueOf(forestCoinBean.getForestCoinCount()));
    }

    private StartAnswerDialogFragment startAnswerDialogFragment;

    public void showAnswerDialogFragment(String type) {
        showAnswerDialogFragment(type, "");
    }

    public void showAnswerDialogFragment(String type, String correctAnswer) {
        showAnswerDialogFragment(type, correctAnswer, "");
    }

    /**
     * 学生所有签到记录信息框
     */
    public void showAnswerDialogFragment(String type, String correctAnswer, String forestCount) {
        if (null != startAnswerDialogFragment) {
            startAnswerDialogFragment.dismiss();
        }
        startAnswerDialogFragment = new StartAnswerDialogFragment();
        startAnswerDialogFragment.setType(type);
        startAnswerDialogFragment.setForestCount(forestCount);
        startAnswerDialogFragment.setRightAnswer(correctAnswer);
        startAnswerDialogFragment.setOnDismissListener(() -> {
            if (TextUtils.equals(StartAnswerDialogFragment.SECOND_ERROR_TYPE, type)) {
                //第二次打错 关闭页面
                finish();
            } else if (TextUtils.equals(StartAnswerDialogFragment.SECOND_OK_TYPE, type)) {
                //第二次答对 进行下一道题
                answerPresenter.getQuestionInfo(DataCenter.getInstance().getUserGrade(), Utils.getQuestionIds(questionIds), batchNumber);
            } else if (TextUtils.equals(StartAnswerDialogFragment.END_ANSWER_TYPE, type)) {
                finish();
            }
        });
        startAnswerDialogFragment.show(getFragmentManager(), "showAnswerDialogFragment");
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        answerPresenter.onDestory();
    }
}
