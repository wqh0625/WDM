package com.bw.movie.view.fragment;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.bw.movie.R;
import com.bw.movie.bean.CarouselData;
import com.bw.movie.bean.NearbyData;
import com.bw.movie.core.utils.FileUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.umeng.analytics.MobclickAgent;

import java.lang.reflect.Type;
import java.util.List;

import com.bw.movie.bean.Result;
import com.bw.movie.core.adapter.CarouselAdapter;
import com.bw.movie.core.adapter.CinemaxAdapter;
import com.bw.movie.core.base.BaseFragment;
import com.bw.movie.core.exception.ApiException;
import com.bw.movie.core.interfase.DataCall;
import com.bw.movie.core.utils.MyApp;
import com.bw.movie.presenter.CarouselPresenter;
import com.bw.movie.presenter.ComingPresenter;
import com.bw.movie.presenter.IsHitPresenter;
import com.bw.movie.view.DetailsActivity;
import com.bw.movie.view.Film_Details_Activity;

import recycler.coverflow.CoverFlowLayoutManger;
import recycler.coverflow.RecyclerCoverFlow;

public class HomeFragment extends BaseFragment implements View.OnClickListener {
    private ImageView imageLocation;
    private TextView txtLocation;
    private RecyclerCoverFlow recycarousel;
    private ImageView imagemore1;
    private RecyclerView recyCinemax;
    private ImageView imageMore2;
    private RecyclerView recyIshit;
    private ImageView imageMore3;
    private RecyclerView recyOnnow;
    private CarouselPresenter carouselPresenter;
    private IsHitPresenter isHitPresenter;
    private ComingPresenter comingPresenter;
    private CarouselAdapter carouselAdapter;
    private CinemaxAdapter cinemaxAdapter, ishitAdapter, comingAdapter;
    private RelativeLayout relativeLayout;
    private int image4 = 3;
    private int image1 = 0;
    private int image2 = 1;
    private int image3 = 2;

    private double longitude;
    private double latitude;
    private TextView movieTextDong;
    private TextView xian;
    private int mCoun;
    private LocationClient locationClient;

    private void initBanner() {
        carouselAdapter = new CarouselAdapter(getActivity());
        recycarousel.setAdapter(carouselAdapter);//设置适配器
        carouselPresenter = new CarouselPresenter(new Carousel());
        if (userInfoBean == null) {
            carouselPresenter.requestNet(0, "", 1, 10);
        } else {
            carouselPresenter.requestNet(userInfoBean.getUserId(), userInfoBean.getSessionId(), 1, 10);
        }
        recycarousel.setOnItemSelectedListener(new CoverFlowLayoutManger.OnSelected() {//滑动监听
            @Override
            public void onItemSelected(int position) {
                int selectedPos = recycarousel.getSelectedPos();
                ObjectAnimator animator = ObjectAnimator.ofFloat(movieTextDong, "translationX", mCoun * (selectedPos));
                animator.setDuration(500);
                animator.start();
            }
        });
        carouselAdapter.setOnCarouselClickListener(new CarouselAdapter.OnCarouselClickListener() {
            @Override
            public void OnCarouselClick(int position) {
                startIntent(position);
            }
        });
    }

    class Carousel implements DataCall<Result<List<CarouselData>>> {

        @Override
        public void success(Result<List<CarouselData>> data) {
            if (data.getStatus().equals("0000")) {
                List<CarouselData> nearbyData = data.getResult();
                String s = new Gson().toJson(nearbyData);
                // 存
                FileUtils.saveDataToFile(MyApp.getContext(), s, "首页轮播List");

                carouselAdapter.setList(data.getResult());
                carouselAdapter.notifyDataSetChanged();
                int mWidth = xian.getWidth();
                int mItemCount = carouselAdapter.getItemCount();
                mCoun = mWidth / mItemCount;
            }
        }

        @Override
        public void fail(ApiException a) {
            String list = FileUtils.loadDataFromFile(MyApp.getContext(), "首页轮播List");
            Type type = new TypeToken<List<CarouselData>>() {
            }.getType();

            List<CarouselData> result = new Gson().fromJson(list, type);

            carouselAdapter.setList(result);
            carouselAdapter.notifyDataSetChanged();
        }
    }

