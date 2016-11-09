package com.mersens.gank.mvp.model;

import com.mersens.gank.entity.GankBean;
import com.mersens.gank.mvp.listener.IFrontData;
import com.mersens.gank.mvp.listener.OnResultListener;
import com.mersens.gank.service.ServiceStore;
import com.mersens.gank.utils.RequestManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;

/**
 * Created by Mersens on 2016/11/8.
 */

public class IFrontDataImpl implements IFrontData {
    private RequestManager manager;
    public IFrontDataImpl(){
        manager=RequestManager.getInstance();
    }


    @Override
    public void getInfo(String type, String pageSize, String pageIndex, final OnResultListener listener) {
        ServiceStore service=manager.create(ServiceStore.class);
        Call<ResponseBody> call=service.getInfo(type,pageSize,pageIndex);
        manager.execute(call, new RequestManager.RequestCallBack() {
            @Override
            public void onSueecss(String msg) {
                List<GankBean> list=new ArrayList<GankBean>();
                try {
                    JSONObject jsonObject=new JSONObject(msg);
                    JSONArray array=jsonObject.getJSONArray("results");
                    for(int i=0;i<array.length();i++){
                        JSONObject json = (JSONObject) array.get(i);
                        GankBean bean=new GankBean();
                        bean.set_id(json.getString("_id"));
                        bean.setCreatedAt(json.getString("createdAt"));
                        bean.setDesc(json.getString("desc"));
                        bean.setPublishedAt(json.getString("publishedAt"));
                        bean.setSource(json.getString("source"));
                        bean.setType(json.getString("type"));
                        bean.setUrl(json.getString("url"));
                        bean.setWho(json.getString("who"));
                        if(json.has("images")){
                            JSONArray images=json.getJSONArray("images");
                            List<String> imgs=new ArrayList<String>();
                            imgs.add(images.get(0).toString());
                            bean.setImages(imgs);
                        }
                        list.add(bean);
                    }
                    listener.onSuccess(list);

                } catch (JSONException e) {
                    e.printStackTrace();
                    listener.onError(e);
                }
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
