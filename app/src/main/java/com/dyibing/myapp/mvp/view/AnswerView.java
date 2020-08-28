package com.dyibing.myapp.mvp.view;

import com.dyibing.myapp.bean.QuestionBean;
import com.dyibing.myapp.net.HttpResult;

public interface AnswerView extends IBaseView {
    /**
     * 获取题目详情
     *
     * @param questionBean
     */
    void getQuestionInfo(QuestionBean questionBean);

    /**
     * 提交答案
     *
     * @param httpResult
     */
    void submitQuestion(HttpResult httpResult);
}
