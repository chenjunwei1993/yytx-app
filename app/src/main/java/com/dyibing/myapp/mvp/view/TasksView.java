package com.dyibing.myapp.mvp.view;

import com.dyibing.myapp.net.HttpResult;


public interface TasksView extends IBaseView {
    void onCurrentDateTask(CurrentDateTaskBean currentDateTaskBean);
    void onUpdateCurrentDateTask(HttpResult result);
}
