package com.dyibing.myapp.mvp.view;

import com.dyibing.myapp.bean.FinishStatusBean;
import com.dyibing.myapp.net.HttpResult;

public interface ForestCoinView extends IBaseView{

    void onUserFinishTaskStatus(FinishStatusBean finishStatusBean);

    void onReceiveForestCoin(HttpResult httpResult);
}
