package com.mersens.gank.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;

import com.mersens.gank.R;

/**
 * Created by Mersens on 2016/11/9.
 */

public abstract class SingleBaseActivity extends BaseActivity {
    public static  final String URL="URL";
    protected String url;
    protected Toolbar toolbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_content);
        url=getIntent().getStringExtra(URL);
        init();
    }

    public void init(){
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(setActionBarTitle());
        FragmentManager fm=getSupportFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();
        Fragment fragment=fm.findFragmentById(R.id.fragment_container);
        if(fragment==null){
            fragment=creatFragment();
        }
        ft.replace(R.id.fragment_container,fragment).commit();
    }

    public abstract Fragment creatFragment();
    public abstract String setActionBarTitle();
}
