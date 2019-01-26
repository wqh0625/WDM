package demo.com.wdmoviedemo.view.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;

import java.sql.SQLException;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import demo.com.wdmoviedemo.bean.UserInfoBean;
import demo.com.wdmoviedemo.core.base.BaseFragment;
import demo.com.wdmoviedemo.core.dao.DbManager;
import demo.com.wdmoviedemo.view.LoginActivity;
import demo.com.wdmoviedemo.view.myactivity.My_Attention_Activity;
import demo.com.wdmoviedemo.view.myactivity.My_Feedback_Activity;
import demo.com.wdmoviedemo.view.myactivity.My_Messiage_Activity;
import demo.com.wdmoviedemo.view.myactivity.My_Rccord_Activity;


public class MyFragment extends BaseFragment {

    private Unbinder unbinder;

    @Override
    public void initView(View view) {
        if (userInfoBean != null) {
//            int userId = userInfoBean.getUserId();
//            Toast.makeText(getActivity(), "咳咳 " + userId, Toast.LENGTH_SHORT).show();
        }
        unbinder = ButterKnife.bind(getActivity(), view);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mys;
    }

    @OnClick({R.id.mRb_messiage, R.id.mRb_aixin, R.id.mRb_logout, R.id.mRb_rccord, R.id.mRb_vsersion, R.id.mRb_feedback})
    public void onck(View v) {
        if (v.getId() == R.id.mRb_messiage) {
            startActivity(new Intent(getActivity(), My_Messiage_Activity.class));
        } else if (v.getId() == R.id.mRb_aixin) {
            // 关注
            startActivity(new Intent(getActivity(), My_Attention_Activity.class));
        } else if (v.getId() == R.id.mRb_vsersion) {
            // 版本
        } else if (v.getId() == R.id.mRb_rccord) {
            // 购票记录
            startActivity(new Intent(getActivity(), My_Rccord_Activity.class));
        } else if (v.getId() == R.id.mRb_feedback) {
            // 意见反馈
            startActivity(new Intent(getActivity(), My_Feedback_Activity.class));
        } else if (v.getId() == R.id.mRb_logout) {
            // 退出登录 通过AlertDialog.Builder这个类来实例化我们的一个AlertDialog的对象
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            //    设置Title的内容
            builder.setTitle("退出警告！");
            //    设置Content来显示一个信息
            builder.setMessage("确定退出登录吗？");
            //    设置一个PositiveButton
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    DbManager dbManager = null;
                    try {
                        dbManager = new DbManager(getContext());
                        int i = dbManager.deleteStudentByS(userInfoBean);
                        Toast.makeText(getContext(), "" + i, Toast.LENGTH_SHORT).show();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    // 删除用户
                    SharedPreferences sp0123 = getActivity().getSharedPreferences("sp0123", Context.MODE_PRIVATE);
                    sp0123.edit().clear().commit();
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    // 清空当前栈 ，并且创建新的栈
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    // 跳转
                    startActivity(intent);
                }
            });
            //    设置一个NegativeButton
            builder.setNegativeButton("取消", null);
            //    显示出该对话框
            builder.show();
        }
    }
}
