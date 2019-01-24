package demo.com.wdmoviedemo.view.myactivity;

import android.view.View;

import com.bw.movie.R;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import demo.com.wdmoviedemo.core.base.BaseActivity;

/**
 * 作者: Wang on 2019/1/24 20:31
 * 寄语：加油！相信自己可以！！！
 */


public class My_Feedback_Activity extends BaseActivity {

    private Unbinder bind;

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        setContentView(R.layout.activity_my_feedback_);
        bind = ButterKnife.bind(this);
    }


    @OnClick(R.id.back_image_f)
    void on() {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        bind.unbind();
    }
}
