package com.bw.movie.view.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

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
import com.bw.movie.presenter.FindRecommendCinemasPresenter;
import com.bw.movie.presenter.FollowCinemaPresenter;
import com.bw.movie.view.LoginActivity;

/**
 * 作者: Wang on 2019/1/25 18:33
 * 寄语：加油！相信自己可以！！！
 */


public class RecommendCinemaFragment extends BaseFragment implements XRecyclerView.LoadingListener, NearbyCinemaAdapter.OnLikeLister {

    @BindView(R.id.recommend_rec)
    XRecyclerView rec;

    private NearbyCinemaAdapter adapter;
    private FindRecommendCinemasPresenter findRecommendCinemasPresenter;
    private FollowCinemaPresenter followCinemaPresenter;
    private CancelFollowCinemaPresenter cancelFollowCinemaPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_recommendcinema;
    }

    @Override
    protected void initView(View view) {
        rec.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rec.setLoadingListener(this);

        findRecommendCinemasPresenter = new FindRecommendCinemasPresenter(new find());

        adapter = new NearbyCinemaAdapter(getActivity());
        rec.setAdapter(adapter);
        adapter.setOnLikeLister(this);
        followCinemaPresenter = new FollowCinemaPresenter(new ConcernCall());
        cancelFollowCinemaPresenter = new CancelFollowCinemaPresenter(new CancelCall());
    }

    @Override
    public void onResume() {
        super.onResume();

        rec.refresh();
    }

    @Override
    public void onStart() {
        super.onStart();
        rec.refresh();
    }

    @Override
    public void onRefresh() {
        if (findRecommendCinemasPresenter.isRuning()) {
            rec.refreshComplete();
            return;
        }

        try {
            DbManager dbManager = new DbManager(getContext());
            Dao<UserInfoBean, String> userDao = dbManager.getUserDao();
            List<UserInfoBean> userInfoBeans = userDao.queryForAll();

            if (userInfoBeans != null && userInfoBeans.size() > 0) {
                findRecommendCinemasPresenter.requestNet(userInfoBeans.get(0).getUserId(), userInfoBeans.get(0).getSessionId(), true);
            } else {
                findRecommendCinemasPresenter.requestNet(0, "", true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLoadMore() {
        if (findRecommendCinemasPresenter.isRuning()) {
            rec.loadMoreComplete();
            return;
        }
    }

    @Override
    public void onlike(int i, int id, NearbyData nearbyData) {
        try {
            DbManager dbManager = new DbManager(MyApp.getContext());
            Dao<UserInfoBean, String> userDao = dbManager.getUserDao();
            List<UserInfoBean> userInfoBeans = userDao.queryForAll();
            if (userInfoBeans != null && userInfoBeans.size() > 0) {
                UserInfoBean userInfon = userInfoBeans.get(0);
                if (nearbyData.getFollowCinema() == 1) {
                    cancelFollowCinemaPresenter.requestNet(userInfon.getUserId(), userInfon.getSessionId(), id, nearbyData.getId(), i);
                } else {
                    followCinemaPresenter.requestNet(userInfon.getUserId(), userInfon.getSessionId(), id, nearbyData.getId(), i);
                }
            } else {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.ac_in, R.anim.ac_out);
            }

        } catch (SQLException e) {
            e.printStackTrace();
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
                adapter.getItem(o).setFollowCinema(1);
                adapter.notifyDataSetChanged();
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
                adapter.getItem(o).setFollowCinema(2);
                adapter.notifyDataSetChanged();
            }
        }

        @Override
        public void fail(ApiException a) {
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
                FileUtils.saveDataToFile(MyApp.getContext(), s, "推荐影院List");

                // 设置显示
                adapter.setList(data.getResult());
                adapter.notifyDataSetChanged();
            }
        }

        @Override
        public void fail(ApiException a) {
            rec.refreshComplete();
            rec.loadMoreComplete();
            String list = FileUtils.loadDataFromFile(MyApp.getContext(), "推荐影院List");
            Type type = new TypeToken<List<NearbyData>>() {
            }.getType();

            List<NearbyData> result = new Gson().fromJson(list, type);
//
            adapter.setList(result);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        findRecommendCinemasPresenter.unBind();
        cancelFollowCinemaPresenter.unBind();
        followCinemaPresenter.unBind();
    }
}
