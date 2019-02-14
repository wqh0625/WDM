package com.bw.movie.view.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

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
import com.bw.movie.presenter.ConcernPresenter;
import com.bw.movie.presenter.IsHitPresenter;
import com.bw.movie.view.Film_Details_Activity;
import com.bw.movie.view.LoginActivity;

public class IsHitFragment extends BaseFragment {
    private RecyclerView ishitRecy;
    private CinemaxAdapters cinemaxAdapter;
    private IsHitPresenter isHitPresenter;
    private ConcernPresenter concernPresenter;
    private CancelConcernPresenter cancelConcernPresenter;

    private void initData() {
        cinemaxAdapter = new CinemaxAdapters(getActivity(), CinemaxAdapters.ISHIT_TYPE);
        ishitRecy.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        ishitRecy.setAdapter(cinemaxAdapter);
        isHitPresenter = new IsHitPresenter(new IsHitCall());

        if (userInfoBean == null) {
            isHitPresenter.requestNet(0, "", 1, 10);
        } else {
            isHitPresenter.requestNet(userInfoBean.getUserId(), userInfoBean.getSessionId(), 1, 10);
        }
        cinemaxAdapter.setOnMovieItemClickListener(new CinemaxAdapters.OnCinemaxItemClickListener() {
            @Override
            public void onMovieClick(int position) {
                Intent intent = new Intent(getActivity(), Film_Details_Activity.class);
                intent.putExtra("position", position);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.ac_in, R.anim.ac_out);
            }
        });
        cinemaxAdapter.setOnImageClickListener(new CinemaxAdapters.OnImageClickListener() {

            @Override
            public void OnImageClick(int i, int position, CarouselData carouselData) {
                if (student.size()==0) {
                     s();
                } else {
                    if (carouselData.getFollowMovie() == 1) {
                        cancelConcernPresenter.requestNet(userInfoBean.getUserId(), userInfoBean.getSessionId(), carouselData.getId(), position, i);

                    } else {
                        concernPresenter.requestNet(userInfoBean.getUserId(), userInfoBean.getSessionId(), carouselData.getId(), position, i);

                    }
                }
            }

        });
    }

    class IsHitCall implements DataCall<Result<List<CarouselData>>> {

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
                cinemaxAdapter.notifyItemChanged(o);
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
                int po = (int) data.getArgs()[4];
                cinemaxAdapter.getItem(po).setFollowMovie(2);
                cinemaxAdapter.notifyItemChanged(po);
            }
        }

        @Override
        public void fail(ApiException a) {
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isHitPresenter.unBind();
        cancelConcernPresenter.unBind();
        concernPresenter.unBind();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_is_hit;
    }

    @Override
    protected void initView(View view) {
        ishitRecy = (RecyclerView) view.findViewById(R.id.ishit_recy);
        initData();
        concernPresenter = new ConcernPresenter(new ConcernCall());
        cancelConcernPresenter = new CancelConcernPresenter(new CancelCall());
    }
}
