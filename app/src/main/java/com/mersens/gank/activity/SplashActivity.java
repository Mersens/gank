package com.mersens.gank.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.mersens.gank.fragment.SplashFragment;

public class SplashActivity extends SingleBaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolbar.setVisibility(View.GONE);
    }

    @Override
    public Fragment creatFragment() {
        return SplashFragment.getInstance();
    }

    @Override
    public String setActionBarTitle() {
        return null;
    }
}
