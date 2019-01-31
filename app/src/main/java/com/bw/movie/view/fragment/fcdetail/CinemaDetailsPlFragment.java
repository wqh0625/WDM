package com.bw.movie.view.fragment.fcdetail;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.bw.movie.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.bw.movie.bean.CommentData;
import com.bw.movie.bean.Result;
import com.bw.movie.core.adapter.CommentAdapter;
import com.bw.movie.core.base.BaseFragment;
import com.bw.movie.core.exception.ApiException;
import com.bw.movie.core.interfase.DataCall;
import com.bw.movie.presenter.CommentPresenter;

/**
 * 作者: Wang on 2019/1/27 16:46
 * 寄语：加油！相信自己可以！！！
 * 影院评论
 */


public class CinemaDetailsPlFragment extends BaseFragment {
    @BindView(R.id.details_pl_recy)
    RecyclerView detailsPlRecy;
    Unbinder unbinder;
    private CommentAdapter commentAdapter;
    private CommentPresenter commentPresenter;
    private int userId;
    private String sessionId;
    private int cinemaId;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_cinema_details_pl;
    }

    @Override
    protected void initView(View view) {
        unbinder = ButterKnife.bind(this, view);
        initData();

    }

    private void initData() {
        userId = userInfoBean.getUserId();
        sessionId = userInfoBean.getSessionId();
        commentAdapter = new CommentAdapter(getActivity());
        detailsPlRecy.setAdapter(commentAdapter);
        detailsPlRecy.setLayoutManager(new LinearLayoutManager(getActivity()));
        commentPresenter = new CommentPresenter(new CommentCall());
        commentPresenter.requestNet(userId,sessionId,19,1,20);
    }
    class CommentCall implements DataCall<Result<List<CommentData>>>{

        @Override
        public void success(Result<List<CommentData>> data) {
            if (data.getStatus().equals("0000")){
                Toast.makeText(getActivity(), ""+data.getMessage(), Toast.LENGTH_SHORT).show();
                commentAdapter.addAll(data.getResult());
                commentAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void fail(ApiException a) {
            Toast.makeText(getActivity(), "失败", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
    }
}
