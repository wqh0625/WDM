package demo.com.wdmoviedemo.view.fragment.fcdetail;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import demo.com.wdmoviedemo.bean.DetailsData;
import demo.com.wdmoviedemo.bean.Result;
import demo.com.wdmoviedemo.core.base.BaseFragment;
import demo.com.wdmoviedemo.core.exception.ApiException;
import demo.com.wdmoviedemo.core.interfase.DataCall;
import demo.com.wdmoviedemo.presenter.DetailsPresenter;

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
        DetailsPresenter detailsPresenter  = new DetailsPresenter(new DetailsCall());
        detailsPresenter.requestNet(userId,sessionId,19);
    }
    class DetailsCall implements DataCall<Result<DetailsData>>{

        private DetailsData result;

        @Override
        public void success(Result<DetailsData> data) {
            if (data.getStatus().equals("0000")){
                Toast.makeText(getActivity(), ""+data.getMessage(), Toast.LENGTH_SHORT).show();
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

}
