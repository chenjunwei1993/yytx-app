package com.dyibing.myapp.mvp.view;

import com.dyibing.myapp.bean.ForestCoinBean;
import com.dyibing.myapp.bean.QuestionBean;
import com.dyibing.myapp.bean.SubmitQuestionBean;
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
    void submitQuestion(HttpResult<SubmitQuestionBean> httpResult);

    /**
     * 获取本轮答题获取的森林币个数
     *
     * @param forestCoinBean
     */
    void queryForestCoinCountByBatchNumber(ForestCoinBean forestCoinBean);
}
