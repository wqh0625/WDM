package demo.com.wdmoviedemo.core.base;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
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
    public final static int PHOTO = 0;// 相册选取
    public final static int CAMERA = 1;// 拍照
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
    public String getFilePath(String fileName, int requestCode, Intent data) {
        if (requestCode == CAMERA) {
            return fileName;
        } else if (requestCode == PHOTO) {
            Uri uri = data.getData();
            String[] proj = {MediaStore.Images.Media.DATA};
            Cursor actualimagecursor = getActivity().managedQuery(uri, proj, null, null, null);
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
}
