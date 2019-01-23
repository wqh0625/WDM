package demo.com.wdmoviedemo.view.fragment;

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

import java.util.List;

import bawei.com.wdmoviedemo.R;
import demo.com.wdmoviedemo.bean.CarouselData;
import demo.com.wdmoviedemo.bean.Result;
import demo.com.wdmoviedemo.core.adapter.CinemaxAdapter;
import demo.com.wdmoviedemo.core.exception.ApiException;
import demo.com.wdmoviedemo.core.interfase.DataCall;
import demo.com.wdmoviedemo.presenter.CarouselPresenter;
import demo.com.wdmoviedemo.presenter.IsHitPresenter;
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
    private CinemaxAdapter cinemaxAdapter;
    private CarouselPresenter carouselPresenter;
    private IsHitPresenter isHitPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initView(view);
        initData();
        return view;
    }

    private void initData() {
        //热门电影
        cinemaxAdapter = new CinemaxAdapter(getActivity());
        carouselPresenter = new CarouselPresenter(new CinemaxCall());
        recy_Cinemax.setAdapter(cinemaxAdapter);
        recy_Cinemax.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        carouselPresenter.requestNet(3823,"15482120944293823",1,10);


        //正在热映
        cinemaxAdapter = new CinemaxAdapter(getActivity());
        isHitPresenter = new IsHitPresenter(new IsHitCall());
        recy_ishit.setAdapter(cinemaxAdapter);
        recy_ishit.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        isHitPresenter.requestNet(3823,"15482120944293823",1,10);

    }
    class CinemaxCall implements DataCall<Result<List<CarouselData>>> {

        @Override
        public void success(Result<List<CarouselData>> data) {
            if (data.getStatus().equals("0000")){
                Log.i("ssssss",data.toString());
                Toast.makeText(getActivity(), "获取成功", Toast.LENGTH_SHORT).show();
                cinemaxAdapter.addAll(data.getResult());
                cinemaxAdapter.notifyDataSetChanged();

            }
        }

        @Override
        public void fail(ApiException a) {
            Toast.makeText(getActivity(), "获取失败", Toast.LENGTH_SHORT).show();
        }
    }

    class IsHitCall implements DataCall<Result<List<CarouselData>>>{

        @Override
        public void success(Result<List<CarouselData>> data) {
            if (data.getStatus().equals("0000")){
                Toast.makeText(getActivity(), "正在上映查询成功", Toast.LENGTH_SHORT).show();
                cinemaxAdapter.addAll(data.getResult());
                cinemaxAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void fail(ApiException a) {
            Toast.makeText(getActivity(), "失败", Toast.LENGTH_SHORT).show();
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
