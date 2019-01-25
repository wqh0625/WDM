package demo.com.wdmoviedemo.view.myactivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import demo.com.wdmoviedemo.bean.MyMessageData;
import demo.com.wdmoviedemo.bean.Result;
import demo.com.wdmoviedemo.bean.UserInfoBean;
import demo.com.wdmoviedemo.core.base.BaseActivity;
import demo.com.wdmoviedemo.core.exception.ApiException;
import demo.com.wdmoviedemo.core.interfase.DataCall;
import demo.com.wdmoviedemo.core.utils.ToDate;
import demo.com.wdmoviedemo.presenter.MyMessagePresenter;

/**
 * 作者: Wang on 2019/1/24 15:53
 * 寄语：加油！相信自己可以！！！
 */


public class My_Messiage_Activity extends BaseActivity {
    private Unbinder unbinder;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_message_);
        unbinder = ButterKnife.bind(this);


        myMessagePresenter = new MyMessagePresenter(new mymesage());
        myMessagePresenter.requestNet(userInfoBean.getUserId(), userInfoBean.getSessionId());

    }

    class mymesage implements DataCall<Result<MyMessageData>> {
        @Override
        public void success(Result<MyMessageData> data) {
            Toast.makeText(My_Messiage_Activity.this, "" + data.getMessage(), Toast.LENGTH_SHORT).show();
            if (data.getStatus().equals("0000")) {
                MyMessageData result = data.getResult();
                t_phone.setText(result.getPhone());
                t_mail.setText(result.getEmail());
                String timedate = ToDate.timedate(result.getLastLoginTime());
                t_date.setText("" + timedate);
                t_header.setImageURI(Uri.parse(result.getHeadPic()));
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

    @OnClick(R.id.back_image)
    void o() {
        finish();//关闭页面
    }

    @OnClick(R.id.go_updapwd)
    void update_pwd() {
        startActivity(new Intent(My_Messiage_Activity.this, UpdatePwd_Activity.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        myMessagePresenter.unBind();
    }
}
