package com.bw.movie.core.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import com.bw.movie.bean.LoginData;
import com.bw.movie.bean.UserInfoBean;

/**
 * 作者: Wang on 2019/1/24 13:21
 * 寄语：加油！相信自己可以！！！
 */


public class MyHelpter extends OrmLiteSqliteOpenHelper {
    public MyHelpter(Context context) {
        super(context, "movie", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            // 建表
            TableUtils.createTable(connectionSource, UserInfoBean.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

    }
}
