package demo.com.wdmoviedemo.view;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Locale;

import bawei.com.wdmoviedemo.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import demo.com.wdmoviedemo.bean.Result;
import demo.com.wdmoviedemo.core.exception.ApiException;
import demo.com.wdmoviedemo.core.interfase.DataCall;
import demo.com.wdmoviedemo.core.utils.EncryptUtil;
import demo.com.wdmoviedemo.presenter.RegisterPresenter;

/**
 * 作者: Wang on 2019/1/22 15:00
 * 寄语：加油！相信自己可以！！！
 */

public class RegisterActivity extends AppCompatActivity {

    private Unbinder unbinder;

    @BindView(R.id.mEt_Data_Reg)
    TextView dateAndTimeLabel;
    @BindView(R.id.mEt_Mail_Reg)
    EditText mailEdt;
    @BindView(R.id.mEt_Name_Reg)
    EditText nameEdt;
    @BindView(R.id.mEt_Phone_Reg)
    EditText phoneEdt;
    @BindView(R.id.mEt_Pwd_Reg)
    EditText pwdEdt;
    @BindView(R.id.mEt_Sex_Reg)
    EditText sexEdt;
    private RegisterPresenter registerPresenter;


    //获取日期格式器对象
    DateFormat fmtDateAndTime = DateFormat.getDateInstance(1);
    //获取一个日历对象
    Calendar dateAndTime = Calendar.getInstance(Locale.CHINA);
    //当点击DatePickerDialog控件的设置按钮时，调用该方法
    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            //修改日历控件的年，月，日
            //这里的year,monthOfYear,dayOfMonth的值与DatePickerDialog控件设置的最新值一致
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            //将页面TextView的显示更新为最新时间
            updateLabel();
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        unbinder = ButterKnife.bind(this);

        dateAndTimeLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //生成一个DatePickerDialog对象，并显示。显示的DatePickerDialog控件可以选择年月日，并设置
                new DatePickerDialog(RegisterActivity.this,
                        d,
                        dateAndTime.get(Calendar.YEAR),
                        dateAndTime.get(Calendar.MONTH),
                        dateAndTime.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        registerPresenter = new RegisterPresenter(new myRegister());
    }


    @OnClick(R.id.mBt_Reg)
    void onReg() {
        String date = dateAndTimeLabel.getText().toString().trim();
        String pwd = pwdEdt.getText().toString().trim();
        String name = nameEdt.getText().toString().trim();
        String mail = mailEdt.getText().toString().trim();
        String phone = phoneEdt.getText().toString().trim();
        String sex = sexEdt.getText().toString().trim();

        String pwdencrypt = EncryptUtil.encrypt(pwd);
        int s;
        if (sex.equals("男")) {
            s = 1;
        } else {
            s = 2;
        }

        registerPresenter.requestNet(name, phone, pwdencrypt, pwdencrypt, s, date, "123465", "手机", "5.0", "android", mail);
    }

    class myRegister implements DataCall<Result> {
        @Override
        public void success(Result data) {
            Toast.makeText(RegisterActivity.this, "" + data.getMessage(), Toast.LENGTH_SHORT).show();
            Log.v("data",data.getMessage()+"");
        }

        @Override
        public void fail(ApiException a) {

        }
    }

    //更新页面TextView的方法
    private void updateLabel() {
        dateAndTimeLabel.setText(fmtDateAndTime.format(dateAndTime.getTime()));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        registerPresenter.unBind();
    }
}
