package demo.com.wdmoviedemo.view.myactivity.gzfragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bw.movie.R;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import demo.com.wdmoviedemo.bean.FindCinemaPageListData;
import demo.com.wdmoviedemo.bean.NearbyData;
import demo.com.wdmoviedemo.bean.Result;
import demo.com.wdmoviedemo.core.adapter.GzCinemaAdapter;
import demo.com.wdmoviedemo.core.base.BaseFragment;
import demo.com.wdmoviedemo.core.exception.ApiException;
import demo.com.wdmoviedemo.core.interfase.DataCall;
import demo.com.wdmoviedemo.presenter.FindCinemaPageListPresenter;

/**
 * 作者: Wang on 2019/1/25 14:15
 * 寄语：加油！相信自己可以！！！
 */


public class GzCinemaFragment extends BaseFragment implements XRecyclerView.LoadingListener {

    @BindView(R.id.gzcinema_rec)
    XRecyclerView rec;
    private int userId;
    private String sessionId;
    private FindCinemaPageListPresenter findCinemaPageListPresenter;
    private GzCinemaAdapter gzCinemaAdapter;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_gzcinema;
    }

    @Override
    protected void initView(View view) {
        rec.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rec.setLoadingListener(this);
        rec.setLoadingMoreEnabled(true);

        // 适配器
        gzCinemaAdapter = new GzCinemaAdapter(getActivity());
        rec.setAdapter(gzCinemaAdapter);

        findCinemaPageListPresenter = new FindCinemaPageListPresenter(new find());
    }

    @Override
    public void onResume() {
        super.onResume();
        userId = userInfoBean.getUserId();
        sessionId = userInfoBean.getSessionId();
        if (sessionId.length() > 0 && userId != 0) {
            rec.refresh();
        } else {
            userId = 0;
            sessionId = "";
        }
    }

    @Override
    public void onRefresh() {
        if (findCinemaPageListPresenter.isRuning()) {
            rec.refreshComplete();
            return;
        }
        rec.refreshComplete();
        rec.loadMoreComplete();
        findCinemaPageListPresenter.requestNet(userId, sessionId, true);
    }

    @Override
    public void onLoadMore() {
        if (findCinemaPageListPresenter.isRuning()) {
            rec.loadMoreComplete();
            return;
        }
        rec.refreshComplete();
        rec.loadMoreComplete();
//        findCinemaPageListPresenter.requestNet(userId,sessionId,false);
    }

    class find implements DataCall<Result<List<FindCinemaPageListData>>> {
        @Override
        public void success(Result<List<FindCinemaPageListData>> data) {
            rec.refreshComplete();
            rec.loadMoreComplete();
            Toast.makeText(getContext(), "11111111"+data.getMessage(), Toast.LENGTH_SHORT).show();

            if (data.getStatus().equals("0000")) {
                gzCinemaAdapter.setListData(data.getResult());
                gzCinemaAdapter.notifyDataSetChanged();
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
        findCinemaPageListPresenter.unBind();
    }
}
