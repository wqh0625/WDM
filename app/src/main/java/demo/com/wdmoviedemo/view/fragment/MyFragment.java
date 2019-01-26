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
import com.facebook.drawee.view.SimpleDraweeView;

import java.sql.SQLException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import demo.com.wdmoviedemo.bean.UserInfoBean;
import demo.com.wdmoviedemo.core.base.BaseFragment;
import demo.com.wdmoviedemo.core.dao.DbManager;
import demo.com.wdmoviedemo.core.utils.MyApp;
import demo.com.wdmoviedemo.view.HomeActivity;
import demo.com.wdmoviedemo.view.LoginActivity;
import demo.com.wdmoviedemo.view.myactivity.My_Attention_Activity;
import demo.com.wdmoviedemo.view.myactivity.My_Feedback_Activity;
import demo.com.wdmoviedemo.view.myactivity.My_Messiage_Activity;
import demo.com.wdmoviedemo.view.myactivity.My_Rccord_Activity;

import static demo.com.wdmoviedemo.core.utils.MyApp.getContext;


public class MyFragment extends BaseFragment {

    @BindView(R.id.my_tv_nickName)
    TextView nickNameTv;
    @BindView(R.id.my_image_icom)
    SimpleDraweeView icon;

    @Override
    public void initView(View view) {
        String headPic = userInfoBean.getHeadPic();
        String nickName = userInfoBean.getNickName();
        if (headPic == null || headPic.length() < 0 || headPic == "") {
            icon.setImageResource(R.drawable.my_icon);
            nickNameTv.setText("未登录");
        } else {
            icon.setImageURI(Uri.parse(headPic));
            nickNameTv.setText(nickName);
        }


    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mys;
    }

    @OnClick({R.id.mRb_messiage, R.id.mRb_aixin, R.id.mRb_logout, R.id.mRb_rccord, R.id.mRb_vsersion, R.id.mRb_feedback})
    public void onck(View v) {
        if (v.getId() == R.id.mRb_messiage) {
            if (userInfoBean.getUserId() == 0) {
                DbManager dbManager = null;
                try {
                    dbManager = new DbManager(getContext());
                    int i = dbManager.deleteStudentByS(userInfoBean);
                    Toast.makeText(getContext(), "" + i, Toast.LENGTH_SHORT).show();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                Intent intent = new Intent(MyApp.getContext(), LoginActivity.class);
                // 跳转
                startActivity(intent);
                getActivity(). overridePendingTransition(R.anim.ac_in, R.anim.ac_out);
            } else {
                startActivity(new Intent(getActivity(), My_Messiage_Activity.class));
            }
        } else if (v.getId() == R.id.mRb_aixin) {
            if (userInfoBean.getUserId() == 0) {
                DbManager dbManager = null;
                try {
                    dbManager = new DbManager(getContext());
                    int i = dbManager.deleteStudentByS(userInfoBean);
                    Toast.makeText(getContext(), "" + i, Toast.LENGTH_SHORT).show();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                Intent intent = new Intent(MyApp.getContext(), LoginActivity.class);
                // 跳转
                startActivity(intent);
                getActivity(). overridePendingTransition(R.anim.ac_in, R.anim.ac_out);

            } else {
                // 关注
                startActivity(new Intent(getActivity(), My_Attention_Activity.class));
            }
        } else if (v.getId() == R.id.mRb_vsersion) {
            // 版本
        } else if (v.getId() == R.id.mRb_rccord) {
            if (userInfoBean.getUserId() == 0) {
                DbManager dbManager = null;
                try {
                    dbManager = new DbManager(getContext());
                    int i = dbManager.deleteStudentByS(userInfoBean);
                    Toast.makeText(getContext(), "" + i, Toast.LENGTH_SHORT).show();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                Intent intent = new Intent(MyApp.getContext(), LoginActivity.class);
                // 跳转
                startActivity(intent);
                getActivity(). overridePendingTransition(R.anim.ac_in, R.anim.ac_out);
            } else {
                // 购票记录
                startActivity(new Intent(getActivity(), My_Rccord_Activity.class));
                getActivity(). overridePendingTransition(R.anim.ac_in, R.anim.ac_out);
            }
        } else if (v.getId() == R.id.mRb_feedback) {
            if (userInfoBean.getUserId() == 0) {
                DbManager dbManager = null;
                try {
                    dbManager = new DbManager(getContext());
                    int i = dbManager.deleteStudentByS(userInfoBean);
                    Toast.makeText(getContext(), "" + i, Toast.LENGTH_SHORT).show();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                Intent intent = new Intent(MyApp.getContext(), LoginActivity.class);
                // 跳转
                startActivity(intent);
                getActivity(). overridePendingTransition(R.anim.ac_in, R.anim.ac_out);
            } else { // 意见反馈
                startActivity(new Intent(getActivity(), My_Feedback_Activity.class));
                getActivity(). overridePendingTransition(R.anim.ac_in, R.anim.ac_out);
            }
        } else if (v.getId() == R.id.mRb_logout) {
            // 退出登录 通过AlertDialog.Builder这个类来实例化我们的一个AlertDialog的对象
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            //    设置Title的内容
            builder.setTitle("温馨提示");
            //    设置Content来显示一个信息
            builder.setMessage("确定退出登录吗？");
            //    设置一个PositiveButton
            builder.setPositiveButton("确认退出", new DialogInterface.OnClickListener() {
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
                    getActivity(). overridePendingTransition(R.anim.ac_in, R.anim.ac_out);
                }
            });
            //    设置一个NegativeButton
            builder.setNegativeButton("暂不退出", null);
            //    显示出该对话框
            builder.show();
        } else if (v.getId() == R.id.my_btn_qd) {
            if (userInfoBean.getUserId() == 0) {
                DbManager dbManager = null;
                try {
                    dbManager = new DbManager(getContext());
                    int i = dbManager.deleteStudentByS(userInfoBean);
                    Toast.makeText(getContext(), "" + i, Toast.LENGTH_SHORT).show();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                Intent intent = new Intent(MyApp.getContext(), LoginActivity.class);
                // 跳转
                startActivity(intent);
                getActivity(). overridePendingTransition(R.anim.ac_in, R.anim.ac_out);
            } else {

            }

        }
    }
}
