package demo.com.wdmoviedemo.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bw.movie.R;

import java.util.List;

import demo.com.wdmoviedemo.bean.CarouselData;
import demo.com.wdmoviedemo.bean.Result;
import demo.com.wdmoviedemo.core.adapter.CinemaxAdapters;
import demo.com.wdmoviedemo.core.base.BaseFragment;
import demo.com.wdmoviedemo.core.exception.ApiException;
import demo.com.wdmoviedemo.core.interfase.DataCall;
import demo.com.wdmoviedemo.presenter.ConcernPresenter;
import demo.com.wdmoviedemo.presenter.IsHitPresenter;
import demo.com.wdmoviedemo.view.Film_Details_Activity;
import demo.com.wdmoviedemo.view.LoginActivity;

public class IsHitFragment extends BaseFragment {
    private RecyclerView ishitRecy;
    private CinemaxAdapters cinemaxAdapter;
    private IsHitPresenter isHitPresenter;
    private int userId;
    private String sessionId;

    private void initData() {
        userId = userInfoBean.getUserId();
        sessionId = userInfoBean.getSessionId();
        cinemaxAdapter = new CinemaxAdapters(getActivity(),CinemaxAdapters.ISHIT_TYPE);
        ishitRecy.setAdapter(cinemaxAdapter);
        isHitPresenter = new IsHitPresenter(new IsHitCall());
        ishitRecy.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        isHitPresenter.requestNet(1,10);
        cinemaxAdapter.setOnMovieItemClickListener(new CinemaxAdapters.OnCinemaxItemClickListener() {
            @Override
            public void onMovieClick(int position) {
                Intent intent = new Intent(getActivity(),Film_Details_Activity.class);
                intent.putExtra("position",position);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.ac_in, R.anim.ac_out);
            }
        });
        cinemaxAdapter.setOnImageClickListener(new CinemaxAdapters.OnImageClickListener() {
            private ConcernPresenter concernPresenter;

            @Override
            public void OnImageClick(int position, CarouselData carouselData) {
                if (userId ==0 || sessionId==null || sessionId==""){
                    Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(),LoginActivity.class);
                    startActivity(intent);
                }else {
                    if (carouselData.getFollowMovie() ==2){
                        concernPresenter = new ConcernPresenter(new ConcernCall());
                        concernPresenter.requestNet(userId,sessionId,position);
                    }else {
                        Toast.makeText(getActivity(), "已关注", Toast.LENGTH_SHORT).show();
                    }
                }
            }


        });
    }
    class IsHitCall implements DataCall<Result<List<CarouselData>>> {

        @Override
        public void success(Result<List<CarouselData>> data) {
            if (data.getStatus().equals("0000")){
                cinemaxAdapter.addAll(data.getResult());
                cinemaxAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void fail(ApiException a) {

        }
    }
    //关注
    class ConcernCall implements DataCall<Result>{

        @Override
        public void success(Result data) {
            if (data.getStatus().equals("0000")){
                Toast.makeText(getActivity(), "关注成功", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void fail(ApiException a) {
            Toast.makeText(getActivity(), "关注失败", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isHitPresenter.unBind();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_is_hit;
    }

    @Override
    protected void initView(View view) {
        ishitRecy = (RecyclerView) view.findViewById(R.id.ishit_recy);
        initData();

    }
}
