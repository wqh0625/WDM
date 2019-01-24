package demo.com.wdmoviedemo.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
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
import com.baidu.mapapi.map.MapView;
import com.bw.movie.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import demo.com.wdmoviedemo.bean.CarouselData;
import demo.com.wdmoviedemo.bean.Result;
import demo.com.wdmoviedemo.core.adapter.CarouselAdapter;
import demo.com.wdmoviedemo.core.adapter.CinemaxAdapter;
import demo.com.wdmoviedemo.core.exception.ApiException;
import demo.com.wdmoviedemo.core.interfase.DataCall;
import demo.com.wdmoviedemo.presenter.CarouselPresenter;
import demo.com.wdmoviedemo.presenter.ComingPresenter;
import demo.com.wdmoviedemo.presenter.IsHitPresenter;
import demo.com.wdmoviedemo.view.DetailsActivity;
import recycler.coverflow.CoverFlowLayoutManger;
import recycler.coverflow.RecyclerCoverFlow;

public class HomeFragment extends Fragment implements View.OnClickListener {
    private ImageView image_location;
    private TextView txt_location;
    private RecyclerCoverFlow recy_carousel;
    private ImageView image_more1;
    private RecyclerView recy_Cinemax;
    private ImageView image_more2;
    private RecyclerView recy_ishit;
    private ImageView image_more3;
    private RecyclerView recy_onnow;
    private CarouselPresenter carouselPresenter;
    private IsHitPresenter isHitPresenter;
    private ComingPresenter comingPresenter;
    private CarouselAdapter carouselAdapter;
    private CinemaxAdapter cinemaxAdapter, ishitAdapter, comingAdapter;
    private MapView mMapView = null;
    public LocationClient mLocationClient = null;
    private MyLocationListener myListener = new MyLocationListener();
    private RelativeLayout relativeLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initView(view);
        //初始化数据
        initData();
        //轮播
        initBanner();
        //百度地图
        initMap();
        return view;
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


    public class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
//            String locationDescribe = location.getLocationDescribe();    //获取位置描述信息
            String addr = location.getCity();    //获取详细地址信息
            if (addr == null) {
                return;
            }
            txt_location.setText(addr);

        }
    }

    private void initBanner() {
        carouselAdapter = new CarouselAdapter(getActivity());
        recy_carousel.setAdapter(carouselAdapter);//设置适配器
        carouselPresenter = new CarouselPresenter(new Carousel());
        carouselPresenter.requestNet(1, 10);
        recy_carousel.setOnItemSelectedListener(new CoverFlowLayoutManger.OnSelected() {//滑动监听
            @Override
            public void onItemSelected(int position) {

            }
        });
    }

    class Carousel implements DataCall<Result<List<CarouselData>>> {

        @Override
        public void success(Result<List<CarouselData>> data) {
            if (data.getStatus().equals("0000")) {
                carouselAdapter.setList(data.getResult());
                carouselAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void fail(ApiException a) {
        }
    }

    private void initData() {
        //适配器
        cinemaxAdapter = new CinemaxAdapter(getActivity(), CinemaxAdapter.CAROUSEL_TYPE);
        ishitAdapter = new CinemaxAdapter(getActivity(), CinemaxAdapter.ISHIT_TYPE);
        comingAdapter = new CinemaxAdapter(getActivity(), CinemaxAdapter.COMING_TYPE);
        //布局管理器
        recy_Cinemax.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recy_ishit.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recy_onnow.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        recy_Cinemax.setAdapter(cinemaxAdapter);
        recy_ishit.setAdapter(ishitAdapter);
        recy_onnow.setAdapter(comingAdapter);


        carouselPresenter = new CarouselPresenter(new CarouselCall());
        carouselPresenter.requestNet(1, 10);

        //正在热映
        isHitPresenter = new IsHitPresenter(new IsHitCall());
        isHitPresenter.requestNet(2, 10);

        //即将上映
        comingPresenter = new ComingPresenter(new ComingCall());
        comingPresenter.requestNet(1, 20);

        //接口回调跳转页面
        cinemaxAdapter.setOnMovieItemClickListener(new CinemaxAdapter.OnMovieItemClickListener() {
            @Override
            public void onMovieClick(int position) {
                Intent intent = new Intent(getActivity(), DetailsActivity.class);
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });
    }

    class CarouselCall implements DataCall<Result<List<CarouselData>>> {

        @Override
        public void success(Result<List<CarouselData>> data) {
            if (data.getStatus().equals("0000")) {
                cinemaxAdapter.addAll(data.getResult());
                cinemaxAdapter.notifyDataSetChanged();

            }
        }

        @Override
        public void fail(ApiException a) {
        }
    }

    class IsHitCall implements DataCall<Result<List<CarouselData>>> {

        @Override
        public void success(Result<List<CarouselData>> data) {
            if (data.getStatus().equals("0000")) {
                ishitAdapter.addAll(data.getResult());
                ishitAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void fail(ApiException a) {
        }
    }

    class ComingCall implements DataCall<Result<List<CarouselData>>> {

        @Override
        public void success(Result<List<CarouselData>> data) {
            if (data.getStatus().equals("0000")) {
                comingAdapter.addAll(data.getResult());
                comingAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void fail(ApiException a) {
        }
    }


    private void initView(View view) {
        image_location = (ImageView) view.findViewById(R.id.image_location);
        txt_location = (TextView) view.findViewById(R.id.txt_location);
        recy_carousel = (RecyclerCoverFlow) view.findViewById(R.id.recy_carousel);
        image_more1 = (ImageView) view.findViewById(R.id.image_more1);
        recy_Cinemax = (RecyclerView) view.findViewById(R.id.recy_Cinemax);
        image_more2 = (ImageView) view.findViewById(R.id.image_more2);
        recy_ishit = (RecyclerView) view.findViewById(R.id.recy_ishit);
        image_more3 = (ImageView) view.findViewById(R.id.image_more3);
        recy_onnow = (RecyclerView) view.findViewById(R.id.recy_onnow);
        relativeLayout = view.findViewById(R.id.recommend_cinema_linear);
        relativeLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.recommend_cinema_linear:
                Intent intent = new Intent(getActivity(),DetailsActivity.class);
                startActivity(intent);
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
}
