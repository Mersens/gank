package com.mersens.gank.mvp.presenter;

import com.mersens.gank.mvp.listener.IWelfareData;
import com.mersens.gank.mvp.listener.OnResultListener;
import com.mersens.gank.mvp.model.IWelfareDataImpl;
import com.mersens.gank.mvp.view.IWelfareView;

/**
 * Created by Mersens on 2016/11/8.
 */

public class IWelfarePresenter {
    private IWelfareData mIWelfareData;
    private IWelfareView mIWelfareView;
    public IWelfarePresenter(IWelfareView mIWelfareView){
        this.mIWelfareView=mIWelfareView;
        mIWelfareData=new IWelfareDataImpl();
    }

    public void getInfo(){
        mIWelfareData.getInfo(mIWelfareView.getType(), mIWelfareView.getPageSize(), mIWelfareView.getPageIndex(), new OnResultListener() {
            @Override
            public <T> void onSuccess(T t) {
                mIWelfareView.onSuccess(t);
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onError(Exception e) {
                mIWelfareView.onError();
            }

            @Override
            public void onFinish() {

            }
        });
    }

}
