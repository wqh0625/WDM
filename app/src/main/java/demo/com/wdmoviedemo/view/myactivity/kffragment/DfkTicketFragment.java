package demo.com.wdmoviedemo.view.myactivity.kffragment;

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

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import demo.com.wdmoviedemo.bean.ObligationData;
import demo.com.wdmoviedemo.bean.Result;
import demo.com.wdmoviedemo.core.adapter.ObligationAdapter;
import demo.com.wdmoviedemo.core.base.BaseFragment;
import demo.com.wdmoviedemo.core.exception.ApiException;
import demo.com.wdmoviedemo.core.interfase.DataCall;
import demo.com.wdmoviedemo.presenter.ObligationPresenter;

/**
 * 作者: Wang on 2019/1/25 14:15
 * 寄语：加油！相信自己可以！！！
 * 待付款
 */


public class DfkTicketFragment extends BaseFragment {

    private Unbinder bind;

    @BindView(R.id.dfk_rec)
    RecyclerView rec;
    private ObligationPresenter obligationPresenter;
    private int userId;
    private String sessionId;
    private ObligationAdapter obligationAdapter;

    private void initData() {
        userId = userInfoBean.getUserId();
        sessionId = userInfoBean.getSessionId();
        obligationPresenter = new ObligationPresenter(new ObligatonCall());
        obligationAdapter = new ObligationAdapter(getActivity());
        rec.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        obligationPresenter.requestNet(userId,sessionId,1,10,1);
        rec.setAdapter(obligationAdapter);

    }
    class ObligatonCall implements DataCall<Result<List<ObligationData>>>{

        @Override
        public void success(Result<List<ObligationData>> data) {
            if (data.getStatus().equals("0000")){
                Toast.makeText(getActivity(), ""+data.getMessage(), Toast.LENGTH_SHORT).show();
                obligationAdapter.addAll(data.getResult());
                obligationAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void fail(ApiException a) {
            Toast.makeText(getActivity(), "查询失败", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        bind.unbind();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_dfkticket;
    }

    @Override
    protected void initView(View view) {
        bind = ButterKnife.bind(this, view);
        initData();

    }
}
