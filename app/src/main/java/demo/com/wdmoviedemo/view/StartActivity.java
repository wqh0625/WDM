package demo.com.wdmoviedemo.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.bw.movie.R;


/**
 * 作者: Wang on 2019/1/22 09:36
 * 寄语：加油！相信自己可以！！！
 * <p>
 * 倒计时页面
 */


public class StartActivity extends AppCompatActivity {

    int time = 3;
    Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                if (time <= 0) {
                    startActivity(new Intent(StartActivity.this, GuidanceActivity.class));
                    finish();
                    overridePendingTransition(R.anim.ac_in, R.anim.ac_out);
                    return;
                }
                time--;
                handler.sendEmptyMessageDelayed(1, 1000);
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        handler.sendEmptyMessage(1);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeMessages(1);
    }
}
