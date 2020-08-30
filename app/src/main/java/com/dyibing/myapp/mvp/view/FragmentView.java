package com.dyibing.myapp.mvp.view;

import com.dyibing.myapp.bean.FragmentBean;
import com.dyibing.myapp.bean.FragmentResult;
import com.dyibing.myapp.net.HttpResult;

public interface FragmentView extends IBaseView {
    /**
     * 获取碎片
     *
     * @param fragmentBean
     */
    void getFragment(FragmentBean fragmentBean);

    /**
     * 获取碎片列表
     *
     * @param fragmentResult
     */
    void getFragmentList(FragmentResult fragmentResult);

    /**
     * 兑换
     *
     * @param httpResult
     */
    void fragmentSale(HttpResult httpResult);
}
