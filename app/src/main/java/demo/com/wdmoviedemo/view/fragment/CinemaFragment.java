package demo.com.wdmoviedemo.view.fragment;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.bw.movie.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import demo.com.wdmoviedemo.core.base.BaseFragment;

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

    @OnClick({R.id.cinema_coming, R.id.cinema_txt_ishit, R.id.cinema_search_image, R.id.serch_line_ref})
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
            if (flag) {
                initExpand();//点击搜索 伸展
            } else {
                initReduce();//点击text收缩
            }
            flag = !flag;
        } else if (view.getId() == R.id.serch_line_ref) {
            etName = edname.getText().toString().trim();
            // 点击隐藏
            if (TextUtils.isEmpty(etName)) {
                Toast.makeText(getContext(), "输入内容不能为空", Toast.LENGTH_SHORT).show();
            }

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

    /*设置伸展状态时的布局*/
    private void initExpand() {
        edname.setHint("CGV影城");
        edname.requestFocus();
        edname.setHintTextColor(Color.WHITE);
        textName.setText("搜索");
        textName.setVisibility(View.VISIBLE);
        edname.setVisibility(View.VISIBLE);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) cineatRel.getLayoutParams();
        layoutParams.width = dip2px(210);
        layoutParams.setMargins(dip2px(0), dip2px(20), dip2px(0), dip2px(0));
        cineatRel.setLayoutParams(layoutParams);
        edname.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                edname.setFocusable(true);
                edname.setFocusableInTouchMode(true);
                return false;
            }
        });
        //开始动画
        beginDelayedTransition(cineatRel);

    }

    /*设置收缩状态时的布局*/
    private void initReduce() {
        edname.setCursorVisible(false);
        edname.setVisibility(View.GONE);
        textName.setVisibility(View.GONE);
        LinearLayout.LayoutParams LayoutParams = (LinearLayout.LayoutParams) cineatRel.getLayoutParams();
        LayoutParams.width = dip2px(40);
        LayoutParams.setMargins(dip2px(0), dip2px(0), dip2px(0), dip2px(0));
        cineatRel.setLayoutParams(LayoutParams);

        //隐藏键盘
        InputMethodManager imm = (InputMethodManager) getActivity()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getActivity().getWindow()
                .getDecorView().getWindowToken(), 0);

        edname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edname.setCursorVisible(true);
            }
        });
        //开始动画
        beginDelayedTransition(cineatRel);
    }

    private void beginDelayedTransition(ViewGroup view) {
        transition = new AutoTransition();
        transition.setDuration(500);
        TransitionManager.beginDelayedTransition(view, transition);
    }

    private int dip2px(float dpVale) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpVale * scale + 0.5f);
    }

}
