package com.mersens.gank.mvp.view;

/**
 * Created by Mersens on 2016/11/8.
 */

public interface IAndroidView {
    <T>void onSuccess(T t);
    String getType();
    String getPageSize();
    String getPageIndex();
    void onError();
}
