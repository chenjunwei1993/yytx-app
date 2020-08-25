package com.dyibing.myapp.net.rx;

import android.content.Context;

import com.blankj.utilcode.util.NetworkUtils;
import com.dyibing.myapp.MyApplication;
import com.dyibing.myapp.R;
import com.dyibing.myapp.utils.SingleToast;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import rx.Subscriber;

/**
 * 用于在Http请求开始时，自动显示一个ProgressDialog
 * 在Http请求结束是，关闭ProgressDialog
 * 调用者自己对请求数据进行处理
 */
public class ProgressSubscriber<T> extends Subscriber<T> implements ProgressCancelListener {

    private SubscriberOnNextListener mSubscriberOnNextListener;
    private ProgressDialogHandler mProgressDialogHandler;

    private Context context;

    public ProgressSubscriber(SubscriberOnNextListener mSubscriberOnNextListener, Context context) {
        this.mSubscriberOnNextListener = mSubscriberOnNextListener;
        this.context = MyApplication.getContext();
        mProgressDialogHandler = new ProgressDialogHandler(context, this, false);
    }

    public ProgressSubscriber(SubscriberOnNextListener mSubscriberOnNextListener, Context context, boolean isNeedDialog) {
        this.mSubscriberOnNextListener = mSubscriberOnNextListener;
        this.context = context;
        if (isNeedDialog) {
            mProgressDialogHandler = new ProgressDialogHandler(context, this, false);
        }
    }

    private void showProgressDialog() {
        if (mProgressDialogHandler != null) {
            mProgressDialogHandler.obtainMessage(ProgressDialogHandler.SHOW_PROGRESS_DIALOG).sendToTarget();
        }
    }

    private void dismissProgressDialog() {
        if (mProgressDialogHandler != null) {
            mProgressDialogHandler.obtainMessage(ProgressDialogHandler.DISMISS_PROGRESS_DIALOG).sendToTarget();
            mProgressDialogHandler = null;
        }
    }

    /**
     * 订阅开始时调用
     * 显示ProgressDialog
     */
    @Override
    public void onStart() {
        showProgressDialog();
    }

    /**
     * 完成，隐藏ProgressDialog
     */
    @Override
    public void onCompleted() {
        dismissProgressDialog();
        //    Toast.makeText(context, "Get Top Movie Completed", Toast.LENGTH_SHORT).show();
    }

    /**
     * 对错误进行统一处理
     * 隐藏ProgressDialog
     *
     * @param e
     */
    @Override
    public void onError(Throwable e) {
        if (e instanceof SocketTimeoutException) {
            SingleToast.showMsg(MyApplication.getContext().getString(R.string.Network_Connection_timeout));
            MyApplication.HttpStateCode = 999;
        } else if (e instanceof ConnectException) {
            SingleToast.showMsg(MyApplication.getContext().getString(R.string.no_network));
            MyApplication.HttpStateCode = 998;
        } else if (e instanceof UnknownHostException) {
            //域名不可用
            if (NetworkUtils.isConnected()) {
                MyApplication.HttpStateCode = 998;
            } else {
                MyApplication.HttpStateCode = 404;
            }
        } else {

        }
        dismissProgressDialog();
//        RxBus.get().post(ConstantValue.EVENT_TYPE_NETWORK_EXCEPTION, "" + e.getMessage());
    }

    /**
     * 将onNext方法中的返回结果交给Activity或Fragment自己处理
     *
     * @param t 创建Subscriber时的泛型类型
     */
    @Override
    public void onNext(T t) {
        if (mSubscriberOnNextListener != null) {
            mSubscriberOnNextListener.onNext(t);
        }
    }

    /**
     * 取消ProgressDialog的时候，取消对observable的订阅，同时也取消了http请求
     */
    @Override
    public void onCancelProgress() {
        if (!this.isUnsubscribed()) {
            this.unsubscribe();
        }
    }
}