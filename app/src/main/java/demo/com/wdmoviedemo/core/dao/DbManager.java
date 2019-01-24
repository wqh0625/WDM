package demo.com.wdmoviedemo.core.dao;

import android.content.Context;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

import demo.com.wdmoviedemo.bean.UserInfoBean;

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

    // 创建数据
    public void insertStudent(UserInfoBean student) throws SQLException {
        //在数据库中创建一条记录，作用与SQLiteDatabase.insert一样
        dao.createOrUpdate(student);
    }

    /**
     * 查询数据
     * @return
     * @throws SQLException
     */
    public List<UserInfoBean> getStudent() throws SQLException {
        List<UserInfoBean> list = dao.queryForAll();
        return list;
    }

    /**
     * 删除数据
     * @throws SQLException
     */
    public int  deleteStudent(int id) throws SQLException {
        //只看id
        return dao.deleteById(String.valueOf(id));
    }


}
