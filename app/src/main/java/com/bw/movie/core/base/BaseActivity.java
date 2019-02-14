package com.bw.movie.core.base;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.bw.movie.R;
import com.bw.movie.view.LoginActivity;
import com.j256.ormlite.dao.Dao;
import com.umeng.analytics.MobclickAgent;

import java.sql.SQLException;
import java.util.List;

import butterknife.ButterKnife;
import com.bw.movie.bean.UserInfoBean;
import com.bw.movie.core.dao.DbManager;
import com.bw.movie.core.utils.MyApp;

/**
 * 作者: Wang on 2019/1/24 13:41
 * 寄语：加油！相信自己可以！！！
 */


public class BaseActivity extends AppCompatActivity {
    public final static int PHOTO = 0;// 相册选取
    public final static int CAMERA = 1;// 拍照

    public List<UserInfoBean> student;
    public UserInfoBean userInfoBean;
    private LocationClient mLocationClient = null;
    private Dao<UserInfoBean, String> userDao;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            userDao = new DbManager(this).getUserDao();
            student = userDao.queryForAll();
            if (student != null && student.size() > 0) {
                userInfoBean = student.get(0);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public String getFilePath(String fileName, int requestCode, Intent data) {
        if (requestCode == CAMERA) {
            return fileName;
        } else if (requestCode == PHOTO) {
            Uri uri = data.getData();
            String[] proj = {MediaStore.Images.Media.DATA};
            Cursor actualimagecursor = managedQuery(uri, proj, null, null, null);
            int actual_image_column_index = actualimagecursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            actualimagecursor.moveToFirst();
            String img_path = actualimagecursor
                    .getString(actual_image_column_index);
            // 4.0以上平台会自动关闭cursor,所以加上版本判断,OK
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                actualimagecursor.close();
            }
            return img_path;
        }
        return null;
    }

    public void s(){
        try {
            userDao.delete(userInfoBean);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Intent intent = new Intent(MyApp.getContext(), LoginActivity.class);
        // 跳转
        startActivity(intent);
        overridePendingTransition(R.anim.ac_in, R.anim.ac_out);
    }
}
