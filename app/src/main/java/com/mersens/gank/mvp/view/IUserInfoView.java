package com.mersens.gank.mvp.view;

/**
 * Created by Mersens on 2016/11/10.
 */

public interface IUserInfoView {
    <T>void onSuccess(T t);
    String getUserName();
    void onError();

}
