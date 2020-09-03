package com.dyibing.myapp.mvp.view;

import com.dyibing.myapp.bean.ForestCoinBean;
import com.dyibing.myapp.net.HttpResult;

public interface ForestCoinView extends IBaseView {

    void onReceiveForestCoin(HttpResult httpResult);

    void onReceiveForestCoinStatus(ForestCoinBean forestCoinBean);

    void onUseForestCoin(HttpResult httpResult);
}
