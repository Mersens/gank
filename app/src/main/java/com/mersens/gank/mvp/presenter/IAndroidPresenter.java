package com.mersens.gank.mvp.presenter;

import com.mersens.gank.mvp.listener.IAndroidData;
import com.mersens.gank.mvp.listener.OnResultListener;
import com.mersens.gank.mvp.model.IAndroidDataImpl;
import com.mersens.gank.mvp.view.IAndroidView;

/**
 * Created by Mersens on 2016/11/8.
 */

public class IAndroidPresenter {
    private IAndroidData mIAndroidData;
    private IAndroidView mIAndroidView;
    public IAndroidPresenter(IAndroidView mIAndroidView){
        this.mIAndroidView=mIAndroidView;
        mIAndroidData=new IAndroidDataImpl();
    }

    public void getInfo(){
        mIAndroidData.getInfo(mIAndroidView.getType(), mIAndroidView.getPageSize(), mIAndroidView.getPageIndex(), new OnResultListener() {
            @Override
            public <T> void onSuccess(T t) {
                mIAndroidView.onSuccess(t);

            }

            @Override
            public void onStart() {

            }

            @Override
            public void onError(Exception e) {
                mIAndroidView.onError();
            }

            @Override
            public void onFinish() {

            }
        });
    }

}
