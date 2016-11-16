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
import com.mersens.gank.mvp.presenter.IOSPresenter;
import com.mersens.gank.mvp.view.IOSView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mersens on 2016/11/8.
 */

public class IOSFragment extends BaseFragment implements IOSView{
    public static final String IOS_TYPE = "IOS";
    @BindView(R.id.id_recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    private boolean isPrepared;
    private LinearLayoutManager mLayoutManager;
    private boolean isRefresh = false;
    private boolean isLoading = false;
    private boolean isFirst=true;
    private int pageSize = 20;
    private int pageIndex = 1;
    private List<GankBean> mList;
    private String type;
    private IOSPresenter presenter;
    private GankAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_ios, null);
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
        type = getArguments().getString(IOS_TYPE);
        init();
    }

    @Override
    public void init() {
        mList = new ArrayList<>();
        presenter=new IOSPresenter(this);
        presenter.getInfo();
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isRefresh = true;
                pageIndex = 1;
                presenter.getInfo();
            }
        });
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            boolean isSlidingToLast = false;
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(dy > 0){
                    isSlidingToLast = true;
                }else{
                    isSlidingToLast = false;
                }
            }
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    int lastVisibleItem = ((LinearLayoutManager) mLayoutManager).findLastVisibleItemPosition();
                    int totalItemCount = mLayoutManager.getItemCount();
                    if (lastVisibleItem >= totalItemCount - 4 && isSlidingToLast) {
                        isLoading = true;
                        pageIndex++;
                        presenter.getInfo();
                    }
                }
            }
        });
        swipeRefreshLayout.setRefreshing(true);
    }


    public static Fragment getInstance(String type) {
        IOSFragment fragment=new IOSFragment();
        Bundle bundle = new Bundle();
        bundle.putString(IOS_TYPE, type);
        fragment.setArguments(bundle);
        return fragment;
    }
    public void stopRefresh() {
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
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
}
