package com.mersens.gank.mvp.presenter;

import com.mersens.gank.mvp.listener.IResourcesData;
import com.mersens.gank.mvp.listener.OnResultListener;
import com.mersens.gank.mvp.model.IResourcesDataImpl;
import com.mersens.gank.mvp.view.IRecourcesView;

/**
 * Created by Mersens on 2016/11/8.
 */

public class IResourcesPresenter {
    private IResourcesData mIResourcesData;
    private IRecourcesView mIRecourcesView;
    public IResourcesPresenter(IRecourcesView mIRecourcesView){
        this.mIRecourcesView=mIRecourcesView;
        mIResourcesData=new IResourcesDataImpl();
    }

    public void getInfo(){
        mIResourcesData.getInfo(mIRecourcesView.getType(), mIRecourcesView.getPageSize(), mIRecourcesView.getPageIndex(), new OnResultListener() {
            @Override
            public <T> void onSuccess(T t) {
                mIRecourcesView.onSuccess(t);

            }

            @Override
            public void onStart() {

            }

            @Override
            public void onError(Exception e) {
                mIRecourcesView.onError();
            }

            @Override
            public void onFinish() {

            }
        });
    }

}
