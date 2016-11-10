package com.mersens.gank.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mersens.gank.R;
import com.mersens.gank.activity.WebActivity;
import com.mersens.gank.adapter.GankAdapter;
import com.mersens.gank.entity.GankBean;
import com.mersens.gank.mvp.presenter.IAndroidPresenter;
import com.mersens.gank.mvp.view.IAndroidView;

import java.util.ArrayList;
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
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    private boolean isPrepared;
    private List<GankBean> mList;
    private GankAdapter mAdapter;
    private IAndroidPresenter mPresenter;
    private LinearLayoutManager mLayoutManager;
    private boolean isRefresh = false;
    private boolean isLoading = false;
    private boolean isFirst=true;
    private int pageSize = 20;
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
        type = getArguments().getString(ANDROID_TYPE);
        init();
    }

    @Override
    public void init() {
        mList = new ArrayList<>();
        mPresenter = new IAndroidPresenter(this);
        mPresenter.getInfo();
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isRefresh = true;
                pageIndex = 1;
                mPresenter.getInfo();
            }
        });
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastVisibleItem = ((LinearLayoutManager) mLayoutManager).findLastVisibleItemPosition();
                int totalItemCount = mLayoutManager.getItemCount();
                if (lastVisibleItem >= totalItemCount - 5 && dy > 0) {
                    isLoading = true;
                    pageIndex++;
                    mPresenter.getInfo();
                }
            }
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public <T> void onSuccess(T t) {
        List<GankBean> list = (List<GankBean>) t;
        if (isRefresh) {
            mList.clear();
            mList.addAll(list);
            isRefresh = false;
        }
        if (isLoading) {
            mList.addAll(list);
            isLoading = false;
        }
        if(isFirst){
            mList.addAll(list);
            isFirst=false;
        }
        if (mList != null && mList.size() > 0) {
            if (mAdapter == null) {
                mAdapter = new GankAdapter(mList, getActivity());
                mRecyclerView.setAdapter(mAdapter);
                mAdapter.setOnItemClickListener(new GankAdapter.OnRecyclerViewItemClickListener() {

                    @Override
                    public void onItemClick(View view, String url) {
                        Intent intent = new Intent(getActivity(), WebActivity.class);
                        intent.putExtra(WebActivity.URL, url);
                        startActivity(intent);
                    }
                });
            } else {
                mAdapter.setList(mList);
                mAdapter.notifyDataSetChanged();
            }
        }
        stopRefresh();
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
        stopRefresh();
    }

    public static Fragment getInstance(String type) {
        AndroidFragment fragment = new AndroidFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ANDROID_TYPE, type);
        fragment.setArguments(bundle);
        return fragment;
    }

    public void stopRefresh() {
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }
}
