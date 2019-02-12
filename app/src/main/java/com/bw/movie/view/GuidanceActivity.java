package com.bw.movie.view;

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

import com.bw.movie.R;
import com.bw.movie.core.base.BaseActivity;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import com.bw.movie.core.guidancedata.DepthPageTransformer;
import com.bw.movie.core.guidancedata.ViewPagerAdatper;

/**
 * 作者: Wang on 2019/1/22 13:57
 * 寄语：加油！相信自己可以！！！
 */


public class GuidanceActivity extends BaseActivity {

    private ViewPager mInVp;
    private LinearLayout mInLl;
    private List<View> mViewList;
    private ImageView mLightDots;
    private int mDistance;
    private ImageView mOneDot;
    private ImageView mTwoDot;
    private ImageView mThreeDot;
    private ImageView mFourDot;
    private SharedPreferences sp0123;
    private LinearLayout linearTiao;

    int time = 1;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 2) {

                linearTiao.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(GuidanceActivity.this, HomeActivity.class));
                        finish();
                        overridePendingTransition(R.anim.ac_in, R.anim.ac_out);
                        return;
                    }
                });
                if (time == 0) {
                    startActivity(new Intent(GuidanceActivity.this, HomeActivity.class));
                    finish();
                    overridePendingTransition(R.anim.ac_in, R.anim.ac_out);
                    return;
                }
                time--;
                handler.sendEmptyMessageDelayed(2, 500);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sp0123 = getSharedPreferences("sp0124", MODE_PRIVATE);
        String ydy = sp0123.getString("ydy", "");
        if (ydy.equals("1")) {
            startActivity(new Intent(GuidanceActivity.this, HomeActivity.class));
            finish();
            overridePendingTransition(R.anim.ac_in, R.anim.ac_out);
            return;
        }
        setContentView(R.layout.activity_guidance);
        sp0123 = getSharedPreferences("sp0124", MODE_PRIVATE);
        linearTiao = findViewById(R.id.tiao);

        initView();
        initData();
        mInVp.setAdapter(new ViewPagerAdatper(mViewList));
        addDots();
        moveDots();
        mInVp.setPageTransformer(true, new DepthPageTransformer());
    }

    private void moveDots() {
        mLightDots.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                //获得两个圆点之间的距离
                mDistance = mInLl.getChildAt(1).getLeft() - mInLl.getChildAt(0).getLeft();
                mLightDots.getViewTreeObserver()
                        .removeGlobalOnLayoutListener(this);
            }
        });
        mInVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //页面滚动时小白点移动的距离，并通过setLayoutParams(params)不断更新其位置
                float leftMargin = mDistance * (position + positionOffset);
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mLightDots.getLayoutParams();
                params.leftMargin = (int) leftMargin;
                mLightDots.setLayoutParams(params);

            }

            @Override
            public void onPageSelected(int position) {
                //页面跳转时，设置小圆点的margin
                float leftMargin = mDistance * position;
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mLightDots.getLayoutParams();
                params.leftMargin = (int) leftMargin;
                mLightDots.setLayoutParams(params);
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
        mOneDot = new ImageView(this);
        mOneDot.setImageResource(R.drawable.gray_dot);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 0, 40, 0);
        mInLl.addView(mOneDot, layoutParams);
        mTwoDot = new ImageView(this);
        mTwoDot.setImageResource(R.drawable.gray_dot);
        mInLl.addView(mTwoDot, layoutParams);
        mThreeDot = new ImageView(this);
        mThreeDot.setImageResource(R.drawable.gray_dot);
        mInLl.addView(mThreeDot, layoutParams);
        mFourDot = new ImageView(this);
        mFourDot.setImageResource(R.drawable.gray_dot);
        mInLl.addView(mFourDot, layoutParams);
        setClickListener();

    }

    private void setClickListener() {
        mOneDot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mInVp.setCurrentItem(0);
            }
        });
        mTwoDot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mInVp.setCurrentItem(1);
            }
        });
        mThreeDot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mInVp.setCurrentItem(3);
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
        mInVp = findViewById(R.id.in_viewpager);
        mInLl = findViewById(R.id.in_ll);
        mLightDots = findViewById(R.id.iv_light_dots);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeMessages(2);
    }
    @Override
    public void onResume() {
        super.onResume();

        MobclickAgent.onPageStart("引导页面");
        MobclickAgent.onResume(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("引导页面");
        MobclickAgent.onPause(this);
    }
}
