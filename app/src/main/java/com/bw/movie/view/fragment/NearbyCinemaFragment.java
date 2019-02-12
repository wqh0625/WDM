package com.bw.movie.view.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.bean.UserInfoBean;
import com.bw.movie.core.dao.DbManager;
import com.bw.movie.core.utils.FileUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.j256.ormlite.dao.Dao;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.lang.reflect.Type;
import java.sql.SQLException;
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


        try {
            DbManager dbManager = new DbManager(getContext());
            Dao<UserInfoBean, String> userDao = dbManager.getUserDao();
            List<UserInfoBean> userInfoBeans = userDao.queryForAll();

            if (userInfoBeans != null && userInfoBeans.size() > 0) {
                findNearbyCinemasPresenter.requestNet(userInfoBean.getUserId(), userInfoBean.getSessionId(), true, String.valueOf(longitude), String.valueOf(latitude));
            } else {
                findNearbyCinemasPresenter.requestNet(0, "", true, String.valueOf(longitude), String.valueOf(latitude));
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
    public void onlike(int i, int id, NearbyData nearbyData) {
        if (userInfoBean == null) {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        } else {
            if (nearbyData.getFollowCinema() == 1) {
                cancelFollowCinemaPresenter.requestNet(userInfoBean.getUserId(), userInfoBean.getSessionId(), id, nearbyData.getId(), i);
            } else {
                followCinemaPresenter.requestNet(userInfoBean.getUserId(), userInfoBean.getSessionId(), id, nearbyData.getId(), i);
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
            rec.loadMoreComplete();
            if (data.getStatus().equals("9999")) {
                Intent intent = new Intent(MyApp.getContext(), LoginActivity.class);
                // 跳转
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.ac_in, R.anim.ac_out);
            }
            if (data.getStatus().equals("0000")) {
                List<NearbyData> nearbyData = data.getResult();
                String s = new Gson().toJson(nearbyData);
                // 存
                FileUtils.saveDataToFile(MyApp.getContext(), s, "附近影院List");

                adapter.setList(data.getResult());
                adapter.notifyDataSetChanged();
            }

        }

        @Override
        public void fail(ApiException a) {
            rec.refreshComplete();
            rec.loadMoreComplete();
            String list = FileUtils.loadDataFromFile(MyApp.getContext(), "附近影院List");
            Type type = new TypeToken<List<NearbyData>>() {
            }.getType();

            List<NearbyData> result = new Gson().fromJson(list, type);
//            Toast.makeText(MyApp.getContext(), "" + result.size(), Toast.LENGTH_SHORT).show();
            adapter.setList(result);
            adapter.notifyDataSetChanged();
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
