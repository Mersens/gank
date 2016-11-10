package com.mersens.gank.mvp.model;

import com.mersens.gank.mvp.listener.IDownload;
import com.mersens.gank.mvp.listener.OnResultListener;
import com.mersens.gank.service.ServiceStore;
import com.mersens.gank.utils.RequestManager;

import okhttp3.ResponseBody;
import retrofit2.Call;

/**
 * Created by Mersens on 2016/11/10.
 */

public class IDownloadImpl implements IDownload {
    private RequestManager manager;
    public IDownloadImpl(){
        manager=RequestManager.getInstance();

    }

    @Override
    public void download(String url,final OnResultListener listener) {
        ServiceStore service=manager.create(ServiceStore.class);
        Call<ResponseBody> call=service.download(url);
        manager.download(call, new RequestManager.RequestCallBack() {
            @Override
            public void onSueecss(String msg) {
                listener.onSuccess(msg);
            }

            @Override
            public void onError(String msg) {
                listener.onError(new Exception(msg));

            }

            @Override
            public void onStart() {
                listener.onStart();

            }

            @Override
            public void onFinish() {
                listener.onFinish();

            }
        });

    }
}
