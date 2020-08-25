package com.dyibing.myapp.mvp.view;

import com.dyibing.myapp.bean.WXTicketBean;
import com.dyibing.myapp.bean.WXTokenBean;

public interface WXAuthView extends IBaseView {
    void onWXToken(WXTokenBean wxTokenBean);
    void onWXTicket(WXTicketBean wxTicketBean);
}
