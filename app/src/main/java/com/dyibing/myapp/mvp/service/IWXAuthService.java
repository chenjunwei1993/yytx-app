package com.dyibing.myapp.mvp.service;

import com.dyibing.myapp.bean.WXTicketBean;
import com.dyibing.myapp.bean.WXTokenBean;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface IWXAuthService {
    /**
     * 微信获取Token
     */
    @GET("https://api.weixin.qq.com/cgi-bin/token")
    Observable<WXTokenBean> getWXToken(
            @Query("grant_type") String type,//固定值client_credential
            @Query("appid") String appID,
            @Query("secret") String appSecret
    );

    /**
     * 微信获取Ticket
     */
    @GET("https://api.weixin.qq.com/cgi-bin/ticket/getticket")
    Observable<WXTicketBean> getTicket(
            @Query("access_token") String token,
            @Query("type") int type
    );
}
