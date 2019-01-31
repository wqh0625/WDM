package com.bw.movie.view.myactivity.gzfragment;


import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;
import com.bw.movie.R;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.bw.movie.bean.FindCinemaPageListData;
import com.bw.movie.bean.FindMoviePageListData;
import com.bw.movie.bean.Result;
import com.bw.movie.core.adapter.GzMovieAdapter;
import com.bw.movie.core.base.BaseFragment;
import com.bw.movie.core.exception.ApiException;
import com.bw.movie.core.interfase.DataCall;
import com.bw.movie.presenter.FindMoviePageListPresenter;

/**
 * 作者: Wang on 2019/1/25 14:15
 * 寄语：加油！相信自己可以！！！
 */


public class GzMoiveFragment extends BaseFragment implements XRecyclerView.LoadingListener {

    @BindView(R.id.gzmovie_rec)
    XRecyclerView rec;
    private FindMoviePageListPresenter findMoviePageListPresenter;
    private GzMovieAdapter gzMovieAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_gzmovie;
    }

    @Override
    protected void initView(View view) {
        rec.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rec.setLoadingListener(this);
        rec.setLoadingMoreEnabled(true);

        gzMovieAdapter = new GzMovieAdapter(getActivity());
        rec.setAdapter(gzMovieAdapter);

        findMoviePageListPresenter = new FindMoviePageListPresenter(new find());
        findMoviePageListPresenter.requestNet(userInfoBean.getUserId(), userInfoBean.getSessionId(), true);
    }

    @Override
    public void onResume() {
        super.onResume();


    }

    @Override
    public void onRefresh() {
        if (findMoviePageListPresenter.isRuning()) {
            rec.refreshComplete();
            return;
        }
        rec.refreshComplete();
        rec.loadMoreComplete();
        findMoviePageListPresenter.requestNet(userInfoBean.getUserId(), userInfoBean.getSessionId(), true);
    }

    @Override
    public void onLoadMore() {
        if (findMoviePageListPresenter.isRuning()) {
            rec.loadMoreComplete();
            return;
        }
        rec.refreshComplete();
        rec.loadMoreComplete();
//        findCinemaPageListPresenter.requestNet(userId,sessionId,false);
    }

    class find implements DataCall<Result<List<FindMoviePageListData>>> {
        @Override
        public void success(Result<List<FindMoviePageListData>> data) {
            rec.refreshComplete();
            rec.loadMoreComplete();
            Toast.makeText(getContext(), ""+data.getMessage(), Toast.LENGTH_SHORT).show();
            if (data.getStatus().equals("0000")) {
                if (data.getResult().size() == 0) {
                    return;
                }
                gzMovieAdapter.setListData(data.getResult());
                gzMovieAdapter.notifyDataSetChanged();
            }
//            Toast.makeText(getContext(), "" + data.getMessage(), Toast.LENGTH_SHORT).show();

        }

        @Override
        public void fail(ApiException a) {
            Toast.makeText(getContext(), "失败", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        findMoviePageListPresenter.unBind();
    }
}
