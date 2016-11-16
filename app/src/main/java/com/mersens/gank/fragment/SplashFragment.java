package com.mersens.gank.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.mersens.gank.R;
import com.mersens.gank.activity.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mersens on 2016/11/16.
 */

public class SplashFragment extends BaseFragment {
    private static final long SPLASH_DELAY_MILLIS = 2000;
    private static final int GO_HOME = 0X01;
    @BindView(R.id.tv_gank)
    TextView tvGank;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_splash, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    public void init() {
        Animation animation= AnimationUtils.loadAnimation(getActivity(),R.anim.scaleto);
        animation.setFillAfter(true);
        tvGank.startAnimation(animation);
        mHandler.sendEmptyMessageDelayed(GO_HOME, SPLASH_DELAY_MILLIS);
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GO_HOME:
                    goHome();
                    break;
            }
            super.handleMessage(msg);
        }
    };

    private void goHome() {
        startActivity(new Intent(getActivity(), MainActivity.class));
    }


    public static Fragment getInstance() {
        return new SplashFragment();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mHandler.removeMessages(GO_HOME);
    }
}
