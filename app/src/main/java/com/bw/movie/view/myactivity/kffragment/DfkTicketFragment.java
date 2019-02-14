package com.bw.movie.view.myactivity.kffragment;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.View;


import com.bw.movie.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.bw.movie.bean.ObligationData;
import com.bw.movie.bean.Result;
import com.bw.movie.core.adapter.ObligationAdapter;
import com.bw.movie.core.base.BaseFragment;
import com.bw.movie.core.exception.ApiException;
import com.bw.movie.core.interfase.DataCall;
import com.bw.movie.presenter.ObligationPresenter;

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

                obligationAdapter.addAll(data.getResult());
                obligationAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void fail(ApiException a) {
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
