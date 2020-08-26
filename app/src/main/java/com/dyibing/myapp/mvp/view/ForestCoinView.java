package com.dyibing.myapp.mvp.view;

import com.dyibing.myapp.net.HttpResult;

public interface ForestCoinView extends IBaseView{

    void onReceiveForestCoin(HttpResult httpResult);
}
