package com.bw.movie.view.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.bw.movie.R;

import java.util.List;

import com.bw.movie.bean.CarouselData;
import com.bw.movie.bean.Result;
import com.bw.movie.core.adapter.CinemaxAdapters;
import com.bw.movie.core.base.BaseFragment;
import com.bw.movie.core.exception.ApiException;
import com.bw.movie.core.interfase.DataCall;
import com.bw.movie.core.utils.MyApp;
import com.bw.movie.presenter.CancelConcernPresenter;
import com.bw.movie.presenter.ComingPresenter;
import com.bw.movie.presenter.ConcernPresenter;
import com.bw.movie.view.Film_Details_Activity;
import com.bw.movie.view.LoginActivity;

public class ComingFragment extends BaseFragment {
    private RecyclerView comingrecy;
    private CinemaxAdapters cinemaxAdapter;
    private ComingPresenter comingPresenter;

    private ConcernPresenter concernPresenter;
    private CancelConcernPresenter cancelConcernPresenter;

    private void initData() {

        cinemaxAdapter = new CinemaxAdapters(getActivity(), CinemaxAdapters.COMING_TYPE);
        comingrecy.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        comingrecy.setAdapter(cinemaxAdapter);
        comingPresenter = new ComingPresenter(new ComingCall());

        if (userInfoBean == null) {
            comingPresenter.requestNet(0, "", 1, 10);
        } else {
            comingPresenter.requestNet(userInfoBean.getUserId(), userInfoBean.getSessionId(), 1, 10);
        }
        cinemaxAdapter.setOnMovieItemClickListener(new CinemaxAdapters.OnCinemaxItemClickListener() {
            @Override
            public void onMovieClick(int position) {
                Intent intent = new Intent(getActivity(), Film_Details_Activity.class);
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });
        cinemaxAdapter.setOnImageClickListener(new CinemaxAdapters.OnImageClickListener() {

            @Override
            public void OnImageClick(int i, int position, CarouselData carouselData) {
                if (student.size() == 0) {
                    s();
                } else {
                    if (carouselData.getFollowMovie() == 1) {
                        cancelConcernPresenter.requestNet(userInfoBean.getUserId(), userInfoBean.getSessionId(), carouselData.getId(), position, i);
                    } else {
                        concernPresenter.requestNet(userInfoBean.getUserId(), userInfoBean.getSessionId(),  carouselData.getId(),position, i);
                    }
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        cinemaxAdapter.notifyDataSetChanged();
    }

    class ComingCall implements DataCall<Result<List<CarouselData>>> {

        @Override
        public void success(Result<List<CarouselData>> data) {
            if (data.getStatus().equals("9999")) {
                s();
            }
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

            if (data.getStatus().equals("9999")) {
                Intent intent = new Intent(MyApp.getContext(), LoginActivity.class);
                // 跳转
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.ac_in, R.anim.ac_out);
            }

            if (data.getStatus().equals("0000")) {
                    int o = (int) data.getArgs()[4];
                    cinemaxAdapter.getItem(o).setFollowMovie(1);
                    cinemaxAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void fail(ApiException a) {
        }
    }

    //取消关注
    class CancelCall implements DataCall<Result> {

        @Override
        public void success(Result data) {

            if (data.getStatus().equals("9999")) {
                Intent intent = new Intent(MyApp.getContext(), LoginActivity.class);
                // 跳转
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.ac_in, R.anim.ac_out);
            }
            if (data.getStatus().equals("0000")) {
                int o = (int) data.getArgs()[4];
                cinemaxAdapter.getItem(o).setFollowMovie(2);
                cinemaxAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void fail(ApiException a) {
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        comingPresenter.unBind();
        concernPresenter.unBind();
        cancelConcernPresenter.unBind();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_coming;
    }

    @Override
    protected void initView(View view) {
        comingrecy = (RecyclerView) view.findViewById(R.id.coming_recy);
        initData();
        concernPresenter = new ConcernPresenter(new ConcernCall());
        cancelConcernPresenter = new CancelConcernPresenter(new CancelCall());
    }
}