    private void initData() {
        //适配器
        cinemaxAdapter = new CinemaxAdapter(getActivity(), CinemaxAdapter.CAROUSEL_TYPE);
        ishitAdapter = new CinemaxAdapter(getActivity(), CinemaxAdapter.ISHIT_TYPE);
        comingAdapter = new CinemaxAdapter(getActivity(), CinemaxAdapter.COMING_TYPE);
        //布局管理器
        recyCinemax.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyIshit.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyOnnow.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        recyCinemax.setAdapter(cinemaxAdapter);
        recyIshit.setAdapter(ishitAdapter);
        recyOnnow.setAdapter(comingAdapter);

        carouselPresenter = new CarouselPresenter(new CarouselCall());
        if (student.size() == 0) {
            carouselPresenter.requestNet(0, "", 1, 10);
        } else {
            carouselPresenter.requestNet(userInfoBean.getUserId(), userInfoBean.getSessionId(), 1, 10);
        }

        //正在热映
        isHitPresenter = new IsHitPresenter(new IsHitCall());
        if (student.size() == 0) {
            isHitPresenter.requestNet(0, "", 2, 10);
        } else {
            isHitPresenter.requestNet(userInfoBean.getUserId(), userInfoBean.getSessionId(), 1, 10);
        }
        //即将上映
        comingPresenter = new ComingPresenter(new ComingCall());
        if (userInfoBean == null) {
            comingPresenter.requestNet(0, "", 1, 20);
        } else {
            comingPresenter.requestNet(userInfoBean.getUserId(), userInfoBean.getSessionId(), 1, 10);
        }

        //接口回调跳转页面
        cinemaxAdapter.setOnMovieItemClickListener(new CinemaxAdapter.OnMovieItemClickListener() {
            @Override
            public void onMovieClick(int position) {
                startIntent(position);
            }
        });
        ishitAdapter.setOnMovieItemClickListener(new CinemaxAdapter.OnMovieItemClickListener() {
            @Override
            public void onMovieClick(int position) {
                startIntent(position);
            }
        });
        comingAdapter.setOnMovieItemClickListener(new CinemaxAdapter.OnMovieItemClickListener() {
            @Override
            public void onMovieClick(int position) {
                startIntent(position);
            }
        });
    }

