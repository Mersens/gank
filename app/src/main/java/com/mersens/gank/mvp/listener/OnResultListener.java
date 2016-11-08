package com.mersens.gank.mvp.listener;

/**
 * Created by Mersens on 2016/11/8.
 */

public interface OnResultListener {
    <T>void onSuccess(T t);
    void onStart();
    void onError(Exception e);
    void onFinish();
}
