package com.mersens.gank.activity;

import android.support.v4.app.Fragment;
import android.view.MenuItem;

import com.mersens.gank.fragment.WebFragment;

/**
 * Created by Mersens on 2016/11/10.
 */

public class WebActivity extends SingleBaseActivity {
    @Override
    public Fragment creatFragment() {
        return WebFragment.getInstance(url);
    }

    @Override
    public String setActionBarTitle() {
        return "";
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
