package com.mersens.gank.mvp.listener;

/**
 * Created by Mersens on 2016/11/8.
 */

public interface IResourcesData {
     void getInfo(String type, String pageSize, String pageIndex, OnResultListener listener);
}
