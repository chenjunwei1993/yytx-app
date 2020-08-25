package com.dyibing.myapp.bean;

public class LoginBean {


    /**
     * userOpenId : wyheyye7777
     * token : mhaeenml31cbnv5v1798bq68jdg1cd1i
     */

    private String userOpenId;
    private String token;
    private String userStockType;
    private String receiveForestCoinStatus;

    public String getUserStockType() {
        return userStockType;
    }

    public void setUserStockType(String userStockType) {
        this.userStockType = userStockType;
    }

    public String getUserOpenId() {
        return userOpenId;
    }

    public void setUserOpenId(String userOpenId) {
        this.userOpenId = userOpenId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getReceiveForestCoinStatus() {
        return receiveForestCoinStatus;
    }

    public void setReceiveForestCoinStatus(String receiveForestCoinStatus) {
        this.receiveForestCoinStatus = receiveForestCoinStatus;
    }
}
