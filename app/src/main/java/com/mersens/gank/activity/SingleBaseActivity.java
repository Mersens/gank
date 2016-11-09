package com.mersens.gank.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.mersens.gank.R;

/**
 * Created by Mersens on 2016/11/9.
 */

public abstract class SingleBaseActivity extends AppCompatActivity {
    public static  final String URL="URL";
    protected String url;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_content);
        url=getIntent().getStringExtra(URL);
        init();
    }

    public void init(){
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

            Toast.makeText(this, "点击保存", Toast.LENGTH_SHORT).show();
            return true;

        }
        return super.onOptionsItemSelected(item);
    }
    public abstract Fragment creatFragment();
    public abstract String setActionBarTitle();
}
