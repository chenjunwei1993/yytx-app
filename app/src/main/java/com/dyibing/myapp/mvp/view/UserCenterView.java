package com.dyibing.myapp.mvp.view;

import com.dyibing.myapp.bean.UploadResult;
import com.dyibing.myapp.net.HttpResult;

public interface UserCenterView extends IBaseView{
    void onUpdateCurrentDateTask(HttpResult httpResult);

    void onUpload(UploadResult uploadResult);
}
