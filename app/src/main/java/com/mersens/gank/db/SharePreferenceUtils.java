package com.mersens.gank.db;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author Mersens
 * @title SharePreferenceUtil
 * @description:SharePreference工具类，数据存储
 * @time 2016年11月6日
 */
public class SharePreferenceUtils {
    private static SharePreferenceUtils sp;
    private static SharedPreferences mSharedPreferences;
    private static SharedPreferences.Editor editor;
    private static final String PREFERENCE_NAME = "_sharedinfo";
    private static final String USER_ID = "user_id";
    private SharePreferenceUtils() {

    }
    public static synchronized SharePreferenceUtils getInstance(Context context) {
        if (sp == null) {
            sp = new SharePreferenceUtils();
            mSharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
            editor = mSharedPreferences.edit();
        }
        return sp;
    }

    public String getUserId() {
        return mSharedPreferences.getString(USER_ID, "");
    }

    public void setUserId(String userid) {
        editor.putString(USER_ID, userid);
        editor.commit();
    }
    //清除数据
    public void clearAllData() {
        editor.clear().commit();
    }



}
