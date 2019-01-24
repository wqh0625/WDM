package demo.com.wdmoviedemo.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bw.movie.R;

import java.util.ArrayList;
import java.util.List;

import demo.com.wdmoviedemo.core.guidancedata.DepthPageTransformer;
import demo.com.wdmoviedemo.core.guidancedata.ViewPagerAdatper;

/**
 * 作者: Wang on 2019/1/22 13:57
 * 寄语：加油！相信自己可以！！！
 */


public class GuidanceActivity extends AppCompatActivity {

    private ViewPager mIn_vp;
    private LinearLayout mIn_ll;
    private List<View> mViewList;
    private ImageView mLight_dots;
    private int mDistance;
    private ImageView mOne_dot;
    private ImageView mTwo_dot;
    private ImageView mThree_dot;
    private ImageView mFour_dot;
    private SharedPreferences sp0123;
    private LinearLayout linearTiao;

    int time = 2;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 2) {

                linearTiao.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(GuidanceActivity.this, LoginActivity.class));
                        finish();
                        return;
                    }
                });
                if (time < 0) {
                    startActivity(new Intent(GuidanceActivity.this, LoginActivity.class));
                    finish();
                    return;
                }
                time--;
                handler.sendEmptyMessageDelayed(2, 1000);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sp0123 = getSharedPreferences("sp0123", MODE_PRIVATE);
        String ydy = sp0123.getString("ydy", "");
        if (ydy.equals("1")) {
            startActivity(new Intent(GuidanceActivity.this, LoginActivity.class));
            finish();
            return;
        }
        setContentView(R.layout.activity_guidance);
        sp0123 = getSharedPreferences("sp0123", MODE_PRIVATE);
        linearTiao = findViewById(R.id.tiao);

        initView();
        initData();
        mIn_vp.setAdapter(new ViewPagerAdatper(mViewList));
        addDots();
        moveDots();
        mIn_vp.setPageTransformer(true, new DepthPageTransformer());
    }

    private void moveDots() {
        mLight_dots.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                //获得两个圆点之间的距离
                mDistance = mIn_ll.getChildAt(1).getLeft() - mIn_ll.getChildAt(0).getLeft();
                mLight_dots.getViewTreeObserver()
                        .removeGlobalOnLayoutListener(this);
            }
        });
        mIn_vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //页面滚动时小白点移动的距离，并通过setLayoutParams(params)不断更新其位置
                float leftMargin = mDistance * (position + positionOffset);
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mLight_dots.getLayoutParams();
                params.leftMargin = (int) leftMargin;
                mLight_dots.setLayoutParams(params);
                // 点击跳进登录页面
                if (position == 3) {


//                    SharedPreferences.Editor edit = sp0123.edit();
//                    edit.putString("ydy", "1");
//                    edit.commit();
//
//                    handler.sendEmptyMessage(2);


                }
            }

            @Override
            public void onPageSelected(int position) {
                //页面跳转时，设置小圆点的margin
                float leftMargin = mDistance * position;
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mLight_dots.getLayoutParams();
                params.leftMargin = (int) leftMargin;
                mLight_dots.setLayoutParams(params);
                // 点击跳进登录页面
                if (position == 3) {

                    SharedPreferences.Editor edit = sp0123.edit();
                    edit.putString("ydy", "1");
                    edit.commit();

                    handler.sendEmptyMessage(2);
                    return;
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void addDots() {
        mOne_dot = new ImageView(this);
        mOne_dot.setImageResource(R.drawable.gray_dot);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 0, 40, 0);
        mIn_ll.addView(mOne_dot, layoutParams);
        mTwo_dot = new ImageView(this);
        mTwo_dot.setImageResource(R.drawable.gray_dot);
        mIn_ll.addView(mTwo_dot, layoutParams);
        mThree_dot = new ImageView(this);
        mThree_dot.setImageResource(R.drawable.gray_dot);
        mIn_ll.addView(mThree_dot, layoutParams);
        mFour_dot = new ImageView(this);
        mFour_dot.setImageResource(R.drawable.gray_dot);
        mIn_ll.addView(mFour_dot, layoutParams);
        setClickListener();

    }

    private void setClickListener() {
        mOne_dot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIn_vp.setCurrentItem(0);
            }
        });
        mTwo_dot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIn_vp.setCurrentItem(1);
            }
        });
        mThree_dot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIn_vp.setCurrentItem(3);
            }
        });
    }

    private void initData() {
        mViewList = new ArrayList<View>();
        LayoutInflater lf = getLayoutInflater().from(GuidanceActivity.this);
        View view1 = lf.inflate(R.layout.we_indicator1, null);
        View view2 = lf.inflate(R.layout.we_indicator2, null);
        View view3 = lf.inflate(R.layout.we_indicator3, null);
        View view4 = lf.inflate(R.layout.we_indicator4, null);
        mViewList.add(view1);
        mViewList.add(view2);
        mViewList.add(view3);
        mViewList.add(view4);
    }

    private void initView() {
        mIn_vp = findViewById(R.id.in_viewpager);
        mIn_ll = findViewById(R.id.in_ll);
        mLight_dots = findViewById(R.id.iv_light_dots);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeMessages(2);
    }
}
