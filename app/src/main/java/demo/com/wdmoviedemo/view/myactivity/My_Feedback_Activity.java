package demo.com.wdmoviedemo.view.myactivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.bw.movie.R;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import demo.com.wdmoviedemo.core.base.BaseActivity;

/**
 * 作者: Wang on 2019/1/24 20:31
 * 寄语：加油！相信自己可以！！！
 *
 * @author Wang
 */


@SuppressWarnings("ALL")
public class My_Feedback_Activity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_feedback_);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.back_image_f)
    void on() {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
