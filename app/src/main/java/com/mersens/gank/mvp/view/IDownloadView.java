package com.mersens.gank.mvp.view;

/**
 * Created by Mersens on 2016/11/10.
 */

public interface IDownloadView {
    void onDownloadSuccess();
    void onDownloadError();
    String getUrl();
}