    private void startIntent(int position) {
        Intent intent = new Intent(getActivity(), Film_Details_Activity.class);
        intent.putExtra("position", position);
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.ac_in, R.anim.ac_out);
    }

    // 热门电影
    class CarouselCall implements DataCall<Result<List<CarouselData>>> {

        @Override
        public void success(Result<List<CarouselData>> data) {
            if (data.getStatus().equals("0000")) {
                List<CarouselData> nearbyData = data.getResult();
                String s = new Gson().toJson(nearbyData);
                // 存
                FileUtils.saveDataToFile(MyApp.getContext(), s, "热门电影List");
                cinemaxAdapter.addAll(data.getResult());
                cinemaxAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void fail(ApiException a) {
            String list = FileUtils.loadDataFromFile(MyApp.getContext(), "热门电影List");
            Type type = new TypeToken<List<CarouselData>>() {
            }.getType();

            List<CarouselData> result = new Gson().fromJson(list, type);
            cinemaxAdapter.addAll(result);
            cinemaxAdapter.notifyDataSetChanged();
        }
    }

    class IsHitCall implements DataCall<Result<List<CarouselData>>> {

        @Override
        public void success(Result<List<CarouselData>> data) {
            if (data.getStatus().equals("0000")) {
                List<CarouselData> nearbyData = data.getResult();
                String s = new Gson().toJson(nearbyData);
                // 存
                FileUtils.saveDataToFile(MyApp.getContext(), s, "正在热映List");

                ishitAdapter.addAll(data.getResult());
                ishitAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void fail(ApiException a) {
            String list = FileUtils.loadDataFromFile(MyApp.getContext(), "正在热映List");
            Type type = new TypeToken<List<CarouselData>>() {
            }.getType();

            List<CarouselData> result = new Gson().fromJson(list, type);
            ishitAdapter.addAll(result);
            ishitAdapter.notifyDataSetChanged();
        }
    }

    //即将上映
    class ComingCall implements DataCall<Result<List<CarouselData>>> {

        @Override
        public void success(Result<List<CarouselData>> data) {
            if (data.getStatus().equals("0000")) {
                List<CarouselData> nearbyData = data.getResult();
                String s = new Gson().toJson(nearbyData);
                // 存
                FileUtils.saveDataToFile(MyApp.getContext(), s, "即将上映List");

                comingAdapter.addAll(data.getResult());
                comingAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void fail(ApiException a) {
            String list = FileUtils.loadDataFromFile(MyApp.getContext(), "即将上映List");
            Type type = new TypeToken<List<CarouselData>>() {
            }.getType();

            List<CarouselData> result = new Gson().fromJson(list, type);
            comingAdapter.addAll(result);
            comingAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void initView(View view) {
        imageLocation = view.findViewById(R.id.image_location);
        txtLocation = view.findViewById(R.id.atxt_location);
        recycarousel = view.findViewById(R.id.recy_carousel);
        imagemore1 = view.findViewById(R.id.image_more1);
        recyCinemax = view.findViewById(R.id.recy_Cinemax);
        imageMore2 = view.findViewById(R.id.image_more2);
        recyIshit = view.findViewById(R.id.recy_ishit);
        imageMore3 = view.findViewById(R.id.image_more3);
        recyOnnow = view.findViewById(R.id.recy_onnow);
        relativeLayout = view.findViewById(R.id.recommend_cinema_linear);
        movieTextDong = view.findViewById(R.id.movie_text_dong);
        xian = view.findViewById(R.id.movie_text_xian);
        relativeLayout.setOnClickListener(this);
        imagemore1.setOnClickListener(this);
        imageMore2.setOnClickListener(this);
        imageMore3.setOnClickListener(this);

        //初始化数据
        initData();
        //轮播
        initBanner();
        //百度地图
        initMap();

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

        locationClient.setLocOption(locationOption);
        locationClient.start();
    }

    class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
//            String locationDescribe = location.getLocationDescribe();    //获取位置描述信息
            String addr = location.getCity();    //获取详细地址信息
            latitude = location.getLatitude();
            longitude = location.getLongitude();
            if (addr == null) {
                getActivity().finish();
                return;
            }
            txtLocation.setText(addr);
            locationClient.stop();
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.recommend_cinema_linear:
                Intent intent = new Intent(getActivity(), DetailsActivity.class);
                intent.putExtra("image", image1);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.ac_in, R.anim.ac_out);
                break;
            case R.id.image_more1:
                Intent intent1 = new Intent(getActivity(), DetailsActivity.class);
                intent1.putExtra("image", image1);
                startActivity(intent1);
                getActivity().overridePendingTransition(R.anim.ac_in, R.anim.ac_out);
                break;
            case R.id.image_more2:
                Intent intent2 = new Intent(getActivity(), DetailsActivity.class);
                intent2.putExtra("image", image2);
                startActivity(intent2);
                getActivity().overridePendingTransition(R.anim.ac_in, R.anim.ac_out);
                break;
            case R.id.image_more3:
                Intent intent3 = new Intent(getActivity(), DetailsActivity.class);
                intent3.putExtra("image", image3);
                startActivity(intent3);
                getActivity().overridePendingTransition(R.anim.ac_in, R.anim.ac_out);
                break;
            default:
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        carouselPresenter.unBind();
        isHitPresenter.unBind();
        comingPresenter.unBind();
    }

    @Override
    public void onResume() {
        super.onResume();

        MobclickAgent.onPageStart("HomeFragmen页面");
        MobclickAgent.onResume(MyApp.getContext());
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("HomeFragmen页面");
        MobclickAgent.onPause(MyApp.getContext());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }
}
