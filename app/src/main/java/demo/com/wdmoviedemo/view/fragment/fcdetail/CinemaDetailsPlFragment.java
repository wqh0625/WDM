package demo.com.wdmoviedemo.view.fragment.fcdetail;

import android.os.Bundle;
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
import demo.com.wdmoviedemo.bean.CommentData;
import demo.com.wdmoviedemo.bean.Result;
import demo.com.wdmoviedemo.core.adapter.CommentAdapter;
import demo.com.wdmoviedemo.core.base.BaseFragment;
import demo.com.wdmoviedemo.core.exception.ApiException;
import demo.com.wdmoviedemo.core.interfase.DataCall;
import demo.com.wdmoviedemo.presenter.ComingPresenter;
import demo.com.wdmoviedemo.presenter.CommentPresenter;

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
//        cinemaId = getArguments().getInt("cinemaId");
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
    }
}
