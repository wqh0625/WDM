package demo.com.wdmoviedemo.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;

import java.sql.SQLException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import demo.com.wdmoviedemo.bean.LoginData;
import demo.com.wdmoviedemo.bean.Result;
import demo.com.wdmoviedemo.bean.UserInfoBean;
import demo.com.wdmoviedemo.core.base.BaseActivity;
import demo.com.wdmoviedemo.core.exception.ApiException;
import demo.com.wdmoviedemo.core.interfase.DataCall;
import demo.com.wdmoviedemo.core.utils.EncryptUtil;
import demo.com.wdmoviedemo.presenter.LoginPresenter;

/**
 * 作者: Wang on 2019/1/22 15:00
 * 寄语：加油！相信自己可以！！！
 */


public class LoginActivity extends BaseActivity {

    private Unbinder unBind;

    @BindView(R.id.et_tel_number)
    // 手机号
            EditText tel_number;
    @BindView(R.id.et_tel_pwd)
    // 密码
            EditText tel_pwd;
    @BindView(R.id.eye)
    // 显示密码或隐藏
            ImageView eye;
    @BindView(R.id.cb_reme_login)
    //自动登录
            CheckBox reme_login;
    @BindView(R.id.cb_reme_pwd)
    //记住密码
            CheckBox reme_pwd;


    boolean isHide;
    private LoginPresenter loginPresenter;
    private SharedPreferences sp0123;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        unBind = ButterKnife.bind(this);
        loginPresenter = new LoginPresenter(new login());
        sp0123 = getSharedPreferences("sp0123", MODE_PRIVATE);
        boolean reme_pwd = sp0123.getBoolean("reme_pwd", false);
        boolean reme_login = sp0123.getBoolean("reme_login", false);
        if (reme_pwd) {
            UserInfoBean user = userInfoBean;
            if (user.getStats() == 1) {
            }
            if (user.getPhone() == null) {
                tel_pwd.setText("");
                tel_number.setText("");
//                Toast.makeText(this, "nullllll" + student.size(), Toast.LENGTH_SHORT).show();
                return;
            }
            tel_number.setText(user.getPhone() + "");
            tel_pwd.setText(EncryptUtil.decrypt(user.getPwd()) + "");
            this.reme_pwd.setChecked(true);
            if (reme_login) {
                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                finish();
                return;
            }
            return;

        }
        // 设置密码不可见
        tel_pwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
        isHide = true;

        SharedPreferences.Editor edit = sp0123.edit();
        edit.putBoolean("reme_pwd", false);
        edit.putBoolean("reme_login", false);
        edit.commit();
    }

    @OnClick({R.id.tv_register, R.id.button_login, R.id.eye, R.id.cb_reme_pwd, R.id.cb_reme_login})
    void onclick(View v) {
        switch (v.getId()) {
            case R.id.eye:
                if (isHide) {
                    isHide = false;
                    //设置密码可见
                    tel_pwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    isHide = true;
                    // 不可见
                    tel_pwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                break;
            case R.id.button_login:
                //登录页面
                String w = tel_pwd.getText().toString().trim();
                String pwddd = EncryptUtil.encrypt(w);
                String number = tel_number.getText().toString().trim();
                if (number.length() > 0 && pwddd.length() > 0) {
                    // 记住密码
                    boolean a = reme_pwd.isChecked();
                    SharedPreferences.Editor edit = sp0123.edit();
                    if (a) {
                        edit.putBoolean("reme_pwd", a);
                    }
                    // 自动登录
                    boolean b = reme_login.isChecked();
                    if (b) {
                        edit.putBoolean("reme_login", b);
                    }
                    edit.commit();
                    loginPresenter.requestNet(number, pwddd);
                }
                break;
            case R.id.tv_register:
                // 进入注册页
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                break;
            case R.id.iv_wx:
                // 微信登录

                break;
        }
    }

    class login implements DataCall<Result<LoginData>> {
        @Override
        public void success(Result<LoginData> data) {
            Toast.makeText(LoginActivity.this, "" + data.getMessage(), Toast.LENGTH_SHORT).show();
//            Log.v("登录数据", "" + data.getResult().getUserInfo().toString());
            if (data.getStatus().equals("0000")) {

                UserInfoBean userInfoBean = data.getResult().getUserInfo();
                //设置状态

                String w = tel_pwd.getText().toString().trim();
                String pwddd = EncryptUtil.encrypt(w);
                // 设置密码
                userInfoBean.setPwd(pwddd);
                userInfoBean.setStats(100);
                Log.v("登录", "" + data.getResult().getUserId() + "  " + data.getResult().getSessionId());
                userInfoBean.setUserId(data.getResult().getUserId());
                userInfoBean.setSessionId(data.getResult().getSessionId());

                // 添加数据库
                try {
                    addUser(userInfoBean);
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                finish();
                overridePendingTransition(R.anim.ac_in, R.anim.ac_out);
            }
        }

        @Override
        public void fail(ApiException a) {

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unBind.unbind();// 解绑
        loginPresenter.unBind();
    }
}
