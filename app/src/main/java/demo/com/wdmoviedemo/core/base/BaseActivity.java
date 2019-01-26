package demo.com.wdmoviedemo.core.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

import java.sql.SQLException;
import java.util.List;

import demo.com.wdmoviedemo.bean.UserInfoBean;
import demo.com.wdmoviedemo.core.dao.DbManager;
import demo.com.wdmoviedemo.core.utils.MyApp;

/**
 * 作者: Wang on 2019/1/24 13:41
 * 寄语：加油！相信自己可以！！！
 */


public class BaseActivity extends AppCompatActivity {

    private DbManager dbManager;
    public List<UserInfoBean> student;
    public UserInfoBean userInfoBean;
    private LocationClient mLocationClient = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            dbManager = new DbManager(this);
            student = dbManager.getStudent();
            userInfoBean = new UserInfoBean();

            if (student != null && student.size() > 0) {
                userInfoBean = student.get(0);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 删除数据
    public int deleteUser(int id) throws SQLException {
        return dbManager.deleteStudentByI(id);
    }

    // 添加数据
    public void addUser(UserInfoBean userInfoBean) throws SQLException {
        dbManager.insertStudent(userInfoBean);
        student = dbManager.getStudent();
    }

    //  查询数据
    public List<UserInfoBean> query() throws SQLException {
        List<UserInfoBean> student = dbManager.getStudent();
        this.student = student;
        return this.student;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

}
