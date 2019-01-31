package com.bw.movie.view.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.bw.movie.R;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.List;


import butterknife.BindView;
import com.bw.movie.bean.NearbyData;
import com.bw.movie.bean.Result;
import com.bw.movie.core.adapter.NearbyCinemaAdapter;
import com.bw.movie.core.base.BaseFragment;
import com.bw.movie.core.exception.ApiException;
import com.bw.movie.core.interfase.DataCall;
import com.bw.movie.core.utils.MyApp;
import com.bw.movie.presenter.CancelFollowCinemaPresenter;
import com.bw.movie.presenter.FindNearbyCinemasPresenter;
import com.bw.movie.presenter.FollowCinemaPresenter;
import com.bw.movie.view.LoginActivity;

/**
 * 作者: Wang on 2019/1/25 18:28
 * 寄语：加油！相信自己可以！！！
 */


public class NearbyCinemaFragment extends BaseFragment implements XRecyclerView.LoadingListener, NearbyCinemaAdapter.OnLikeLister {
    @BindView(R.id.nearby_rec)
    XRecyclerView rec;
    private NearbyCinemaAdapter adapter;
    private FindNearbyCinemasPresenter findNearbyCinemasPresenter;
    private FollowCinemaPresenter followCinemaPresenter;
    private CancelFollowCinemaPresenter cancelFollowCinemaPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_nearbycinema;
    }

    @Override
    protected void initView(View view) {
        rec.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rec.setLoadingListener(this);
//        rec.setLoadingMoreEnabled(true);

        findNearbyCinemasPresenter = new FindNearbyCinemasPresenter(new find());

        adapter = new NearbyCinemaAdapter(getActivity());
        adapter.setOnLikeLister(this);
        rec.setAdapter(adapter);

        followCinemaPresenter = new FollowCinemaPresenter(new ConcernCall());
        cancelFollowCinemaPresenter = new CancelFollowCinemaPresenter(new CancelCall());
    }

    @Override
    public void onResume() {
        super.onResume();

        rec.refresh();

    }
    double latitude = CinemaFragment.latitude;
    double longitude = CinemaFragment.longitude;

    @Override
    public void onRefresh() {
        if (findNearbyCinemasPresenter.isRuning()) {
            rec.refreshComplete();
            return;
        }
        rec.refreshComplete();
        rec.loadMoreComplete();
        if (userInfoBean == null) {
            findNearbyCinemasPresenter.requestNet(0, "", true,String.valueOf(longitude),String.valueOf(latitude));
        }else{
            findNearbyCinemasPresenter.requestNet(userInfoBean.getUserId(),userInfoBean.getSessionId(), true,String.valueOf(longitude),String.valueOf(latitude));
        }
    }

    @Override
    public void onLoadMore() {
        if (findNearbyCinemasPresenter.isRuning()) {
            rec.loadMoreComplete();
            return;
        }
        rec.refreshComplete();
        rec.loadMoreComplete();
//        findNearbyCinemasPresenter.requestNet(0,"",false);
    }

    @Override
    public void onlike(int i,int id, NearbyData nearbyData) {
        if (userInfoBean == null) {
//                    Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        } else {
            if (nearbyData.getFollowCinema() == 1) {
                cancelFollowCinemaPresenter.requestNet(userInfoBean.getUserId(), userInfoBean.getSessionId(), id, nearbyData.getId(), i);
            } else {
                followCinemaPresenter.requestNet(userInfoBean.getUserId(), userInfoBean.getSessionId(), id,nearbyData.getId(), i);
            }
        }
    }

    //关注
    class ConcernCall implements DataCall<Result> {

        @Override
        public void success(Result data) {
            Toast.makeText(getActivity(), "" + data.getMessage(), Toast.LENGTH_SHORT).show();
            if (data.getStatus().equals("9999")) {
                Intent intent = new Intent(MyApp.getContext(), LoginActivity.class);
                // 跳转
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.ac_in, R.anim.ac_out);
            }
            if (data.getStatus().equals("0000")) {
                    int o = (int) data.getArgs()[4];
                    adapter.getItem(o).setFollowCinema(1);
                    adapter.notifyDataSetChanged();
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
            if (data.getStatus().equals("9999")) {
                Intent intent = new Intent(MyApp.getContext(), LoginActivity.class);
                // 跳转
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.ac_in, R.anim.ac_out);
            }
            if (data.getStatus().equals("0000")) {
                int o = (int) data.getArgs()[4];
                adapter.getItem(o).setFollowCinema(2);
                adapter.notifyDataSetChanged();
            }
        }

        @Override
        public void fail(ApiException a) {
            Toast.makeText(getContext(), "q失败", Toast.LENGTH_SHORT).show();
        }
    }

    class find implements DataCall<Result<List<NearbyData>>> {
        @Override
        public void success(Result<List<NearbyData>> data) {
            rec.refreshComplete();
            rec.loadMoreComplete();if (data.getStatus().equals("9999")) {
                Intent intent = new Intent(MyApp.getContext(), LoginActivity.class);
                // 跳转
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.ac_in, R.anim.ac_out);
            }
            if (data.getStatus().equals("0000")) {
                adapter.setList(data.getResult());
                adapter.notifyDataSetChanged();
            }
//            Toast.makeText(getContext(), "" + data.getMessage(), Toast.LENGTH_SHORT).show();

        }

        @Override
        public void fail(ApiException a) {

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        findNearbyCinemasPresenter.unBind();
        cancelFollowCinemaPresenter.unBind();
        followCinemaPresenter.unBind();
    }
}
