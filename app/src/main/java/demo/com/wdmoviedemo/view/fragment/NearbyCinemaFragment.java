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
import demo.com.wdmoviedemo.presenter.FindNearbyCinemasPresenter;
import demo.com.wdmoviedemo.view.LoginActivity;

/**
 * 作者: Wang on 2019/1/25 18:28
 * 寄语：加油！相信自己可以！！！
 */


public class NearbyCinemaFragment extends BaseFragment implements XRecyclerView.LoadingListener {
    @BindView(R.id.nearby_rec)
    XRecyclerView rec;


    private NearbyCinemaAdapter adapter;
    private FindNearbyCinemasPresenter findNearbyCinemasPresenter;

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
        rec.setAdapter(adapter);
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
        findNearbyCinemasPresenter.requestNet(0, "", true);
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

    class find implements DataCall<Result<List<NearbyData>>> {
        @Override
        public void success(Result<List<NearbyData>> data) {
            rec.refreshComplete();
            rec.loadMoreComplete();
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
    }
}
