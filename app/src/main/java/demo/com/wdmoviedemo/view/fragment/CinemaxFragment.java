package demo.com.wdmoviedemo.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bw.movie.R;

import java.util.List;

import demo.com.wdmoviedemo.bean.CarouselData;
import demo.com.wdmoviedemo.bean.Result;
import demo.com.wdmoviedemo.core.adapter.CinemaxAdapter;
import demo.com.wdmoviedemo.core.adapter.CinemaxAdapters;
import demo.com.wdmoviedemo.core.base.BaseFragment;
import demo.com.wdmoviedemo.core.exception.ApiException;
import demo.com.wdmoviedemo.core.interfase.DataCall;
import demo.com.wdmoviedemo.presenter.CarouselPresenter;
import demo.com.wdmoviedemo.presenter.ConcernPresenter;
import demo.com.wdmoviedemo.view.Film_Details_Activity;

public class CinemaxFragment extends BaseFragment {
    private RecyclerView cinemax_recy;
    private CinemaxAdapters cinemaxAdapter;
    private CarouselPresenter carouselPresenter;
    private int userId;
//    private String sessionId;


    private void initData() {
        cinemaxAdapter = new CinemaxAdapters(getActivity(), CinemaxAdapters.CAROUSEL_TYPE);
        cinemax_recy.setAdapter(cinemaxAdapter);
        carouselPresenter = new CarouselPresenter(new CinemaxCall());
        cinemax_recy.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        carouselPresenter.requestNet(1, 10);
        cinemaxAdapter.setOnMovieItemClickListener(new CinemaxAdapters.OnCinemaxItemClickListener() {
            @Override
            public void onMovieClick(int position) {
                Intent intent = new Intent(getActivity(), Film_Details_Activity.class);
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });
        cinemaxAdapter.setOnImageClickListener(new CinemaxAdapters.OnImageClickListener() {

            private ConcernPresenter concernPresenter;

            @Override
            public void OnImageClick(int position) {
                concernPresenter = new ConcernPresenter(new ConcernCall());
//                concernPresenter.requestNet(userId,sessionId,position);
            }
        });
    }

    class CinemaxCall implements DataCall<Result<List<CarouselData>>> {

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

    //关注
    class ConcernCall implements DataCall<Result> {

        @Override
        public void success(Result data) {
            if (data.getStatus().equals("0000")) {
                Toast.makeText(getActivity(), "关注成功", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void fail(ApiException a) {
            Toast.makeText(getActivity(), "关注失败", Toast.LENGTH_SHORT).show();
        }
    }

    private void init(View view) {
        cinemax_recy = (RecyclerView) view.findViewById(R.id.cinemax_recy);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        carouselPresenter.unBind();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_cinemax;
    }

    @Override
    protected void initView(View view) {
        init(view);
        initData();
    }
}
