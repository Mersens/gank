package com.mersens.gank.activity;

import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.mersens.gank.R;
import com.mersens.gank.fragment.ImageDetailFragment;
import com.mersens.gank.mvp.presenter.IDownloadPresenter;
import com.mersens.gank.mvp.view.IDownloadView;

/**
 * Created by Mersens on 2016/11/9.
 */

public class ImageDetailActivity extends SingleBaseActivity implements IDownloadView {

    @Override
    public Fragment creatFragment() {
        return ImageDetailFragment.getInstance(url);
    }

    @Override
    public String setActionBarTitle() {
        return "";
    }

    @Override
    public void onDownloadSuccess() {
        Toast.makeText(this, "保存成功！", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onDownloadError() {
        Toast.makeText(this, "保存失败！", Toast.LENGTH_SHORT).show();
    }

    @Override
    public String getUrl() {
        return url;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        if(id ==R.id.action_save){
            IDownloadPresenter presenter=new IDownloadPresenter(this);
            presenter.download();
            return true;

        }
        return super.onOptionsItemSelected(item);
    }
}
