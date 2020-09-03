package com.dyibing.myapp.mvp.view;

import com.dyibing.myapp.bean.GameBean;
import com.dyibing.myapp.net.HttpResult;

public interface UserGameView {
    void getGameUserId(GameBean gameBean);

    void saveGameUser(HttpResult httpResult);
}
