package com.bw.movie.view.fragment;

import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.transition.AutoTransition;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.bw.movie.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import com.bw.movie.core.base.BaseFragment;

public class CinemaFragment extends BaseFragment {
    private List<Fragment> fragments;
    private boolean flag = true;
    private String etName;

    private AutoTransition transition;
    @BindView(R.id.cinema_vp)
    ViewPager vp;
    @BindView(R.id.cinema_coming)
    TextView TvRecommend;
    @BindView(R.id.cinema_txt_ishit)
    TextView Tvnearby;
    @BindView(R.id.cinema_edname)
    EditText edname;
    @BindView(R.id.cinema_textName)
    TextView textName;
    @BindView(R.id.cinema_location)
    TextView txtLocation;
    @BindView(R.id.serch_line_ref)
    RelativeLayout cineatRel;
    private MyLocationListener myListener = new MyLocationListener();
    private LocationClient mLocationClient = null;
    private boolean animatort = false;
    private boolean animatorf = false;

    public static double latitude;
    public static double longitude;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_cinema;
    }

    @Override
    protected void initView(View view) {
        initData();
        initMap();

        ObjectAnimator animator = ObjectAnimator.ofFloat(cineatRel, "translationX", 40f, 470f);
        animator.setDuration(0);
        animator.start();

    }

    private void initData() {
        fragments = new ArrayList<>();
        // 附近
        // 推荐
        fragments.add(new RecommendCinemaFragment());
        fragments.add(new NearbyCinemaFragment());

        vp.setCurrentItem(0);
        ChangeBackGround(0);
        vp.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return fragments.get(i);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        });
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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

    private void initMap() {
        mLocationClient = new LocationClient(getActivity());
        //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);
        //注册监听函数
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Battery_Saving);
        //如果开发者需要获得当前点的位置信息，此处必须为true
        option.setIsNeedLocationDescribe(true);
        //可选，设置是否需要地址信息，默认不需要
        option.setIsNeedAddress(true);
        //可选，默认false,设置是否使用gps
        option.setOpenGps(true);
        //可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
        option.setLocationNotify(true);
        mLocationClient.setLocOption(option);
        mLocationClient.start();
    }

    class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
//            String locationDescribe = location.getLocationDescribe();    //获取位置描述信息
            String addr = location.getCity();    //获取详细地址信息
            //获取纬度信息
            latitude = location.getLatitude();
            //获取经度信息
            longitude = location.getLongitude();
            if (addr == null) {
                return;
            }
            txtLocation.setText(addr);
            mLocationClient.stop();
        }

    }

    @OnClick({R.id.cinema_coming, R.id.cinema_txt_ishit, R.id.cinema_search_image, R.id.serch_line_ref,R.id.cinema_textName})
    void onccck(View view) {
        if (view.getId() == R.id.cinema_txt_ishit) {
            //推荐影院
            vp.setCurrentItem(0);
            ChangeBackGround(0);
        } else if (view.getId() == R.id.cinema_coming) {
            // 附近影院
            vp.setCurrentItem(1);
            ChangeBackGround(1);
        } else if (view.getId() == R.id.cinema_search_image) {
            if (animatort) {
                return;
            }
            animatort = true;
            animatorf = false;
            //这是显示出现的动画
            ObjectAnimator animator = ObjectAnimator.ofFloat(cineatRel, "translationX", 470f, 40f);
            animator.setDuration(1500);
            animator.start();
        } else if (view.getId() == R.id.cinema_textName) {
            if (animatorf) {
                return;
            }
            animatorf = true;
            animatort = false;
            //这是隐藏进去的动画
            ObjectAnimator animator2 = ObjectAnimator.ofFloat(cineatRel, "translationX", 40f, 470f);
            animator2.setDuration(1500);
            animator2.start();

        }
    }

    private void ChangeBackGround(int index) {
        //背景颜色
        Tvnearby.setBackgroundResource(index == 0 ? R.drawable.details_bgs : R.drawable.details_back);
        //字体颜色
        Tvnearby.setTextColor(index == 0 ? Color.WHITE : Color.BLACK);
        TvRecommend.setBackgroundResource(index == 1 ? R.drawable.details_bgs : R.drawable.details_back);
        TvRecommend.setTextColor(index == 1 ? Color.WHITE : Color.BLACK);
    }
}
