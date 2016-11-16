package com.mersens.gank.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mersens.gank.entity.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mersens on 2016/1/6.
 */
public class GankDaoImpl implements GankDao {
    private DBHelper helper;
    private Context context;

    public GankDaoImpl(Context context) {
        helper = DBHelper.getInstance(context);
        this.context = context;
    }

    @Override
    public List<User> findAllUser() {
        List<User> list = new ArrayList<User>();
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery(
                "select * from ganktb",
                null);
        while (cursor.moveToNext()) {
            User user = new User();
            String userid = cursor.getString(cursor.getColumnIndex("userid"));
            user.setId(userid);
            String avatar = cursor.getString(cursor.getColumnIndex("avatar"));
            user.setAvatar_url(avatar);
            String login = cursor.getString(cursor.getColumnIndex("login"));
            user.setLogin(login);
            String email = cursor.getString(cursor.getColumnIndex("email"));
            user.setEmail(email);
            String name = cursor.getString(cursor.getColumnIndex("name"));
            user.setName(name);
            list.add(user);
        }
        cursor.close();
        db.close();
        return list;
    }

    @Override
    public User findUserInfoById(String userid) {
        List<User> list = new ArrayList<User>();
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from ganktb where userid=?",
                new String[]{userid});
        while (cursor.moveToNext()) {
            User user = new User();
            String id = cursor.getString(cursor.getColumnIndex("userid"));
            user.setId(id);
            String avatar = cursor.getString(cursor.getColumnIndex("avatar"));
            user.setAvatar_url(avatar);
            String login = cursor.getString(cursor.getColumnIndex("login"));
            user.setLogin(login);
            String email = cursor.getString(cursor.getColumnIndex("email"));
            user.setEmail(email);
            String name = cursor.getString(cursor.getColumnIndex("name"));
            user.setName(name);
            list.add(user);
        }
        cursor.close();
        db.close();
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }

    }

    @Override
    public void delUserInfoById(String userid) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("delete from ganktb where userid=?",
                new Object[]{userid});
        db.close();
    }

    @Override
    public boolean findUserIsExist(String userid) {
        boolean flag = false;
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery(
                "select * from ganktb where userid=? ",
                new String[]{userid});
        while (cursor.moveToNext()) {
            flag = true;
        }
        cursor.close();
        db.close();
        return flag;
    }

    @Override
    public void addUserInfo(User user) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL(
                "insert into ganktb(userid,login,avatar,email,name) values(?,?,?,?,?,?,?,?,?,?,?,?)",
                new Object[]{user.getId(), user.getLogin(),
                        user.getAvatar_url(), user.getEmail(), user.getName()});
        db.close();
    }


    @Override
    public void updateUserInfo(User user, String userid) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("UPDATE ganktb SET userid=?,login=?,avatar=?,email=?,name=?  where userid=?", new Object[]{
                user.getId(), user.getLogin(),
                user.getAvatar_url(), user.getEmail(), user.getName(), userid});
        db.close();
    }


}
