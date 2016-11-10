package com.mersens.gank.mvp.presenter;

import com.mersens.gank.mvp.listener.IUserInfo;
import com.mersens.gank.mvp.listener.OnResultListener;
import com.mersens.gank.mvp.model.IUserInfoImpl;
import com.mersens.gank.mvp.view.IUserInfoView;

/**
 * Created by Mersens on 2016/11/8.
 */

public class IUserInfoPresenter {
    private IUserInfo mIUserInfo;
    private IUserInfoView mIUserInfoView;
    public IUserInfoPresenter(IUserInfoView mIUserInfoView){
        this.mIUserInfoView=mIUserInfoView;
        mIUserInfo=new IUserInfoImpl();
    }

    public void getInfo(){
        mIUserInfo.getInfo(mIUserInfoView.getUserName(), new OnResultListener() {
            @Override
            public <T> void onSuccess(T t) {
                mIUserInfoView.onSuccess(t);
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onError(Exception e) {
                mIUserInfoView.onError();
            }

            @Override
            public void onFinish() {

            }
        });
    }

}
