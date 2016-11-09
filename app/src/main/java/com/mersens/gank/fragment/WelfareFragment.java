package com.mersens.gank.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mersens.gank.R;
import com.mersens.gank.activity.ImageDetailActivity;
import com.mersens.gank.adapter.WelfareAdapter;
import com.mersens.gank.entity.GankBean;
import com.mersens.gank.mvp.presenter.IWelfarePresenter;
import com.mersens.gank.mvp.view.IWelfareView;
import com.mersens.gank.utils.SpacesItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mersens on 2016/11/8.
 */

public class WelfareFragment extends BaseFragment implements IWelfareView {
    public static final String WELFARE_TYPE = "福利";
    @BindView(R.id.id_recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    private boolean isPrepared;
    private List<GankBean> mList;
    private WelfareAdapter mAdapter;
    private IWelfarePresenter mPresenter;
    private StaggeredGridLayoutManager mLayoutManager;
    private boolean isRefresh = false;
    private boolean isLoading = false;
    private boolean isFirst = true;
    private int pageSize = 20;
    private int pageIndex = 1;
    private String type;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_welfare, null);
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
        type = getArguments().getString(WELFARE_TYPE);
        init();
    }

    @Override
    public void init() {
        mList = new ArrayList<>();
        mPresenter = new IWelfarePresenter(this);
        mPresenter.getInfo();
        mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        SpacesItemDecoration decoration = new SpacesItemDecoration(5);
        mRecyclerView.addItemDecoration(decoration);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setHasFixedSize(true);
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
                StaggeredGridLayoutManager manager = (StaggeredGridLayoutManager) recyclerView.getLayoutManager();
                int[] lastVisiblePositions = manager.findLastVisibleItemPositions(new int[manager.getSpanCount()]);
                int lastVisiblePos = getMaxElem(lastVisiblePositions);
                int totalItemCount = manager.getItemCount();
                if (lastVisiblePos >= (totalItemCount - 6) && dy > 0) {
                    //加载更多功能的代码
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

    private int getMaxElem(int[] arr) {
        int size = arr.length;
        int maxVal = Integer.MIN_VALUE;
        for (int i = 0; i < size; i++) {
            if (arr[i] > maxVal)
                maxVal = arr[i];
        }
        return maxVal;
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
        if (isFirst) {
            mList.addAll(list);
            isFirst = false;
        }
        if (mList != null && mList.size() > 0) {
            if (mAdapter == null) {
                mAdapter = new WelfareAdapter(mList, getActivity());
                mRecyclerView.setAdapter(mAdapter);
                mAdapter.setOnItemClickListener(new WelfareAdapter.OnRecyclerViewItemClickListener() {
                    @Override
                    public <T> void onItemClick(View view, int pos) {
                        Intent intent = new Intent(getActivity(), ImageDetailActivity.class);
                        intent.putExtra(ImageDetailActivity.URL, mList.get(pos).getUrl());
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
        WelfareFragment fragment = new WelfareFragment();
        Bundle bundle = new Bundle();
        bundle.putString(WELFARE_TYPE, type);
        fragment.setArguments(bundle);
        return fragment;
    }
}
