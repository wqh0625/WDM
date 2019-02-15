package com.bw.movie.view;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.bw.movie.R;
import com.bw.movie.core.base.BaseActivity;
import com.bw.movie.core.utils.MyApp;
import com.bw.movie.view.fragment.CinemaFragment;
import com.umeng.analytics.MobclickAgent;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import com.bw.movie.view.fragment.CinemaxFragment;
import com.bw.movie.view.fragment.ComingFragment;
import com.bw.movie.view.fragment.HomeFragment;
import com.bw.movie.view.fragment.IsHitFragment;


public class DetailsActivity extends BaseActivity implements View.OnClickListener {


    private ImageView imageLocation;
    private TextView txtLocation;
    private RelativeLayout top;
    private TextView txtCinemax;
    private TextView txtIshit;
    private TextView txtComing;
    private RelativeLayout center;
    private ViewPager detailsVp;
    private ImageView imageBack;
    private List<Fragment> fragments;
    private ImageView recommendCinemSearchImage;
    private EditText recommendCinemaEdname;
    private TextView recommendCinemaTextName;
    private RelativeLayout recommendCinemaLinear;
    private int image;
    private boolean animatort = false;
    private boolean animatorf = false;

    private LocationClient locationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        initView();
        initData();
        initMap();
    }

    private void initData() {
        fragments = new ArrayList<>();
        fragments.add(new CinemaxFragment());
        fragments.add(new IsHitFragment());
        fragments.add(new ComingFragment());

        detailsVp.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return fragments.get(i);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        });
        detailsVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
        ChangeBackGround(image);
        detailsVp.setCurrentItem(image);
    }

    private void initView() {
        Intent intent = getIntent();
        image = intent.getExtras().getInt("image");
        imageLocation = (ImageView) findViewById(R.id.image_location);
        txtLocation = (TextView) findViewById(R.id.txt_location);
        top = (RelativeLayout) findViewById(R.id.top);
        txtCinemax = (TextView) findViewById(R.id.txt_Cinemax);
        txtIshit = (TextView) findViewById(R.id.txt_ishit);
        txtComing = (TextView) findViewById(R.id.txt_coming);
        center = (RelativeLayout) findViewById(R.id.center);
        detailsVp = (ViewPager) findViewById(R.id.details_vp);
        imageBack = (ImageView) findViewById(R.id.image_back);
        txtCinemax.setOnClickListener(this);
        txtIshit.setOnClickListener(this);
        txtComing.setOnClickListener(this);
        imageBack.setOnClickListener(this);
        recommendCinemSearchImage = (ImageView) findViewById(R.id.recommend_cinem_search_image);
        recommendCinemSearchImage.setOnClickListener(this);
        recommendCinemaEdname = (EditText) findViewById(R.id.recommend_cinema_edname);
        recommendCinemaEdname.setOnClickListener(this);
        recommendCinemaTextName = (TextView) findViewById(R.id.recommend_cinema_textName);
        recommendCinemaTextName.setOnClickListener(this);
        recommendCinemaLinear = findViewById(R.id.recommend_cinema_linear);
        recommendCinemaLinear.setOnClickListener(this);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(recommendCinemaLinear, "translationX", 20f, 470f);
        animator2.setDuration(1);
        animator2.start();
    }

    private void initMap() {
        //定位服务的客户端。宿主程序在客户端声明此类，并调用，目前只支持在主线程中启动
        locationClient = new LocationClient(MyApp.getContext());
        //声明LocationClient类实例并配置定位参数
        LocationClientOption locationOption = new LocationClientOption();
        MyLocationListener myLocationListener = new MyLocationListener();
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
            String addr = location.getCity();    //获取详细地址信息
            if (addr == null) {
                return;
            }
            txtLocation.setText(addr);
            locationClient.stop();
        }

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.txt_Cinemax:
                detailsVp.setCurrentItem(0);
                ChangeBackGround(0);
                break;
            case R.id.txt_ishit:
                detailsVp.setCurrentItem(1);
                ChangeBackGround(1);
                break;
            case R.id.txt_coming:
                detailsVp.setCurrentItem(2);
                ChangeBackGround(2);
                break;
            case R.id.image_back:
                finish();
                overridePendingTransition(R.anim.ac_in, R.anim.ac_out);
                break;
            case R.id.recommend_cinem_search_image:
                if (animatort) {
                    return;
                }
                animatort = true;
                animatorf = false;
                //这是显示出现的动画
                ObjectAnimator animator = ObjectAnimator.ofFloat(recommendCinemaLinear, "translationX", 470f, 20f);
                animator.setDuration(1500);
                animator.start();

                break;
            case R.id.recommend_cinema_textName:
                if (animatorf) {
                    return;
                }
                animatorf = true;
                animatort = false;
                //这是隐藏进去的动画
                ObjectAnimator animator2 = ObjectAnimator.ofFloat(recommendCinemaLinear, "translationX", 20f, 470f);
                animator2.setDuration(1500);
                animator2.start();
                break;
            default:
                break;
        }
    }

    public void ChangeBackGround(int index) {
        //背景颜色
        txtCinemax.setBackgroundResource(index == 0 ? R.drawable.details_bgs : R.drawable.details_back);
        //字体颜色
        txtCinemax.setTextColor(index == 0 ? Color.WHITE : Color.BLACK);
        txtIshit.setBackgroundResource(index == 1 ? R.drawable.details_bgs : R.drawable.details_back);
        txtIshit.setTextColor(index == 1 ? Color.WHITE : Color.BLACK);
        txtComing.setBackgroundResource(index == 2 ? R.drawable.details_bgs : R.drawable.details_back);
        txtComing.setTextColor(index == 2 ? Color.WHITE : Color.BLACK);
    }

    @Override
    public void onResume() {
        super.onResume();

        MobclickAgent.onPageStart("影片页面");
        MobclickAgent.onResume(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("影片页面");
        MobclickAgent.onPause(this);
    }
}
