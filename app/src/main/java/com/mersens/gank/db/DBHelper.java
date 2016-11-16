package com.mersens.gank.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DBHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String NAME = "GANK.db";
    private static final String SQL_LOGIN_HISTORY_CREAT = "create table ganktb(_id integer primary key autoincrement,userid text,login text,avatar text,email text,name text)";
    private static final String SQL_LOGIN_HISTORY_DROP = "drop table if exists ganktb";

    public static DBHelper helper = null;
    public static Context mContext;

    public static DBHelper getInstance(Context context) {
        if (helper == null) {
            synchronized (DBHelper.class) {
                if (helper == null) {
                    helper = new DBHelper(context.getApplicationContext());
                }
            }
        }
        mContext = context;
        return helper;
    }

    private DBHelper(Context context) {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_LOGIN_HISTORY_CREAT);
    }

    /**
     * 当数据库更新时，调用该方法
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_LOGIN_HISTORY_DROP);
        db.execSQL(SQL_LOGIN_HISTORY_CREAT);

    }
}
