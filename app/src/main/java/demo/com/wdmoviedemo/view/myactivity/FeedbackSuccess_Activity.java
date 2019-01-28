package demo.com.wdmoviedemo.view.myactivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.bw.movie.R;

import demo.com.wdmoviedemo.core.base.BaseActivity;

/**
 * 作者: Wang on 2019/1/27 20:19
 * 寄语：加油！相信自己可以！！！
 */

public class FeedbackSuccess_Activity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_success);
        findViewById(R.id.back_image_feedback_success).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.ac_in, R.anim.ac_out);
            }
        });
    }
}
