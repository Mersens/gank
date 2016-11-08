package com.mersens.gank.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.mersens.gank.fragment.AndroidFragment;
import com.mersens.gank.fragment.FrontFragment;
import com.mersens.gank.fragment.IOSFragment;
import com.mersens.gank.fragment.ResourcesFragment;
import com.mersens.gank.fragment.VideoFragment;
import com.mersens.gank.fragment.WelfareFragment;

import java.util.List;

/**
 * Created by Mersens on 2016/11/8.
 */

public class MyFragmentPagerAdapter extends FragmentStatePagerAdapter {
    private List<String> tabTitles;

    public MyFragmentPagerAdapter(FragmentManager fm, List<String> tabTitles) {
        super(fm);
        this.tabTitles = tabTitles;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = AndroidFragment.getInstance(tabTitles.get(position));
                break;
            case 1:
                fragment = IOSFragment.getInstance();
                break;
            case 2:
                fragment = WelfareFragment.getInstance();
                break;
            case 3:
                fragment = FrontFragment.getInstance();
                break;
            case 4:
                fragment = VideoFragment.getInstance();
                break;
            case 5:
                fragment = ResourcesFragment.getInstance();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return tabTitles == null ? 0 : tabTitles.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {

        return tabTitles.get(position % tabTitles.size());
    }
}
