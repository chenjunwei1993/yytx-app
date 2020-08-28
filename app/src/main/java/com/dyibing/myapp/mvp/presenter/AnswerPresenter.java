package com.dyibing.myapp.mvp.presenter;

import android.content.Context;

import com.dyibing.myapp.bean.QuestionBean;
import com.dyibing.myapp.mvp.model.AnswerModel;
import com.dyibing.myapp.mvp.view.AnswerView;
import com.dyibing.myapp.mvp.view.IBaseView;
import com.dyibing.myapp.net.HttpResult;
import com.dyibing.myapp.net.rx.ProgressSubscriber;

import okhttp3.RequestBody;
import rx.Subscription;

public class AnswerPresenter extends BasePresenter{

    private final AnswerModel mModel;

    public AnswerPresenter(Context mContext, IBaseView view) {
        super(mContext, view);
        mModel = new AnswerModel();
    }

    /**
     * 提交答案
     */
    public void submitQuestion(RequestBody body) {
        Subscription subscription = mModel.submitQuestion(body,new ProgressSubscriber(o -> ((AnswerView) mView).submitQuestion((HttpResult) o), mContext));
        subList.add(subscription);
    }

    /**
     * 提交答案
     */
    public void getQuestionInfo(String userGrade, String questionIds) {
        Subscription subscription = mModel.getQuestionInfo(userGrade,questionIds,new ProgressSubscriber(o -> ((AnswerView) mView).getQuestionInfo((QuestionBean) o), mContext));
        subList.add(subscription);
    }


}
