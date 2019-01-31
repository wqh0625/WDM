package com.bw.movie.view.myactivity.kffragment;

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
import com.bw.movie.bean.ObligationData;
import com.bw.movie.bean.Result;
import com.bw.movie.core.adapter.CompletedAdapter;
import com.bw.movie.core.adapter.ObligationAdapter;
import com.bw.movie.core.base.BaseFragment;
import com.bw.movie.core.exception.ApiException;
import com.bw.movie.core.interfase.DataCall;
import com.bw.movie.presenter.ObligationPresenter;

/**
 * 作者: Wang on 2019/1/25 14:15
 * 寄语：加油！相信自己可以！！！
 */


public class YwcFinishFragment extends BaseFragment {

    private Unbinder bind;

    @BindView(R.id.ywc_rec)
    RecyclerView rec;
    private int userId;
    private String sessionId;
    private ObligationPresenter obligationPresenter;
    private CompletedAdapter completedAdapter;

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        bind.unbind();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_ywcfinish;
    }

    @Override
    protected void initView(View view) {
        bind = ButterKnife.bind(this, view);
        initData();
    }

    private void initData() {
        userId = userInfoBean.getUserId();
        sessionId = userInfoBean.getSessionId();
        obligationPresenter = new ObligationPresenter(new ObligatonCall());
        completedAdapter = new CompletedAdapter(getActivity());
        rec.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        obligationPresenter.requestNet(userId,sessionId,1,10,2);
        rec.setAdapter(completedAdapter);
    }
    class ObligatonCall implements DataCall<Result<List<ObligationData>>> {

        @Override
        public void success(Result<List<ObligationData>> data) {
            if (data.getStatus().equals("0000")){
                Toast.makeText(getActivity(), ""+data.getMessage(), Toast.LENGTH_SHORT).show();
                completedAdapter.addAll(data.getResult());
                completedAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void fail(ApiException a) {
            Toast.makeText(getActivity(), "查询失败", Toast.LENGTH_SHORT).show();
        }
    }
}
