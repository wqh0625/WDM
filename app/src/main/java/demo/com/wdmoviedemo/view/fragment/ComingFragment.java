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
import demo.com.wdmoviedemo.core.adapter.CinemaxAdapters;
import demo.com.wdmoviedemo.core.base.BaseFragment;
import demo.com.wdmoviedemo.core.exception.ApiException;
import demo.com.wdmoviedemo.core.interfase.DataCall;
import demo.com.wdmoviedemo.presenter.ComingPresenter;
import demo.com.wdmoviedemo.presenter.ConcernPresenter;
import demo.com.wdmoviedemo.view.Film_Details_Activity;
import demo.com.wdmoviedemo.view.LoginActivity;

public class ComingFragment extends BaseFragment {
    private RecyclerView comingrecy;
    private CinemaxAdapters cinemaxAdapter;
    private ComingPresenter comingPresenter;
    private int userId;
    private String sessionId;

    private void initData() {
        userId = userInfoBean.getUserId();
        sessionId = userInfoBean.getSessionId();
        cinemaxAdapter = new CinemaxAdapters(getActivity(),CinemaxAdapters.COMING_TYPE);
        comingrecy.setAdapter(cinemaxAdapter);
        comingPresenter = new ComingPresenter(new ComingCall());
        comingrecy.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        comingPresenter.requestNet(1,10);
        cinemaxAdapter.setOnMovieItemClickListener(new CinemaxAdapters.OnCinemaxItemClickListener() {
            @Override
            public void onMovieClick(int position) {
                Intent intent = new Intent(getActivity(),Film_Details_Activity.class);
                intent.putExtra("position",position);
                startActivity(intent);
            }
        });
        cinemaxAdapter.setOnImageClickListener(new CinemaxAdapters.OnImageClickListener() {

            private ConcernPresenter concernPresenter;

            @Override
            public void OnImageClick(int position, CarouselData carouselData) {

            }

        });
    }
    class ComingCall implements DataCall<Result<List<CarouselData>>> {

        @Override
        public void success(Result<List<CarouselData>> data) {
            if (data.getStatus().equals("0000")){
                cinemaxAdapter.addAll(data.getResult());
                cinemaxAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void fail(ApiException a) {

        }
    }
    //关注
    class ConcernCall implements DataCall<Result>{

        @Override
        public void success(Result data) {
            if (data.getStatus().equals("0000")){
                Toast.makeText(getActivity(), "关注成功", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void fail(ApiException a) {
            Toast.makeText(getActivity(), "关注失败", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        comingPresenter.unBind();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_coming;
    }

    @Override
    protected void initView(View view) {
        comingrecy = (RecyclerView) view.findViewById(R.id.coming_recy);
        initData();

    }
}
