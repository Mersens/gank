package com.mersens.gank.mvp.presenter;

import com.mersens.gank.mvp.listener.IOSData;
import com.mersens.gank.mvp.listener.OnResultListener;
import com.mersens.gank.mvp.model.IOSDataImpl;
import com.mersens.gank.mvp.view.IOSView;

/**
 * Created by Mersens on 2016/11/8.
 */

public class IOSPresenter {
    private IOSData mIOSData;
    private IOSView mIOSView;
    public IOSPresenter(IOSView mIOSView){
        this.mIOSView=mIOSView;
        mIOSData=new IOSDataImpl();
    }

    public void getInfo(){
        mIOSData.getInfo(mIOSView.getType(), mIOSView.getPageSize(), mIOSView.getPageIndex(), new OnResultListener() {
            @Override
            public <T> void onSuccess(T t) {
                mIOSView.onSuccess(t);

            }

            @Override
            public void onStart() {

            }

            @Override
            public void onError(Exception e) {
                mIOSView.onError();
            }

            @Override
            public void onFinish() {

            }
        });
    }

}
