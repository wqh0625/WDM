package com.bw.movie.view.myactivity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.bw.movie.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import com.bw.movie.core.base.BaseActivity;
import com.bw.movie.view.myactivity.gzfragment.GzCinemaFragment;
import com.bw.movie.view.myactivity.gzfragment.GzMoiveFragment;
import com.bw.movie.view.myactivity.kffragment.DfkTicketFragment;
import com.bw.movie.view.myactivity.kffragment.YwcFinishFragment;

/**
 * 作者: Wang on 2019/1/24 20:15
 * 寄语：加油！相信自己可以！！！
 */


public class My_Rccord_Activity extends BaseActivity {

    @BindView(R.id.my_rccord_dfk)
    TextView t_dfk;
    @BindView(R.id.my_rccord_yfk)
    TextView t_yfk;
    @BindView(R.id.my_rccord_vp)
    ViewPager r_vp;
    private List<Fragment> fragments;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_rccord_);
        ButterKnife.bind(this);
        ChangeBackGround(0);
        r_vp.setCurrentItem(0);
        initData();
    }

    private void initData() {
        fragments = new ArrayList<>();
        fragments.add(new DfkTicketFragment());
        fragments.add(new YwcFinishFragment());
        r_vp.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return fragments.get(i);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        });
        r_vp.setCurrentItem(0);
        r_vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                ChangeBackGround(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    @OnClick({R.id.my_rccord_yfk, R.id.my_rccord_dfk})
    void onss(View v) {
        if (v.getId() == R.id.my_rccord_dfk) {
            r_vp.setCurrentItem(0);
            ChangeBackGround(0);
        } else if (v.getId() == R.id.my_rccord_yfk) {
            r_vp.setCurrentItem(1);
            ChangeBackGround(1);
        }
    }

    @OnClick(R.id.back_image_r)
    void on() {
        finish();
        overridePendingTransition(R.anim.ac_in, R.anim.ac_out);
    }

    private void ChangeBackGround(int index) {
        //背景颜色
        t_dfk.setBackgroundResource(index == 0 ? R.drawable.details_bgs : R.drawable.details_back);
        //字体颜色
        t_dfk.setTextColor(index == 0 ? Color.WHITE : Color.BLACK);
        t_yfk.setBackgroundResource(index == 1 ? R.drawable.details_bgs : R.drawable.details_back);
        t_yfk.setTextColor(index == 1 ? Color.WHITE : Color.BLACK);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
