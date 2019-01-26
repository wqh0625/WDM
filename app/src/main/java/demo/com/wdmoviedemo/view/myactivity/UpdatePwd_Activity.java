package demo.com.wdmoviedemo.view.myactivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;

import java.sql.SQLException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import demo.com.wdmoviedemo.bean.Result;
import demo.com.wdmoviedemo.core.base.BaseActivity;
import demo.com.wdmoviedemo.core.dao.DbManager;
import demo.com.wdmoviedemo.core.exception.ApiException;
import demo.com.wdmoviedemo.core.interfase.DataCall;
import demo.com.wdmoviedemo.core.utils.EncryptUtil;
import demo.com.wdmoviedemo.core.utils.JavaUtils;
import demo.com.wdmoviedemo.presenter.ModifyUserPwdPresenter;
import demo.com.wdmoviedemo.view.LoginActivity;

/**
 * 作者: Wang on 2019/1/25 10:35
 * 寄语：加油！相信自己可以！！！
 */


public class UpdatePwd_Activity extends BaseActivity {
    @BindView(R.id.pwd_01)
    EditText pwd1;
    @BindView(R.id.pwd_02)
    EditText pwd2;
    @BindView(R.id.te_number)
    EditText t;
    private ModifyUserPwdPresenter modifyUserPwdPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updatepwd);
        ButterKnife.bind(this);


        modifyUserPwdPresenter = new ModifyUserPwdPresenter(new modify());
    }

    class modify implements DataCall<Result> {
        @Override
        public void success(Result data) {
            Toast.makeText(UpdatePwd_Activity.this, "" + data.getMessage(), Toast.LENGTH_SHORT).show();
            if (data.getStatus().equals("0000")) {
                DbManager dbManager = null;
                try {
                    dbManager = new DbManager(UpdatePwd_Activity.this);

                    dbManager.deleteStudentByS(userInfoBean);
                    // 删除用户
                    SharedPreferences sp0123 = getSharedPreferences("sp0123", Context.MODE_PRIVATE);
                    sp0123.edit().clear().commit();
                    Intent intent = new Intent(UpdatePwd_Activity.this, LoginActivity.class);
                    // 清空当前栈 ，并且创建新的栈
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    // 跳转
                    startActivity(intent);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void fail(ApiException a) {
        }
    }

    @OnClick(R.id.upw_btn)
    void on() {
        if (pwd1.getText().toString().equals(pwd2.getText().toString())) {
            boolean password = JavaUtils.isPassword(pwd2.getText().toString());
            if (password) {
                modifyUserPwdPresenter.requestNet(userInfoBean.getUserId(), userInfoBean.getSessionId(), EncryptUtil.encrypt(t.getText().toString()), EncryptUtil.encrypt(pwd2.getText().toString()), EncryptUtil.encrypt(pwd2.getText().toString()));
            } else {
                Toast.makeText(this, "密码格式不正确", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @OnClick(R.id.back_image_upwd)
    void ba(){
        finish();
        overridePendingTransition(R.anim.ac_in, R.anim.ac_out);
    }
//    public static void main(String[] args) {
//        new Runnable() {
//            @Override
//            public void run() {
//                System.out.println(Thread.currentThread().getName());
//            }
//        }.run();
//        new Thread(){
//            @Override
//            public void run() {
//                System.out.println(Thread.currentThread().getName());
//            }
//        }.start();
//    }

}
