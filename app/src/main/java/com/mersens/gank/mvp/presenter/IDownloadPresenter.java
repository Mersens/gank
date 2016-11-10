package com.mersens.gank.mvp.presenter;

import com.mersens.gank.mvp.listener.IDownload;
import com.mersens.gank.mvp.listener.OnResultListener;
import com.mersens.gank.mvp.model.IDownloadImpl;
import com.mersens.gank.mvp.view.IDownloadView;

/**
 * Created by Mersens on 2016/11/10.
 */

public class IDownloadPresenter {
    private IDownload mIDownload;
    private IDownloadView mIDownloadView;
    public IDownloadPresenter(IDownloadView mIDownloadView){
        this.mIDownloadView=mIDownloadView;
        mIDownload=new IDownloadImpl();
    }
    public void download(){
        mIDownload.download(mIDownloadView.getUrl(), new OnResultListener() {
            @Override
            public <T> void onSuccess(T t) {
                mIDownloadView.onDownloadSuccess();

            }

            @Override
            public void onStart() {

            }

            @Override
            public void onError(Exception e) {
                mIDownloadView.onDownloadError();

            }

            @Override
            public void onFinish() {

            }
        });

    }

}
