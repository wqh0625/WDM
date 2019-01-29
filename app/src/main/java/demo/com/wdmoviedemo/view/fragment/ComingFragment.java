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
import demo.com.wdmoviedemo.presenter.CancelConcernPresenter;
import demo.com.wdmoviedemo.presenter.ComingPresenter;
import demo.com.wdmoviedemo.presenter.ConcernPresenter;
import demo.com.wdmoviedemo.view.Film_Details_Activity;
import demo.com.wdmoviedemo.view.LoginActivity;

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
            public void OnImageClick(int i,int position, CarouselData carouselData) {
                if (userInfoBean == null) {
//                    Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                } else {
                    if (carouselData.getFollowMovie() == 1) {
                        cancelConcernPresenter.requestNet(userInfoBean.getUserId(), userInfoBean.getSessionId(), carouselData.getId(), position,i);
                    } else {
                        concernPresenter.requestNet(userInfoBean.getUserId(), userInfoBean.getSessionId(), position,i);
                    }
                }

            }
        });
    }

    class ComingCall implements DataCall<Result<List<CarouselData>>> {

        @Override
        public void success(Result<List<CarouselData>> data) {
            Toast.makeText(getContext(), "正在热映" + data.getResult().size(), Toast.LENGTH_SHORT).show();
            if (data.getStatus().equals("0000")) {
                cinemaxAdapter.addAll(data.getResult());
                cinemaxAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void fail(ApiException a) {
            Toast.makeText(getContext(), "asas", Toast.LENGTH_SHORT).show();
        }
    }

    //关注
    class ConcernCall implements DataCall<Result> {

        @Override
        public void success(Result data) {
            Toast.makeText(getActivity(), "" + data.getMessage(), Toast.LENGTH_SHORT).show();

            if (data.getStatus().equals("0000")) {
                if (data.getStatus().equals("0000")) {
                    int o = (int) data.getArgs()[4];
                    cinemaxAdapter.getItem(o).setFollowMovie(1);
                    cinemaxAdapter.notifyItemChanged(o);
                }
            }
        }

        @Override
        public void fail(ApiException a) {
            Toast.makeText(getActivity(), "gggg失败", Toast.LENGTH_SHORT).show();
        }
    }

    //取消关注
    class CancelCall implements DataCall<Result> {

        @Override
        public void success(Result data) {
            Toast.makeText(getActivity(), "" + data.getMessage(), Toast.LENGTH_SHORT).show();

            if (data.getStatus().equals("0000")) {
                int o = (int) data.getArgs()[4];
                cinemaxAdapter.getItem(o).setFollowMovie(2);
                cinemaxAdapter.notifyItemChanged(o);
            }
        }

        @Override
        public void fail(ApiException a) {
            Toast.makeText(getContext(), "q失败", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        comingPresenter.unBind();
        concernPresenter.unBind();
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
