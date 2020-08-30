package com.dyibing.myapp.mvp.presenter;

import android.content.Context;

import com.dyibing.myapp.bean.ForestCoinBean;
import com.dyibing.myapp.bean.QuestionBean;
import com.dyibing.myapp.bean.SubmitQuestionBean;
import com.dyibing.myapp.mvp.model.AnswerModel;
import com.dyibing.myapp.mvp.view.AnswerView;
import com.dyibing.myapp.mvp.view.IBaseView;
import com.dyibing.myapp.net.HttpResult;
import com.dyibing.myapp.net.rx.ProgressSubscriber;

import okhttp3.RequestBody;
import rx.Subscription;

public class AnswerPresenter extends BasePresenter {

    private final AnswerModel mModel;

    public AnswerPresenter(Context mContext, IBaseView view) {
        super(mContext, view);
        mModel = new AnswerModel();
    }

    /**
     * 提交答案
     */
    public void submitQuestion(RequestBody body) {
        Subscription subscription = mModel.submitQuestion(body, new ProgressSubscriber(o -> ((AnswerView) mView).submitQuestion((HttpResult<SubmitQuestionBean>) o), mContext, false));
        subList.add(subscription);
    }

    /**
     * 获取题目信息
     */
    public void getQuestionInfo(String userGrade, String questionIds, String batchNumber) {
        Subscription subscription = mModel.getQuestionInfo(userGrade, questionIds, batchNumber, new ProgressSubscriber(o -> ((AnswerView) mView).getQuestionInfo((QuestionBean) o), mContext));
        subList.add(subscription);
    }

    /**
     * 提交答案
     */
    public void queryForestCoinCountByBatchNumber(String batchNumber) {
        Subscription subscription = mModel.queryForestCoinCountByBatchNumber(batchNumber, new ProgressSubscriber(o -> ((AnswerView) mView).queryForestCoinCountByBatchNumber((ForestCoinBean) o), mContext));
        subList.add(subscription);
    }

}
