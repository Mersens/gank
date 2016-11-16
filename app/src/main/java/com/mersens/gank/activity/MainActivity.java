package com.mersens.gank.activity;

import android.animation.Animator;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.afollestad.materialdialogs.color.ColorChooserDialog;
import com.bumptech.glide.Glide;
import com.mersens.gank.R;
import com.mersens.gank.adapter.MyFragmentPagerAdapter;
import com.mersens.gank.app.App;
import com.mersens.gank.app.Constans;
import com.mersens.gank.db.GankDao;
import com.mersens.gank.db.GankDaoImpl;
import com.mersens.gank.db.SharePreferenceUtils;
import com.mersens.gank.entity.SkinChangeEvent;
import com.mersens.gank.entity.User;
import com.mersens.gank.fragment.AboutFragment;
import com.mersens.gank.mvp.presenter.IUserInfoPresenter;
import com.mersens.gank.mvp.view.IUserInfoView;
import com.mersens.gank.utils.ColorUiUtil;
import com.mersens.gank.utils.PreUtils;
import com.mersens.gank.utils.Theme;
import com.mersens.gank.utils.ThemeUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener,IUserInfoView ,ColorChooserDialog.ColorCallback{
    private List<String> tabTitles;
    private TabLayout mTabLayout;
    private MyFragmentPagerAdapter mAdapter;
    private ViewPager mViewPager;
    private CircleImageView img_user;
    private TextView tv_name;
    private TextView tv_email;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private GankDao dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    public void init(){
         toolbar = (Toolbar) findViewById(R.id.toolbar);
         setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setCheckedItem(R.id.nav_front);
        navigationView.setNavigationItemSelectedListener(this);
        View view=navigationView.getHeaderView(0);
        img_user=(CircleImageView) view.findViewById(R.id.img_user);
        tv_name=(TextView) view.findViewById(R.id.tv_name);
        tv_email=(TextView) view.findViewById(R.id.tv_email);

        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        initDatas();
    }


    public void initDatas(){
        dao=new GankDaoImpl(MainActivity.this);
        IUserInfoPresenter presenter=new IUserInfoPresenter(this);
        presenter.getInfo();
        tabTitles = new ArrayList<String>();
        tabTitles.add("Android");
        tabTitles.add("iOS");
        tabTitles.add("福利");
        tabTitles.add("前端");
        tabTitles.add("拓展资源");
        mTabLayout.addTab(mTabLayout.newTab().setText(tabTitles.get(0)));
        mTabLayout.addTab(mTabLayout.newTab().setText(tabTitles.get(1)));
        mTabLayout.addTab(mTabLayout.newTab().setText(tabTitles.get(2)));
        mTabLayout.addTab(mTabLayout.newTab().setText(tabTitles.get(3)));
        mTabLayout.addTab(mTabLayout.newTab().setText(tabTitles.get(4)));
        mAdapter=new MyFragmentPagerAdapter(getSupportFragmentManager(),tabTitles);
        mViewPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                switch (position){
                    case 0:
                        navigationView.setCheckedItem(R.id.nav_android);
                        break;
                    case 1:
                        navigationView.setCheckedItem(R.id.nav_ios);
                        break;
                    case 2:
                        navigationView.setCheckedItem(R.id.nav_welfare);
                        break;
                    case 3:
                        navigationView.setCheckedItem(R.id.nav_front);
                        break;
                    case 4:
                        navigationView.setCheckedItem(R.id.nav_resource);
                        break;
                }

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.nav_android:
                mViewPager.setCurrentItem(0);
                break;
            case R.id.nav_ios:
                mViewPager.setCurrentItem(1);
                break;
            case R.id.nav_welfare:
                mViewPager.setCurrentItem(2);
                break;
            case R.id.nav_front:
                mViewPager.setCurrentItem(3);
                break;
            case R.id.nav_resource:
                mViewPager.setCurrentItem(4);
                break;
            case R.id.nav_about:
                AboutFragment fragment=new AboutFragment();
                fragment.show(getSupportFragmentManager(),"关于");
                break;
            case R.id.nav_style:
                new ColorChooserDialog.Builder(this, R.string.theme)
                        .customColors(R.array.colors, null)
                        .doneButton(R.string.done)
                        .cancelButton(R.string.cancel)
                        .allowUserColorInput(false)
                        .allowUserColorInputAlpha(false)
                        .show();
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public <T> void onSuccess(T t) {
        User user=(User)t;
        tv_name.setText(user.getLogin());
        tv_email.setText(user.getEmail());
        Glide.with(MainActivity.this).load(user.getAvatar_url()).into(img_user);
        saveToDb(user);
    }

    //用户信息保存数据库
    public  void saveToDb( User user){
        SharePreferenceUtils.getInstance(App.getInstance()).setUserId(user.getId());
        if(dao.findUserIsExist(user.getId()))
            dao.updateUserInfo(user,user.getId());
        else
            dao.addUserInfo(user);
    }


    @Override
    public String getUserName() {
        return Constans.USER;
    }

    @Override
    public void onError() {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            confirmExit();
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    public void confirmExit(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示").setMessage("是否退出软件？").setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                App.getInstance().exit();
                finish();

            }
        }).setNegativeButton("取消",null).create().show();
    }

    @Override
    public void onColorSelection(@NonNull ColorChooserDialog dialog, @ColorInt int selectedColor) {
        if (selectedColor == ThemeUtils.getThemeColor(this, R.attr.colorPrimary))
            return;
            EventBus.getDefault().post(new SkinChangeEvent());

        if (selectedColor == getResources().getColor(R.color.colorBluePrimary)) {
            setTheme(R.style.BlueTheme);
            PreUtils.setCurrentTheme(this, Theme.Blue);


        } else if (selectedColor == getResources().getColor(R.color.colorRedPrimary)) {
            setTheme(R.style.RedTheme);
            PreUtils.setCurrentTheme(this, Theme.Red);


        } else if (selectedColor == getResources().getColor(R.color.colorBrownPrimary)) {
            setTheme(R.style.BrownTheme);
            PreUtils.setCurrentTheme(this, Theme.Brown);


        } else if (selectedColor == getResources().getColor(R.color.colorGreenPrimary)) {
            setTheme(R.style.GreenTheme);


        } else if (selectedColor == getResources().getColor(R.color.colorPurplePrimary)) {
            setTheme(R.style.PurpleTheme);
            PreUtils.setCurrentTheme(this, Theme.Purple);

        } else if (selectedColor == getResources().getColor(R.color.colorTealPrimary)) {
            setTheme(R.style.TealTheme);
            PreUtils.setCurrentTheme(this, Theme.Teal);

        } else if (selectedColor == getResources().getColor(R.color.colorPinkPrimary)) {
            setTheme(R.style.PinkTheme);
            PreUtils.setCurrentTheme(this, Theme.Pink);

        } else if (selectedColor == getResources().getColor(R.color.colorDeepPurplePrimary)) {
            setTheme(R.style.DeepPurpleTheme);
            PreUtils.setCurrentTheme(this, Theme.DeepPurple);

        } else if (selectedColor == getResources().getColor(R.color.colorOrangePrimary)) {
            setTheme(R.style.OrangeTheme);
            PreUtils.setCurrentTheme(this, Theme.Orange);

        } else if (selectedColor == getResources().getColor(R.color.colorIndigoPrimary)) {
            setTheme(R.style.IndigoTheme);
            PreUtils.setCurrentTheme(this, Theme.Indigo);

        } else if (selectedColor == getResources().getColor(R.color.colorLightGreenPrimary)) {
            setTheme(R.style.LightGreenTheme);
            PreUtils.setCurrentTheme(this, Theme.LightGreen);

        } else if (selectedColor == getResources().getColor(R.color.colorDeepOrangePrimary)) {
            setTheme(R.style.DeepOrangeTheme);
            PreUtils.setCurrentTheme(this, Theme.DeepOrange);

        } else if (selectedColor == getResources().getColor(R.color.colorLimePrimary)) {
            setTheme(R.style.LimeTheme);
            PreUtils.setCurrentTheme(this, Theme.Lime);

        } else if (selectedColor == getResources().getColor(R.color.colorBlueGreyPrimary)) {
            setTheme(R.style.BlueGreyTheme);
            PreUtils.setCurrentTheme(this, Theme.BlueGrey);

        } else if (selectedColor == getResources().getColor(R.color.colorCyanPrimary)) {
            setTheme(R.style.CyanTheme);
            PreUtils.setCurrentTheme(this, Theme.Cyan);

        }
        final View rootView = getWindow().getDecorView();
        rootView.setDrawingCacheEnabled(true);
        rootView.buildDrawingCache(true);

        final Bitmap localBitmap = Bitmap.createBitmap(rootView.getDrawingCache());
        rootView.setDrawingCacheEnabled(false);
        if (null != localBitmap && rootView instanceof ViewGroup) {
            final View tmpView = new View(getApplicationContext());
            tmpView.setBackgroundDrawable(new BitmapDrawable(getResources(), localBitmap));
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            ((ViewGroup) rootView).addView(tmpView, params);
            tmpView.animate().alpha(0).setDuration(400).setListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    ColorUiUtil.changeTheme(rootView, getTheme());
                    System.gc();
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    ((ViewGroup) rootView).removeView(tmpView);
                    localBitmap.recycle();
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            }).start();
        }
    }
}
