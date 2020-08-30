package com.dyibing.myapp.bean;

import java.util.List;

public class QuestionBean {
    private String examLogId;
    private String questionContent;
    private String batchNumber;
    private List<String> answerList;

    public String getExamLogId() {
        return examLogId;
    }

    public void setExamLogId(String examLogId) {
        this.examLogId = examLogId;
    }

    public String getQuestionContent() {
        return questionContent;
    }

    public void setQuestionContent(String questionContent) {
        this.questionContent = questionContent;
    }

    public List<String> getAnswerList() {
        return answerList;
    }

    public void setAnswerList(List<String> answerList) {
        this.answerList = answerList;
    }

    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }
}
