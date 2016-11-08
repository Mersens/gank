package com.mersens.gank.fragment;

import android.support.v4.app.Fragment;

/**
 * Created by Mersens on 2016/11/8.
 */

public abstract class BaseFragment extends Fragment {
    protected boolean isVisible;
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    protected void onVisible() {
        lazyLoad();
    }

    protected void onInvisible() {}

    protected abstract void lazyLoad();

    public abstract void init();

}
