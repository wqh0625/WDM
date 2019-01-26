package demo.com.wdmoviedemo.view.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.bw.movie.R;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.List;

import javax.xml.parsers.FactoryConfigurationError;

import butterknife.BindView;
import demo.com.wdmoviedemo.bean.NearbyData;
import demo.com.wdmoviedemo.bean.Result;
import demo.com.wdmoviedemo.core.adapter.NearbyCinemaAdapter;
import demo.com.wdmoviedemo.core.base.BaseFragment;
import demo.com.wdmoviedemo.core.exception.ApiException;
import demo.com.wdmoviedemo.core.interfase.DataCall;
import demo.com.wdmoviedemo.presenter.FindRecommendCinemasPresenter;

/**
 * 作者: Wang on 2019/1/25 18:28
 * 寄语：加油！相信自己可以！！！
 */


public class NearbyCinemaFragment extends BaseFragment implements XRecyclerView.LoadingListener {
    @BindView(R.id.nearby_rec)
    XRecyclerView rec;
    private FindRecommendCinemasPresenter findRecommendCinemasPresenter;
    private int userId;
    private String sessionId;
    private NearbyCinemaAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_nearbycinema;
    }

    @Override
    protected void initView(View view) {
        rec.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rec.setLoadingListener(this);
//        rec.setLoadingMoreEnabled(true);


        findRecommendCinemasPresenter = new FindRecommendCinemasPresenter(new find());
        adapter = new NearbyCinemaAdapter(getContext());
        rec.setAdapter(adapter);
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
//        findRecommendCinemasPresenter.requestNet(userId,sessionId,false);
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
        findRecommendCinemasPresenter.unBind();
    }
}
