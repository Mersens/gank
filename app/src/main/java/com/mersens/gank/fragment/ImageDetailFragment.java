package com.mersens.gank.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.mersens.gank.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by Mersens on 2016/11/9.
 */

public class ImageDetailFragment extends BaseFragment {
    public static final String URL = "URL";
    @BindView(R.id.photoView)
    PhotoView photoView;
    private String url;
    private boolean isPrepared;
    private PhotoViewAttacher photoViewAttacher;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_image_details, null);
        ButterKnife.bind(this, view);
        url = getArguments().getString(URL);
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
        init();
    }

    @Override
    public void init() {
        Glide.with(getActivity()).load(url).into(photoView);
        photoViewAttacher = new PhotoViewAttacher(photoView);
    }

    public static Fragment getInstance(String url) {
        ImageDetailFragment fragment = new ImageDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString(URL, url);
        fragment.setArguments(bundle);
        return fragment;
    }

}
