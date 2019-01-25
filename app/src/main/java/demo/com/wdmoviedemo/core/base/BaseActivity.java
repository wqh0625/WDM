package demo.com.wdmoviedemo.core.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import demo.com.wdmoviedemo.bean.UserInfoBean;
import demo.com.wdmoviedemo.core.dao.DbManager;

/**
 * 作者: Wang on 2019/1/24 13:41
 * 寄语：加油！相信自己可以！！！
 */


public class BaseActivity extends AppCompatActivity {

    private DbManager dbManager;
    public List<UserInfoBean> student;
    public UserInfoBean userInfoBean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            dbManager = new DbManager(this);
            student = dbManager.getStudent();
            userInfoBean = new UserInfoBean();

            for (int i = student.size()-1; i >= 0; i--) {
                if (student.get(i).getStats() == 100) {
                    userInfoBean = student.get(i);
                    return;
                }
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

    //// 查询数据
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
