package demo.com.wdmoviedemo.view.cinemaactivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import demo.com.wdmoviedemo.bean.TicketDetailsData;
import demo.com.wdmoviedemo.core.adapter.CinemaDetailAdapter;
import demo.com.wdmoviedemo.core.adapter.TicketDetailsAdapter;
import demo.com.wdmoviedemo.core.base.BaseActivity;
import demo.com.wdmoviedemo.core.exception.ApiException;
import demo.com.wdmoviedemo.core.interfase.DataCall;
import demo.com.wdmoviedemo.presenter.FindCinemasListByMovieIdPresenter;
import demo.com.wdmoviedemo.presenter.FindMovieListByCinemaIdPresenter;
import demo.com.wdmoviedemo.view.detailsactvity.CheckInActivity;
import demo.com.wdmoviedemo.view.detailsactvity.TicketDetailsActivity;
import recycler.coverflow.CoverFlowLayoutManger;
import recycler.coverflow.RecyclerCoverFlow;

/**
 * 作者: Wang on 2019/1/26 09:46
 * 寄语：加油！相信自己可以！！！
 */


public class CinemaDetailActivity extends BaseActivity {

    private FindMovieListByCinemaIdPresenter findMovieListByCinemaIdPresenter;
    private FindCinemasListByMovieIdPresenter findMovieScheduleListPresenter;
    @BindView(R.id.cinema_detail_recy_carousel)
    RecyclerCoverFlow recycarousel;
    @BindView(R.id.cinema_detail_image_icon)
    SimpleDraweeView icon;
    @BindView(R.id.cinema_detail_address)
    TextView addtess;
    @BindView(R.id.cinema_detail_text_title)
    TextView title;
    @BindView(R.id.cinema_detail_rec)
    RecyclerView rec;
    private CinemaDetailAdapter cinemaDetailAdapter;
    private List<CinemaDetailListData> resuleeee;
    private int cinemaId;
    private TicketDetailsAdapter ticketDetailsAdapter;
    private int position;
    private int id;
    private String name;
    private String movieTypes;
    private String director;
    private String duration;
    private String placeOrigin;
    private String address;
    private String imageUrl;
    private String names;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cinema_detail);
        ButterKnife.bind(this);
        initView();


        ticketDetailsAdapter = new TicketDetailsAdapter(this);
        rec.setAdapter(ticketDetailsAdapter);
        rec.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        findMovieListByCinemaIdPresenter.requestNet(cinemaId);
        // 根据电影ID和影院ID查询电影排期列表
        findMovieScheduleListPresenter = new FindCinemasListByMovieIdPresenter(new find222());

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

        //接口回调传值跳转选座页
        ticketDetailsAdapter.setOnImageClickLisener(new TicketDetailsAdapter.OnImageClickLisener() {
            @Override
            public void onImageClick(String ScreeningHall, String BeginTime, String EndTime, double Price) {
                Intent intent = new Intent(CinemaDetailActivity.this, CheckInActivity.class);
                intent.putExtra("name", name);
                intent.putExtra("address", address);
                intent.putExtra("names", names);
                intent.putExtra("ScreeningHall", ScreeningHall);
                intent.putExtra("BeginTime", BeginTime);
                intent.putExtra("EndTime", EndTime);
                intent.putExtra("Price", Price);
                startActivity(intent);
            }
        });
    }

    private void initView() {
        //接受传过来的值
        Intent intent = getIntent();
        position = intent.getExtras().getInt("position");
        id = intent.getExtras().getInt("id");
        name = intent.getExtras().getString("name");
        names = intent.getExtras().getString("names");
        address = intent.getExtras().getString("address");
        imageUrl = intent.getExtras().getString("imageUrl");
        movieTypes = intent.getExtras().getString("movieTypes");
        director = intent.getExtras().getString("director");
        duration = intent.getExtras().getString("duration");
        placeOrigin = intent.getExtras().getString("placeOrigin");
        //根据影院ID查询该影院当前排期的电影列表
        findMovieListByCinemaIdPresenter = new FindMovieListByCinemaIdPresenter(new find());
        cinemaId = intent.getIntExtra("CcinemaID", 0);
        Log.v("cinemaId", "cinemaId" + cinemaId);
        String imageUrl = intent.getStringExtra("CimageUrl");
        String addre = intent.getStringExtra("Caddress");
        String cinameName = intent.getStringExtra("CcinameName");
        icon.setImageURI(Uri.parse(imageUrl));
        addtess.setText(addre);
        title.setText(cinameName);
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

    class find222 implements DataCall<Result<List<TicketDetailsData>>> {
        @Override
        public void success(Result<List<TicketDetailsData>> data) {
            Toast.makeText(CinemaDetailActivity.this, "下下下下下" + data.getMessage() + data.getResult().size(), Toast.LENGTH_SHORT).show();
            if (data.getStatus().equals("0000")) {
                ticketDetailsAdapter.addAll(data.getResult());
                ticketDetailsAdapter.notifyDataSetChanged();
            }
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
