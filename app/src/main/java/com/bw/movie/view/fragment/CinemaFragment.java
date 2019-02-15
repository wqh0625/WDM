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
import com.bw.movie.core.utils.MyApp;

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
    private boolean animatort = false;
    private boolean animatorf = false;

    public static double latitude;
    public static double longitude;
    private LocationClient locationClient;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_cinema;
    }

    @Override
    protected void initView(View view) {
        initData();
        initMap();

        ObjectAnimator animator = ObjectAnimator.ofFloat(cineatRel, "translationX", 20f, 470f);
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

        //定位服务的客户端。宿主程序在客户端声明此类，并调用，目前只支持在主线程中启动
        locationClient = new LocationClient(MyApp.getContext());
        //声明LocationClient类实例并配置定位参数
        LocationClientOption locationOption = new LocationClientOption();
          MyLocationListener myLocationListener = new  MyLocationListener();
//注册监听函数
        locationClient.registerLocationListener(myLocationListener);
//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        locationOption.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
//可选，默认gcj02，设置返回的定位结果坐标系，如果配合百度地图使用，建议设置为bd09ll;
        locationOption.setCoorType("gcj02");
//可选，默认0，即仅定位一次，设置发起连续定位请求的间隔需要大于等于1000ms才是有效的
        locationOption.setScanSpan(1100);
//可选，设置是否需要地址信息，默认不需要
        locationOption.setIsNeedAddress(true);
//可选，设置是否需要地址描述
        locationOption.setIsNeedLocationDescribe(true);
//可选，设置是否需要设备方向结果
        locationOption.setNeedDeviceDirect(false);
//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        locationOption.setLocationNotify(true);
//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        locationOption.setIgnoreKillProcess(true);
//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        locationOption.setIsNeedLocationDescribe(true);
//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        locationOption.setIsNeedLocationPoiList(true);
//可选，默认false，设置是否收集CRASH信息，默认收集
        locationOption.SetIgnoreCacheException(false);
//可选，默认false，设置是否开启Gps定位
        locationOption.setOpenGps(true);
//可选，默认false，设置定位时是否需要海拔信息，默认不需要，除基础定位版本都可用
        locationOption.setIsNeedAltitude(false);
//设置打开自动回调位置模式，该开关打开后，期间只要定位SDK检测到位置变化就会主动回调给开发者，该模式下开发者无需再关心定位间隔是多少，定位SDK本身发现位置变化就会及时回调给开发者
        locationOption.setOpenAutoNotifyMode();
//设置打开自动回调位置模式，该开关打开后，期间只要定位SDK检测到位置变化就会主动回调给开发者
        locationOption.setOpenAutoNotifyMode(3000, 1, LocationClientOption.LOC_SENSITIVITY_HIGHT);
        ///--
        //声明LocationClient类
        locationClient.registerLocationListener(myLocationListener);

        locationClient.setLocOption(locationOption);
        locationClient.start();
    }

    class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
//            String locationDescribe = location.getLocationDescribe();    //获取位置描述信息
            //获取详细地址信息
            String addr = location.getCity();
            //获取纬度信息
            latitude = location.getLatitude();
            //获取经度信息
            longitude = location.getLongitude();
            if (addr == null||addr==""||txtLocation==null) {
                getActivity().finish();
                return;
            }
            txtLocation.setText(addr+"");
            locationClient.stop();
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
            ObjectAnimator animator = ObjectAnimator.ofFloat(cineatRel, "translationX", 470f, 20f);
            animator.setDuration(1500);
            animator.start();
        } else if (view.getId() == R.id.cinema_textName) {
            if (animatorf) {
                return;
            }
            animatorf = true;
            animatort = false;
            //这是隐藏进去的动画
            ObjectAnimator animator2 = ObjectAnimator.ofFloat(cineatRel, "translationX", 20f, 470f);
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
