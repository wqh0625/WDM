package demo.com.wdmoviedemo.view.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.bw.movie.R;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.sql.SQLException;
import java.util.List;


import butterknife.BindView;
import demo.com.wdmoviedemo.bean.NearbyData;
import demo.com.wdmoviedemo.bean.Result;
import demo.com.wdmoviedemo.core.adapter.NearbyCinemaAdapter;
import demo.com.wdmoviedemo.core.base.BaseFragment;
import demo.com.wdmoviedemo.core.dao.DbManager;
import demo.com.wdmoviedemo.core.exception.ApiException;
import demo.com.wdmoviedemo.core.interfase.DataCall;
import demo.com.wdmoviedemo.core.utils.MyApp;
import demo.com.wdmoviedemo.presenter.CancelFollowCinemaPresenter;
import demo.com.wdmoviedemo.presenter.FindNearbyCinemasPresenter;
import demo.com.wdmoviedemo.presenter.FollowCinemaPresenter;
import demo.com.wdmoviedemo.view.LoginActivity;

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

    @Override
    public void onRefresh() {
        if (findNearbyCinemasPresenter.isRuning()) {
            rec.refreshComplete();
            return;
        }
        rec.refreshComplete();
        rec.loadMoreComplete();
        if (userInfoBean == null) {
            findNearbyCinemasPresenter.requestNet(0, "", true);
        }else{
            findNearbyCinemasPresenter.requestNet(userInfoBean.getUserId(),userInfoBean.getSessionId(), true);
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
            if (nearbyData.getFollowcinema() == 1) {
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
                    adapter.getItem(o).setFollowcinema(1);
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
                adapter.getItem(o).setFollowcinema(2);
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
