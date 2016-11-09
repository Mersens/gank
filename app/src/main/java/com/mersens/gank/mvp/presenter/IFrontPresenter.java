package com.mersens.gank.mvp.presenter;

import com.mersens.gank.mvp.listener.IFrontData;
import com.mersens.gank.mvp.listener.OnResultListener;
import com.mersens.gank.mvp.model.IFrontDataImpl;
import com.mersens.gank.mvp.view.IFrontView;

/**
 * Created by Mersens on 2016/11/8.
 */

public class IFrontPresenter {
    private IFrontData mIFrontData;
    private IFrontView mIFrontView;
    public IFrontPresenter(IFrontView mIFrontView){
        this.mIFrontView=mIFrontView;
        mIFrontData=new IFrontDataImpl();
    }

    public void getInfo(){
        mIFrontData.getInfo(mIFrontView.getType(), mIFrontView.getPageSize(), mIFrontView.getPageIndex(), new OnResultListener() {
            @Override
            public <T> void onSuccess(T t) {
                mIFrontView.onSuccess(t);

            }

            @Override
            public void onStart() {

            }

            @Override
            public void onError(Exception e) {
                mIFrontView.onError();
            }

            @Override
            public void onFinish() {

            }
        });
    }

}
