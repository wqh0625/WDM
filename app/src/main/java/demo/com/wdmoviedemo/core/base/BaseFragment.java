package demo.com.wdmoviedemo.core.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.sql.SQLException;
import java.util.List;

import demo.com.wdmoviedemo.bean.UserInfoBean;
import demo.com.wdmoviedemo.core.dao.DbManager;

/**
 * 作者: Wang on 2019/1/24 13:41
 * 寄语：加油！相信自己可以！！！
 */


public class BaseFragment extends Fragment {

    private DbManager dbManager;
    public List<UserInfoBean> student;
    public UserInfoBean userInfoBean;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        try {
            dbManager = new DbManager(getContext());
            student = dbManager.getStudent();
            userInfoBean = new UserInfoBean();
            query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    // 删除数据
    public int deleteUser(int id) throws SQLException {

        return dbManager.deleteStudentByI(id);
    }

    // 添加数据
    public void addUser(UserInfoBean userInfoBean) throws SQLException {

        dbManager.insertStudent(userInfoBean);
    }

    //// 查询数据
    public void query() throws SQLException {

        for (int i = 0; i < student.size(); i++) {
            if (student.get(i).getStats() == 1) {
                userInfoBean = student.get(i);
                return;
            }
        }
    }

}
