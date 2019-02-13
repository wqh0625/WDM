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
    private MyLocationListener myListener = new MyLocationListener();
    private LocationClient mLocationClient = null;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        initView();
        initData();
        initMap();
    }


        /**
         * 将数据存到文件中
         *
         *
         */
//        private void saveDataToFile(Context context, String data, String fileName)
//        {
//            FileOutputStream fileOutputStream = null;
//            BufferedWriter bufferedWriter = null;
//            try
//            {
//                /**
//                 * "data"为文件名,MODE_PRIVATE表示如果存在同名文件则覆盖，
//                 * 还有一个MODE_APPEND表示如果存在同名文件则会往里面追加内容
//                 */
//                fileOutputStream = context.openFileOutput(fileName,
//                        Context.MODE_PRIVATE);
//                bufferedWriter = new BufferedWriter(
//                        new OutputStreamWriter(fileOutputStream));
//                bufferedWriter.write(data);
//            }
//            catch (IOException e)
//            {
//                e.printStackTrace();
//            }
//
//            finally
//            {
//                try
//                {
//                    if (bufferedWriter != null)
//                    {
//                        bufferedWriter.close();
//                    }
//                }
//                catch (IOException e)
//                {
//                    e.printStackTrace();
//                }
//            }
//        }


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
//                detailsVp.setCurrentItem(i);
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
        recommendCinemaLinear =  findViewById(R.id.recommend_cinema_linear);
        recommendCinemaLinear.setOnClickListener(this);



    }

    private void initMap() {
        mLocationClient = new LocationClient(this);
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
            if (addr == null) {
                return;
            }
            txtLocation.setText(addr);
            mLocationClient.stop();
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
                ObjectAnimator animator = ObjectAnimator.ofFloat(recommendCinemaLinear, "translationX", 350f, 30f);
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
                ObjectAnimator animator2 = ObjectAnimator.ofFloat(recommendCinemaLinear, "translationX", 30f, 350f);
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
