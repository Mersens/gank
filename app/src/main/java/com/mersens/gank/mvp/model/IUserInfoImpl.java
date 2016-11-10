package com.mersens.gank.mvp.model;

import com.mersens.gank.entity.UserBean;
import com.mersens.gank.mvp.listener.IUserInfo;
import com.mersens.gank.mvp.listener.OnResultListener;
import com.mersens.gank.service.ServiceStore;
import com.mersens.gank.utils.RequestManager;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;

/**
 * Created by Mersens on 2016/11/8.
 */

public class IUserInfoImpl implements IUserInfo {
    private RequestManager manager;
    public IUserInfoImpl(){
        manager=RequestManager.getInstance();
    }

    @Override
    public void getInfo(String username,final OnResultListener listener) {
        ServiceStore service=manager.create(ServiceStore.class);
        Call<ResponseBody> call=service.getUserInfo(username);
        manager.execute(call, new RequestManager.RequestCallBack() {
            @Override
            public void onSueecss(String msg) {
                try {
                    JSONObject jsonObject=new JSONObject(msg);
                    UserBean user=new UserBean();
                    user.setLogin(jsonObject.getString("login"));
                    user.setEmail(jsonObject.getString("email"));
                    user.setAvatar_url(jsonObject.getString("avatar_url"));
                    listener.onSuccess(user);
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
