package com.mersens.gank.activity;

import android.support.v4.app.Fragment;

import com.mersens.gank.fragment.ImageDetailFragment;

/**
 * Created by Mersens on 2016/11/9.
 */

public class ImageDetailActivity extends SingleBaseActivity {

    @Override
    public Fragment creatFragment() {
        return ImageDetailFragment.getInstance(url);
    }

    @Override
    public String setActionBarTitle() {
        return "";
    }
}
