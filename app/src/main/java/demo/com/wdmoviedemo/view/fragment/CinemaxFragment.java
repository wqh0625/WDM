package demo.com.wdmoviedemo.view.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.bw.movie.R;

import java.sql.SQLException;
import java.util.List;

import demo.com.wdmoviedemo.bean.CarouselData;
import demo.com.wdmoviedemo.bean.Result;
import demo.com.wdmoviedemo.core.adapter.CinemaxAdapters;
import demo.com.wdmoviedemo.core.base.BaseFragment;
import demo.com.wdmoviedemo.core.dao.DbManager;
import demo.com.wdmoviedemo.core.exception.ApiException;
import demo.com.wdmoviedemo.core.interfase.DataCall;
import demo.com.wdmoviedemo.core.utils.MyApp;
import demo.com.wdmoviedemo.presenter.CancelConcernPresenter;
import demo.com.wdmoviedemo.presenter.CarouselPresenter;
import demo.com.wdmoviedemo.presenter.ConcernPresenter;
import demo.com.wdmoviedemo.view.Film_Details_Activity;
import demo.com.wdmoviedemo.view.HomeActivity;
import demo.com.wdmoviedemo.view.LoginActivity;

import static demo.com.wdmoviedemo.core.utils.MyApp.getContext;

public class CinemaxFragment extends BaseFragment {
    private RecyclerView cinemaxrecy;
    private CinemaxAdapters cinemaxAdapter;
    private CarouselPresenter carouselPresenter;

    private ConcernPresenter concernPresenter;
    private CancelConcernPresenter cancelConcernPresenter;

    private void initData() {


        cinemaxAdapter = new CinemaxAdapters(getActivity(), CinemaxAdapters.CAROUSEL_TYPE);
        cinemaxrecy.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        cinemaxrecy.setAdapter(cinemaxAdapter);
        carouselPresenter = new CarouselPresenter(new CinemaxCall());

        if (userInfoBean == null) {
            carouselPresenter.requestNet(0, "", 1, 20);
        } else {
            carouselPresenter.requestNet(userInfoBean.getUserId(), userInfoBean.getSessionId(), 1, 20);
        }
        cinemaxAdapter.setOnMovieItemClickListener(new CinemaxAdapters.OnCinemaxItemClickListener() {
            @Override
            public void onMovieClick(int position) {
                Intent intent = new Intent(getActivity(), Film_Details_Activity.class);
                intent.putExtra("position", position);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.ac_in, R.anim.ac_out);
            }
        });
        cinemaxAdapter.setOnImageClickListener(new CinemaxAdapters.OnImageClickListener() {

            @Override
            public void OnImageClick(int i, int pos, CarouselData carouselData) {
                if (userInfoBean == null || userInfoBean.getSessionId() == "") {
                    DbManager dbManager = null;
                    try {
                        dbManager = new DbManager(getContext());
                        int ii = dbManager.deleteStudentByS(userInfoBean);
                        Toast.makeText(getContext(), "" + ii, Toast.LENGTH_SHORT).show();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    Intent intent = new Intent(MyApp.getContext(), LoginActivity.class);
                    // 跳转
                    startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.ac_in, R.anim.ac_out);
                } else {
                    if (carouselData.getFollowMovie() == 1) {
                        cancelConcernPresenter.requestNet(userInfoBean.getUserId(), userInfoBean.getSessionId(), carouselData.getId(), pos, i);
                    } else {
                        concernPresenter.requestNet(userInfoBean.getUserId(), userInfoBean.getSessionId(), carouselData.getId(), pos, i);
                    }
                }
            }
        });
    }

    // 得到列表信息
    class CinemaxCall implements DataCall<Result<List<CarouselData>>> {

        @Override
        public void success(Result<List<CarouselData>> data) {
            if (data.getStatus().equals("9999")) {
                Intent intent = new Intent(MyApp.getContext(), LoginActivity.class);
                // 跳转
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.ac_in, R.anim.ac_out);
            }
            if (data.getStatus().equals("0000")) {
                cinemaxAdapter.addAll(data.getResult());
                cinemaxAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void fail(ApiException a) {
            Toast.makeText(getContext(), "d失败", Toast.LENGTH_SHORT).show();
        }
    }

    //关注
    class ConcernCall implements DataCall<Result> {

        @Override
        public void success(Result data) {
            if (data.getStatus().equals("9999")) {
                Intent intent = new Intent(MyApp.getContext(), LoginActivity.class);
                // 跳转
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.ac_in, R.anim.ac_out);
            }

            if (data.getStatus().equals("0000")) {
                int po = (int) data.getArgs()[4];
                cinemaxAdapter.getItem(po).setFollowMovie(1);
                cinemaxAdapter.notifyItemChanged(po);

            }
        }

        @Override
        public void fail(ApiException a) {
            Toast.makeText(getActivity(), "gggg失败", Toast.LENGTH_SHORT).show();
        }
    }

    //取消关注
    class CancelCall implements DataCall<Result> {

        @Override
        public void success(Result data) {
            Toast.makeText(getActivity(), "0000" + data.getMessage(), Toast.LENGTH_SHORT).show();
            if (data.getStatus().equals("9999")) {
                Intent intent = new Intent(MyApp.getContext(), LoginActivity.class);
                // 跳转
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.ac_in, R.anim.ac_out);
            }

            if (data.getStatus().equals("0000")) {
                int po = (int) data.getArgs()[4];
                cinemaxAdapter.getItem(po).setFollowMovie(2);
                cinemaxAdapter.notifyItemChanged(po);
            }
        }

        @Override
        public void fail(ApiException a) {
            Toast.makeText(getContext(), "q失败", Toast.LENGTH_SHORT).show();
        }
    }


    private void init(View view) {
        cinemaxrecy = view.findViewById(R.id.cinemax_recy);
        concernPresenter = new ConcernPresenter(new ConcernCall());
        cancelConcernPresenter = new CancelConcernPresenter(new CancelCall());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        carouselPresenter.unBind();
        cancelConcernPresenter.unBind();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_cinemax;
    }

    @Override
    protected void initView(View view) {
        init(view);
        initData();
    }
}
