package demo.com.wdmoviedemo.view.myactivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import demo.com.wdmoviedemo.bean.MyMessageData;
import demo.com.wdmoviedemo.bean.Result;
import demo.com.wdmoviedemo.core.base.BaseActivity;
import demo.com.wdmoviedemo.core.exception.ApiException;
import demo.com.wdmoviedemo.core.interfase.DataCall;
import demo.com.wdmoviedemo.bean.MyMessage;
import demo.com.wdmoviedemo.core.utils.ToDate;
import demo.com.wdmoviedemo.presenter.MyMessagePresenter;
import demo.com.wdmoviedemo.presenter.UpdateMysessagePresenter;

/**
 * 作者: Wang on 2019/1/24 15:53
 * 寄语：加油！相信自己可以！！！
 */


public class My_Messiage_Activity extends BaseActivity {
    int sex = 0;
    @BindView(R.id.update_date)
    TextView t_date;
    @BindView(R.id.update_name)
    TextView t_name;
    @BindView(R.id.update_mail)
    TextView t_mail;
    @BindView(R.id.update_sex)
    TextView t_sex;
    @BindView(R.id.update_phone)
    TextView t_phone;
    @BindView(R.id.my_header)
    SimpleDraweeView t_header;
    private MyMessagePresenter myMessagePresenter;
    private UpdateMysessagePresenter updateMysessagePresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_message_);
        ButterKnife.bind(this);
        myMessagePresenter = new MyMessagePresenter(new mymesage());

        updateMysessagePresenter = new UpdateMysessagePresenter(new upp());

    }

    @Override
    protected void onResume() {
        super.onResume();
        myMessagePresenter.requestNet(userInfoBean.getUserId(), userInfoBean.getSessionId());
    }

    class upp implements DataCall<Result<MyMessage>> {
        @Override
        public void success(Result<MyMessage> data) {
            Toast.makeText(My_Messiage_Activity.this, "" + data.getMessage(), Toast.LENGTH_SHORT).show();
            if (data.getStatus().equals("0000")) {
                onResume();
            }
        }

        @Override
        public void fail(ApiException a) {

        }
    }

    class mymesage implements DataCall<Result<MyMessageData>> {
        @Override
        public void success(Result<MyMessageData> data) {
//            Toast.makeText(My_Messiage_Activity.this, "" + data.getMessage(), Toast.LENGTH_SHORT).show();
            if (data.getStatus().equals("0000")) {
                MyMessageData result = data.getResult();
                if (result.getPhone() == null) {
                    t_phone.setText("手机号");
                } else {
                    t_phone.setText(result.getPhone() + "");
                }
                if (result.getEmail() == null) {
                    t_mail.setText("邮箱");
                } else {
                    t_mail.setText(result.getEmail() + "");
                }

                if (result.getBirthday() == 0) {
                    String timedate = ToDate.timedate(result.getBirthday());
                    t_date.setText("1998-01-25");
                } else {
                    String timedate = ToDate.timedate(result.getBirthday());
                    t_date.setText("" + timedate);
                }

                if (!result.getHeadPic().contains(".")) {
                    t_header.setImageURI(result.getHeadPic() + ".png");
                } else {
                    t_header.setImageURI(result.getHeadPic());

                }
                t_name.setText(result.getNickName());
                int s = result.getSex();

                if (s == 1) {
                    t_sex.setText("男");
                } else {
                    t_sex.setText("女");
                }
            }
        }

        @Override
        public void fail(ApiException a) {
        }
    }

    @OnClick({R.id.go_updapwd, R.id.back_image, R.id.update})
    void update_pwd(View v) {
        if (v.getId() == R.id.go_updapwd) {
            startActivity(new Intent(My_Messiage_Activity.this, UpdatePwd_Activity.class));
            overridePendingTransition(R.anim.ac_in, R.anim.ac_out);
        } else if (v.getId() == R.id.back_image) {
            finish();//关闭页面
            overridePendingTransition(R.anim.ac_in, R.anim.ac_out);
        } else if (v.getId() == R.id.update) {

            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            final View view1 = View.inflate(this, R.layout.activity_mine_dialog, null);


            builder.setTitle("修改信息");
            builder.setView(view1);
            builder.setPositiveButton("修改", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    EditText newName = view1.findViewById(R.id.newname);
                    EditText newBox = view1.findViewById(R.id.newbox);

                    RadioButton man = view1.findViewById(R.id.man);
                    RadioButton woman = view1.findViewById(R.id.woman);

                    if (man.isChecked()) {
                        sex = 1;
                    }
                    if (woman.isChecked()) {
                        sex = 2;
                    }

                    String name = newName.getText().toString().trim();
                    String box = newBox.getText().toString().trim();


                    updateMysessagePresenter.requestNet(userInfoBean.getUserId(), userInfoBean.getSessionId(), name, sex, box);
                }
            });
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder.show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myMessagePresenter.unBind();
    }
}
