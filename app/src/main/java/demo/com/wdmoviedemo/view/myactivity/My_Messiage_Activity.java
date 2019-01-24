package demo.com.wdmoviedemo.view.myactivity;

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
import demo.com.wdmoviedemo.bean.UserInfoBean;
import demo.com.wdmoviedemo.core.base.BaseActivity;
import demo.com.wdmoviedemo.core.utils.ToDate;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_message_);
        unbinder = ButterKnife.bind(this);

        t_phone.setText(userInfoBean.getPhone());
//        Log.v("----",userInfoBean.getMail());
        t_mail.setText("2667187655@qq.com");
        String timedate = ToDate.timedate(userInfoBean.getLastLoginTime());
        t_date.setText("" + timedate);
        t_header.setImageURI(Uri.parse(userInfoBean.getHeadPic()));
        t_name.setText(userInfoBean.getNickName());
        int s = userInfoBean.getSex();

        if (s == 1) {
            t_sex.setText("男");
        } else {
            t_sex.setText("女");
        }
    }

    @OnClick(R.id.back_image)
    void o() {
        finish();//关闭页面
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
