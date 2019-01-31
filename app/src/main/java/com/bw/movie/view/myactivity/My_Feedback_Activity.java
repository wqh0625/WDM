package com.bw.movie.view.myactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.bw.movie.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import com.bw.movie.bean.Result;
import com.bw.movie.core.base.BaseActivity;
import com.bw.movie.core.exception.ApiException;
import com.bw.movie.core.interfase.DataCall;
import com.bw.movie.presenter.RecordFeedBackPresenter;
import com.bw.movie.view.StartActivity;

/**
 * 作者: Wang on 2019/1/24 20:31
 * 寄语：加油！相信自己可以！！！
 *
 * @author Wang
 */


@SuppressWarnings("ALL")
public class My_Feedback_Activity extends BaseActivity {

    @BindView(R.id.feedback_ed)
    EditText e;
    private RecordFeedBackPresenter recordFeedBackPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_feedback_);
        ButterKnife.bind(this);
        recordFeedBackPresenter = new RecordFeedBackPresenter(new record());
    }

    @OnClick({R.id.back_image_f, R.id.feedback_tijiao_btn})
    void on(View view) {
        if (view.getId() == R.id.back_image_f) {
            finish();
            overridePendingTransition(R.anim.ac_in, R.anim.ac_out);
        } else if (view.getId() == R.id.feedback_tijiao_btn) {
            String s = e.getText().toString();
            if (s == "") {
                Toast.makeText(this, "不可反馈空", Toast.LENGTH_SHORT).show();
                return;
            }
            recordFeedBackPresenter.requestNet(userInfoBean.getUserId(), userInfoBean.getSessionId(), s);
        }
    }

    class record implements DataCall<Result> {
        @Override
        public void success(Result data) {
            Toast.makeText(My_Feedback_Activity.this, "" + data.getMessage(), Toast.LENGTH_SHORT).show();
            if (data.getStatus().equals("0000")) {
                e.setText("");
                startActivity(new Intent(My_Feedback_Activity.this,FeedbackSuccess_Activity.class));
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
        recordFeedBackPresenter.unBind();
    }
}
