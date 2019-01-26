package demo.com.wdmoviedemo.view.cinemaactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.bw.movie.R;

import demo.com.wdmoviedemo.core.base.BaseActivity;

/**
 * 作者: Wang on 2019/1/26 09:46
 * 寄语：加油！相信自己可以！！！
 */


public class CinemaDetailActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cinema_detail);
        Intent intent = getIntent();
        String cinemaId = intent.getStringExtra("cinemaID");
        Toast.makeText(this, ""+cinemaId, Toast.LENGTH_SHORT).show();
    }
}
