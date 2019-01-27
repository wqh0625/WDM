package demo.com.wdmoviedemo.view.cinemaactivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import demo.com.wdmoviedemo.bean.CinemaDetailListData;
import demo.com.wdmoviedemo.bean.CinemaDetailListDataBottom;
import demo.com.wdmoviedemo.bean.Result;
import demo.com.wdmoviedemo.core.adapter.CinemaDetailAdapter;
import demo.com.wdmoviedemo.core.base.BaseActivity;
import demo.com.wdmoviedemo.core.exception.ApiException;
import demo.com.wdmoviedemo.core.interfase.DataCall;
import demo.com.wdmoviedemo.presenter.FindMovieListByCinemaIdPresenter;
import demo.com.wdmoviedemo.presenter.FindMovieScheduleListPresenter;
import recycler.coverflow.CoverFlowLayoutManger;
import recycler.coverflow.RecyclerCoverFlow;

/**
 * 作者: Wang on 2019/1/26 09:46
 * 寄语：加油！相信自己可以！！！
 */


public class CinemaDetailActivity extends BaseActivity {

    private FindMovieListByCinemaIdPresenter findMovieListByCinemaIdPresenter;
    private FindMovieScheduleListPresenter findMovieScheduleListPresenter;
    @BindView(R.id.cinema_detail_recy_carousel)
    RecyclerCoverFlow recycarousel;
    @BindView(R.id.cinema_detail_image_icon)
    SimpleDraweeView icon;
    @BindView(R.id.cinema_detail_address)
    TextView addtess;
    @BindView(R.id.cinema_detail_text_title)
    TextView title;
    private CinemaDetailAdapter cinemaDetailAdapter;
    private List<CinemaDetailListData> resuleeee;
    private int cinemaId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cinema_detail);
        ButterKnife.bind(this);
        //根据影院ID查询该影院当前排期的电影列表
        findMovieListByCinemaIdPresenter = new FindMovieListByCinemaIdPresenter(new find());
        Intent intent = getIntent();
        cinemaId = intent.getIntExtra("cinemaID", 0);
        Log.v("cinemaId","cinemaId"+ cinemaId);
        String imageUrl = intent.getStringExtra("imageUrl");
        String address = intent.getStringExtra("address");
        String cinameName = intent.getStringExtra("cinameName");
        icon.setImageURI(Uri.parse(imageUrl));
        addtess.setText(address);
        title.setText(cinameName);
        findMovieListByCinemaIdPresenter.requestNet(cinemaId);
        // 根据电影ID和影院ID查询电影排期列表
        findMovieScheduleListPresenter = new FindMovieScheduleListPresenter(new find222());

        cinemaDetailAdapter = new CinemaDetailAdapter(this);
        //设置适配器
        recycarousel.setAdapter(cinemaDetailAdapter);
        //滑动监听

        recycarousel.setOnItemSelectedListener(new CoverFlowLayoutManger.OnSelected() {
            @Override
            public void onItemSelected(int position) {
                CinemaDetailListData cinemaDetailListData = resuleeee.get(position);

                findMovieScheduleListPresenter.requestNet(cinemaDetailListData.getId());
            }
        });

    }

    class find implements DataCall<Result<List<CinemaDetailListData>>> {
        @Override
        public void success(Result<List<CinemaDetailListData>> data) {

            if (data.getStatus().equals("0000")) {
                Log.v("影院详情", data.getResult().size() + "");
                resuleeee = data.getResult();
                int id = resuleeee.get(0).getId();

                findMovieScheduleListPresenter.requestNet(id);
                cinemaDetailAdapter.setList(data.getResult());
                cinemaDetailAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void fail(ApiException a) {

        }
    }

    class find222 implements DataCall<Result<List<CinemaDetailListDataBottom>>> {
        @Override
        public void success(Result<List<CinemaDetailListDataBottom>> data) {
            Toast.makeText(CinemaDetailActivity.this, "下下下下下" + data.getMessage() + data.getResult().size(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void fail(ApiException a) {

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        findMovieListByCinemaIdPresenter.unBind();
        findMovieScheduleListPresenter.unBind();
    }
}
