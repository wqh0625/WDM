package demo.com.wdmoviedemo.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import bawei.com.wdmoviedemo.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import demo.com.wdmoviedemo.bean.LoginData;
import demo.com.wdmoviedemo.bean.Result;
import demo.com.wdmoviedemo.core.exception.ApiException;
import demo.com.wdmoviedemo.core.interfase.DataCall;
import demo.com.wdmoviedemo.core.utils.EncryptUtil;
import demo.com.wdmoviedemo.presenter.LoginPresenter;

/**
 * 作者: Wang on 2019/1/22 15:00
 * 寄语：加油！相信自己可以！！！
 */


public class LoginActivity extends AppCompatActivity {

    private Unbinder unBind;


    @BindView(R.id.et_tel_number)
    EditText tel_number;// 手机号
    @BindView(R.id.et_tel_pwd)
    EditText tel_pwd;// 密码
    @BindView(R.id.eye)
    ImageView eye;// 显示密码或隐藏
    @BindView(R.id.cb_reme_login)
    CheckBox reme_login;//自动登录
    @BindView(R.id.cb_reme_pwd)
    CheckBox reme_pwd;//记住密码


    boolean isHide;
    private LoginPresenter loginPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        unBind = ButterKnife.bind(this);

        // 设置密码不可见
        tel_pwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
        isHide = true;

        loginPresenter = new LoginPresenter(new login());

    }

    class login implements DataCall<Result<LoginData>> {
        @Override
        public void success(Result<LoginData> data) {
            Toast.makeText(LoginActivity.this, "" + data.getMessage(), Toast.LENGTH_SHORT).show();
            if (data.getStatus().equals("0000")) {
                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
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

    @OnClick({R.id.tv_register, R.id.button_login, R.id.eye})
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

                loginPresenter.requestNet(number, pwddd);
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
}
