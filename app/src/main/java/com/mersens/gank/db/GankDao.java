package com.mersens.gank.db;


import com.mersens.gank.entity.User;

import java.util.List;

/**
 * Created by Mersens on 2016/11/6.
 */
public interface GankDao {

    //查询所有用户信息
    public List<User> findAllUser();

    //根据id查找用户信息
    public User findUserInfoById(String userid);

    //删除用户信息
    public void delUserInfoById(String userid);

    //判断用户是否存在
    public boolean findUserIsExist(String userid);

    //添加用户信息
    public void addUserInfo(User user);

    //修改用户信息
    public void updateUserInfo(User user, String userid);
}
