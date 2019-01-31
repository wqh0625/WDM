package com.bw.movie.core.dao;

import android.content.Context;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

import com.bw.movie.bean.UserInfoBean;

/**
 * 作者: Wang on 2019/1/24 13:35
 * 寄语：加油！相信自己可以！！！
 */


public class DbManager {


    private Dao<UserInfoBean, String> dao;

    public DbManager(Context context) throws SQLException {
        MyHelpter myHelpter = new MyHelpter(context);
        dao = myHelpter.getDao(UserInfoBean.class);
    }

    public Dao<UserInfoBean, String> getUserDao() {
        return dao;
    }

}
