package com.mersens.gank.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mersens.gank.R;
import com.mersens.gank.adapter.GankAdapter;
import com.mersens.gank.entity.GankBean;
import com.mersens.gank.mvp.presenter.IAndroidPresenter;
import com.mersens.gank.mvp.view.IAndroidView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mersens on 2016/11/8.
 */

public class AndroidFragment extends BaseFragment implements IAndroidView {
    public static final String ANDROID_TYPE = "Android";
    @BindView(R.id.id_recyclerview)
    RecyclerView mRecyclerView;
    private boolean isPrepared;
    private List<GankBean> mList;
    private GankAdapter mAdapter;
    private IAndroidPresenter mPresenter;
    private int pageSize = 15;
    private int pageIndex = 1;
    private String type;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_android, null);
        ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isPrepared = true;
        lazyLoad();
    }

    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible) {
            return;
        }
        type=getArguments().getString(ANDROID_TYPE);
        init();
    }

    @Override
    public void init() {
        mPresenter = new IAndroidPresenter(this);
        mPresenter.getInfo();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public <T> void onSuccess(T t) {
        mList = (List<GankBean>) t;
        if (mList != null && mList.size() > 0) {
            mAdapter = new GankAdapter(mList,getActivity());
            mRecyclerView.setAdapter(mAdapter);
        }
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public String getPageSize() {
        return pageSize + "";
    }

    @Override
    public String getPageIndex() {
        return pageIndex + "";
    }

    @Override
    public void onError() {

    }

    public static Fragment getInstance(String type) {
        AndroidFragment fragment = new AndroidFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ANDROID_TYPE, type);
        fragment.setArguments(bundle);
        return fragment;
    }
}
