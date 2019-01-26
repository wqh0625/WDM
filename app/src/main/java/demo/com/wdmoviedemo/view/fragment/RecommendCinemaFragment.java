package demo.com.wdmoviedemo.view.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.bw.movie.R;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.List;

import butterknife.BindView;
import demo.com.wdmoviedemo.bean.NearbyData;
import demo.com.wdmoviedemo.bean.Result;
import demo.com.wdmoviedemo.core.adapter.NearbyCinemaAdapter;
import demo.com.wdmoviedemo.core.base.BaseFragment;
import demo.com.wdmoviedemo.core.exception.ApiException;
import demo.com.wdmoviedemo.core.interfase.DataCall;
import demo.com.wdmoviedemo.presenter.FindRecommendCinemasPresenter;

/**
 * 作者: Wang on 2019/1/25 18:33
 * 寄语：加油！相信自己可以！！！
 */


public class RecommendCinemaFragment extends BaseFragment implements XRecyclerView.LoadingListener {

    @BindView(R.id.recommend_rec)
    XRecyclerView rec;
    private int userId;
    private String sessionId;
    private NearbyCinemaAdapter adapter;
    private FindRecommendCinemasPresenter findRecommendCinemasPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_recommendcinema;
    }

    @Override
    protected void initView(View view) {
        rec.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rec.setLoadingListener(this);

        findRecommendCinemasPresenter = new FindRecommendCinemasPresenter(new find());

        adapter = new NearbyCinemaAdapter(getContext());
        rec.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        userId = userInfoBean.getUserId();
        sessionId = userInfoBean.getSessionId();
        if (sessionId != "" && userId != 0) {
            rec.refresh();
        } else {
            userId = 0;
            sessionId = "";
        }
    }

    @Override
    public void onRefresh() {
        if (findRecommendCinemasPresenter.isRuning()) {
            rec.refreshComplete();
            return;
        }
        rec.refreshComplete();
        rec.loadMoreComplete();
        findRecommendCinemasPresenter.requestNet(userId, sessionId, true);
    }

    @Override
    public void onLoadMore() {
        if (findRecommendCinemasPresenter.isRuning()) {
            rec.loadMoreComplete();
            return;
        }
        rec.refreshComplete();
        rec.loadMoreComplete();
//        findRecommendCinemasPresenter.requestNet(userId, sessionId, true);
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

        }

        @Override
        public void fail(ApiException a) {

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        findRecommendCinemasPresenter.unBind();
    }
}
