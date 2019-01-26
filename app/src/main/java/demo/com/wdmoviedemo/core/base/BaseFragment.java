package demo.com.wdmoviedemo.core.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

import java.sql.SQLException;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import demo.com.wdmoviedemo.bean.UserInfoBean;
import demo.com.wdmoviedemo.core.dao.DbManager;
import demo.com.wdmoviedemo.core.utils.MyApp;

/**
 * 作者: Wang on 2019/1/24 13:41
 * 寄语：加油！相信自己可以！！！
 */


public abstract class BaseFragment extends Fragment {

    private DbManager dbManager;
    public UserInfoBean userInfoBean;
    private Unbinder unbinder;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        try {
            dbManager = new DbManager(getContext());
            List<UserInfoBean> student = dbManager.getStudent();
            userInfoBean = new UserInfoBean();

            if (student != null && student.size() > 0) {
                userInfoBean = student.get(0);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        View view = inflater.inflate(getLayoutId(), container, false);
        unbinder = ButterKnife.bind(this, view);
        initView(view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    protected abstract int getLayoutId();

    protected abstract void initView(View view);

}
