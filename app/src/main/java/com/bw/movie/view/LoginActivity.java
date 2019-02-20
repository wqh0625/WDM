package com.bw.movie.view;

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
import com.bw.movie.core.utils.MyApp;
import com.j256.ormlite.dao.Dao;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.umeng.analytics.MobclickAgent;

import java.sql.SQLException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import com.bw.movie.bean.LoginData;
import com.bw.movie.bean.Result;
import com.bw.movie.bean.UserInfoBean;
import com.bw.movie.core.base.BaseActivity;
import com.bw.movie.core.dao.DbManager;
import com.bw.movie.core.exception.ApiException;
import com.bw.movie.core.interfase.DataCall;
import com.bw.movie.core.utils.EncryptUtil;
import com.bw.movie.core.utils.WeiXinUtil;
import com.bw.movie.presenter.LoginPresenter;

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
        try {
            setContentView(R.layout.activity_login);
            unBind = ButterKnife.bind(this);
            loginPresenter = new LoginPresenter(new login());
            sp0123 = getSharedPreferences("sp0123", MODE_PRIVATE);
            boolean reme_pwd = sp0123.getBoolean("reme_pwd", false);
            boolean reme_login = sp0123.getBoolean("reme_login", false);


            if (reme_pwd) {


                Dao<UserInfoBean, String> userDao = new DbManager(getApplicationContext()).getUserDao();
                List<UserInfoBean> userInfoBeans = userDao.queryForAll();
                if (userInfoBeans.size() == 0) {
                    return;
                } else {
                    UserInfoBean user = userInfoBeans.get(0);

                    if (user.getPhone() == null) {
                        tel_pwd.setText("");
                        tel_number.setText("");
//                Toast.makeText(this, "nullllll" + student.size(), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    tel_number.setText(user.getPhone() + "");
                    this.reme_pwd.setChecked(true);
                    if (reme_login) {
                        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                        finish();
                        overridePendingTransition(R.anim.ac_in, R.anim.ac_out);
                        return;
                    }
                    return;
                }


            }
        } catch (SQLException eq) {
            eq.printStackTrace();
        }
        // 设置密码不可见
        tel_pwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
        isHide = true;

        SharedPreferences.Editor edit = sp0123.edit();
        edit.putBoolean("reme_pwd", false);
        edit.putBoolean("reme_login", false);
        edit.commit();
    }

    @Override
    protected void onStart() {
        super.onStart();

        try {
            DbManager dbManager = new DbManager(MyApp.getContext());
            List<UserInfoBean> userInfoBeanxc = dbManager.getUserDao().queryForAll();
            if (userInfoBeanxc.size() > 0) {
                finish();
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    @OnClick({R.id.tv_register, R.id.button_login, R.id.eye, R.id.cb_reme_pwd, R.id.cb_reme_login, R.id.iv_wx})
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
                overridePendingTransition(R.anim.ac_in, R.anim.ac_out);
                break;
            case R.id.iv_wx:
                // 微信登录
                if (!WeiXinUtil.success(this)) {
                    Toast.makeText(this, "请先安装应用", Toast.LENGTH_SHORT).show();
                } else {
                    //  验证
                    SendAuth.Req req = new SendAuth.Req();
                    req.scope = "snsapi_userinfo";
                    req.state = "wechat_sdk_demo_test";
                    WeiXinUtil.reg(LoginActivity.this).sendReq(req);
                }
                break;
            default:
                break;
        }
    }

    class login implements DataCall<Result<LoginData>> {
        @Override
        public void success(Result<LoginData> data) {
            Toast.makeText(LoginActivity.this, "登录成功" , Toast.LENGTH_SHORT).show();
            if (data.getStatus().equals("0000")) {

                UserInfoBean userInfoBean = data.getResult().getUserInfo();
                //设置状态

                userInfoBean.setStats(100);
                Log.v("登录", "" + data.getResult().getUserId() + "  " + data.getResult().getSessionId());
                userInfoBean.setUserId(data.getResult().getUserId());
                userInfoBean.setSessionId(data.getResult().getSessionId());

                // 添加数据库
                try {
                    Dao<UserInfoBean, String> userDao = new DbManager(getApplicationContext()).getUserDao();
                    userDao.deleteBuilder().delete();
                    userDao.createOrUpdate(userInfoBean);

                } catch (SQLException e) {
                    e.printStackTrace();
                }
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

    @Override
    public void onResume() {
        super.onResume();
        try {
            DbManager dbManager = new DbManager(MyApp.getContext());
            List<UserInfoBean> userInfoBeanxc = dbManager.getUserDao().queryForAll();
            if (userInfoBeanxc.size() > 0) {
                finish();
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        MobclickAgent.onPageStart("登录页面");
        MobclickAgent.onResume(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("登录页面");
        MobclickAgent.onPause(this);
    }
}
