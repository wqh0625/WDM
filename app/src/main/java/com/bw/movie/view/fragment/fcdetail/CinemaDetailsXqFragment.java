package com.bw.movie.view.fragment.fcdetail;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.bean.DetailsData;
import com.bw.movie.bean.Result;
import com.bw.movie.core.base.BaseFragment;
import com.bw.movie.core.exception.ApiException;
import com.bw.movie.core.interfase.DataCall;
import com.bw.movie.presenter.DetailsPresenter;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 作者: Wang on 2019/1/27 16:46
 * 寄语：加油！相信自己可以！！！
 * 影院详情页
 */


public class CinemaDetailsXqFragment extends BaseFragment {
    @BindView(R.id.txt_dw)
    TextView txtDw;
    @BindView(R.id.txt_phone)
    TextView txtPhone;
    @BindView(R.id.txt_lx)
    TextView txtLx;
    @BindView(R.id.txt_dt)
    TextView txtDt;
    @BindView(R.id.txt_gj)
    TextView txtGj;
    @BindView(R.id.txt_zj)
    TextView txtZj;
    Unbinder unbinder;
    Unbinder unbinder1;
    private int userId;
    private String sessionId;
    private int cinemaId;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_cinema_details_xq;
    }

    @Override
    protected void initView(View view) {
        unbinder = ButterKnife.bind(this, view);
        initData();
    }

    private void initData() {
        userId = userInfoBean.getUserId();
        sessionId = userInfoBean.getSessionId();
        DetailsPresenter detailsPresenter = new DetailsPresenter(new DetailsCall());
        detailsPresenter.requestNet(userId, sessionId, 19);
    }


    class DetailsCall implements DataCall<Result<DetailsData>> {

        private DetailsData result;

        @Override
        public void success(Result<DetailsData> data) {
            if (data.getStatus().equals("0000")) {
                Toast.makeText(getActivity(), "" + data.getMessage(), Toast.LENGTH_SHORT).show();
                result = data.getResult();
                txtDw.setText(result.getAddress());
                txtDt.setText(result.getAddress());
                txtPhone.setText(result.getPhone());
                txtLx.setText(result.getVehicleRoute());
                txtDt.setText(result.getVehicleRoute());
                txtGj.setText(result.getAddress());
                txtZj.setText(result.getVehicleRoute());
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
        //取消订阅
        EventBus.getDefault().unregister(this);

    }

    @OnClick(R.id.txt_phone)
    public void onViewClicked() {
        Intent intentPhone = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:"+txtPhone);
        intentPhone.setData(data);
        startActivity(intentPhone);
    }
}
