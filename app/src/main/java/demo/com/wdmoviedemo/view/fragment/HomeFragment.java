package demo.com.wdmoviedemo.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.bw.movie.R;

import java.util.List;
import java.util.logging.Logger;

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

public class HomeFragment extends Fragment {
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
    private CinemaxAdapter cinemaxAdapter;
    private CinemaxAdapter ishitAdapter;
    private CinemaxAdapter comingAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initView(view);
        initData();
        initBanner();
        return view;
    }

    private void initBanner() {
        carouselAdapter = new CarouselAdapter(getActivity());
        recy_carousel.setAdapter(carouselAdapter);//设置适配器
        carouselPresenter = new CarouselPresenter(new Carousel());
        carouselPresenter.requestNet(1,10);
        recy_carousel.setOnItemSelectedListener(new CoverFlowLayoutManger.OnSelected() {//滑动监听
            @Override
            public void onItemSelected(int position) {

            }
        });
    }
    class Carousel implements DataCall<Result<List<CarouselData>>>{

        @Override
        public void success(Result<List<CarouselData>> data) {
            if (data.getStatus().equals("0000")){
                Toast.makeText(getActivity(), "轮播", Toast.LENGTH_SHORT).show();
                carouselAdapter.setList(data.getResult());
                carouselAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void fail(ApiException a) {
            Toast.makeText(getActivity(), "shibai", Toast.LENGTH_SHORT).show();
        }
    }

    private void initData() {
        //适配器
        cinemaxAdapter = new CinemaxAdapter(getActivity(),CinemaxAdapter.CAROUSEL_TYPE);
        ishitAdapter = new CinemaxAdapter(getActivity(),CinemaxAdapter.ISHIT_TYPE);
        comingAdapter = new CinemaxAdapter(getActivity(),CinemaxAdapter.COMING_TYPE);
        //布局管理器
        recy_Cinemax.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        recy_ishit.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        recy_onnow.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));

        recy_Cinemax.setAdapter(cinemaxAdapter);
        recy_ishit.setAdapter(ishitAdapter);
        recy_onnow.setAdapter(comingAdapter);


        carouselPresenter = new CarouselPresenter(new CinemaxCall());
        carouselPresenter.requestNet( 1,10);

        //正在热映
        isHitPresenter = new IsHitPresenter(new CinemaxCall());
        isHitPresenter.requestNet( 1,10);

        //即将上映
        comingPresenter = new ComingPresenter(new CinemaxCall());
        comingPresenter.requestNet( 1,10);

        //接口回调跳转页面
        cinemaxAdapter.setOnMovieItemClickListener(new CinemaxAdapter.OnMovieItemClickListener() {
            @Override
            public void onMovieClick(int position) {
                Intent intent = new Intent(getActivity(),DetailsActivity.class);
                intent.putExtra("position",position);
                startActivity(intent);
            }
        });
    }
    class CinemaxCall implements DataCall<Result<List<CarouselData>>> {

        @Override
        public void success(Result<List<CarouselData>> data) {
            if (data.getStatus().equals("0000")){
                Log.i("ssssss",data.toString());
                Toast.makeText(getActivity(), "获取成功", Toast.LENGTH_SHORT).show();
                cinemaxAdapter.addAll(data.getResult());
                ishitAdapter.addAll(data.getResult());
                comingAdapter.addAll(data.getResult());

            }
        }

        @Override
        public void fail(ApiException a) {
            Toast.makeText(getActivity(), "获取失败", Toast.LENGTH_SHORT).show();
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
    }
}
