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
import demo.com.wdmoviedemo.presenter.ConcernPresenter;
import demo.com.wdmoviedemo.presenter.IsHitPresenter;
import demo.com.wdmoviedemo.view.Film_Details_Activity;
import demo.com.wdmoviedemo.view.LoginActivity;

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
            public void OnImageClick(int i,int position, CarouselData carouselData) {
                if (userInfoBean == null) {
                    Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                } else {
                    if (carouselData.getFollowMovie() == 1) {
                        cancelConcernPresenter.requestNet(userInfoBean.getUserId(), userInfoBean.getSessionId(), carouselData.getId(), position,i);

                    } else {
                        concernPresenter.requestNet(userInfoBean.getUserId(), userInfoBean.getSessionId(), carouselData.getId(), position,i);

                    }
                }
            }

        });
    }

    class IsHitCall implements DataCall<Result<List<CarouselData>>> {

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
            Toast.makeText(getActivity(), "" + data.getMessage(), Toast.LENGTH_SHORT).show();

                if (data.getStatus().equals("0000")) {
                    int o = (int) data.getArgs()[4];
                    cinemaxAdapter.getItem(o).setFollowMovie(1);
                    cinemaxAdapter.notifyItemChanged(o);
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
                int po = (int) data.getArgs()[4];
                cinemaxAdapter.getItem(po).setFollowMovie(2);
                cinemaxAdapter.notifyItemChanged(po);
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
        concernPresenter = new ConcernPresenter(new ConcernCall()); cancelConcernPresenter = new CancelConcernPresenter(new CancelCall());
    }
}
