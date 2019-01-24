package demo.com.wdmoviedemo.view.myactivity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.bw.movie.R;

import butterknife.ButterKnife;
import butterknife.OnClick;
import demo.com.wdmoviedemo.core.base.BaseActivity;

/**
 * 作者: Wang on 2019/1/24 20:15
 * 寄语：加油！相信自己可以！！！
 */


public class My_Attention_Activity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_attention_);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.back_image_a)
    void on() {
        finish();
    }
}
