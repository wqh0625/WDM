package com.bw.movie.view.cinemaactivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.bw.movie.bean.CinemaDetailListData;
import com.bw.movie.bean.Result;
import com.bw.movie.bean.TicketDetailsData;
import com.bw.movie.core.adapter.CinemaDetailAdapter;
import com.bw.movie.core.adapter.ReviewAdapter;
import com.bw.movie.core.adapter.TicketDetailsAdapter;
import com.bw.movie.core.base.BaseActivity;
import com.bw.movie.core.exception.ApiException;
import com.bw.movie.core.interfase.DataCall;
import com.bw.movie.presenter.FindCinemasListByMovieIdPresenter;
import com.bw.movie.presenter.FindMovieListByCinemaIdPresenter;
import com.bw.movie.presenter.ReviewPresenter;
import com.bw.movie.view.detailsactvity.CheckInActivity;
import com.bw.movie.view.fragment.fcdetail.CinemaDetailsPlFragment;
import com.bw.movie.view.fragment.fcdetail.CinemaDetailsXqFragment;
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
    private int cid;
    private String name;
    private String movieTypes;
    private String director;
    private String duration;
    private String placeOrigin;
    private String address;
    private String imageUrl;
    private String names;
    private TextView xq,pl;
    int id;


    private ReviewAdapter reviewAdapter;
    private ImageView activityReviewPopwindowDown;
    private RecyclerView activityreviewpopwindowrecy;
    private ReviewPresenter reviewPresenter;
    private ViewPager vp;
    private List<Fragment> fragments;
    private View xqV, plV;
    private String addre;
    private String cinameName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cinema_detail);
        ButterKnife.bind(this);
        initView();

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

                findMovieScheduleListPresenter.requestNet(cinemaId, cinemaDetailListData.getId());
            }
        });

        //接口回调传值跳转选座页
        ticketDetailsAdapter.setOnImageClickLisener(new TicketDetailsAdapter.OnImageClickLisener() {
            @Override
            public void onImageClick(String ScreeningHall, String BeginTime, String EndTime, double Price, int id) {
                Intent intent = new Intent(CinemaDetailActivity.this, CheckInActivity.class);
                intent.putExtra("name", name);
                intent.putExtra("address", address);
                intent.putExtra("names", names);
                intent.putExtra("ScreeningHall", ScreeningHall);
                intent.putExtra("BeginTime", BeginTime);
                intent.putExtra("EndTime", EndTime);
                intent.putExtra("Price", Price);
                intent.putExtra("cinameName", cinameName);
                intent.putExtra("addre", addre);
                if (id == 0) {
                    intent.putExtra("Id", cid);
                } else {
                    intent.putExtra("Id", id);
                }
                startActivity(intent);
                overridePendingTransition(R.anim.ac_in, R.anim.ac_out);
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
        cinemaId = intent.getExtras().getInt("CcinemaID");
        Log.v("cinemaId", "cinemaId" + cinemaId);
        String imageUrl = intent.getStringExtra("CimageUrl");
        addre = intent.getStringExtra("Caddress");
        cinameName = intent.getStringExtra("CcinameName");
        cid = intent.getExtras().getInt("Cid");
        icon.setImageURI(Uri.parse(imageUrl));
        addtess.setText(addre);
        title.setText(cinameName);
        ticketDetailsAdapter = new TicketDetailsAdapter(this);
        rec.setAdapter(ticketDetailsAdapter);
        rec.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    @OnClick({R.id.cinema_detail_image_back, R.id.cinema_detail_image_icon})
    void on(View view) {
        switch (view.getId()) {
            case R.id.cinema_detail_image_back:
                finish();
                overridePendingTransition(R.anim.ac_in, R.anim.ac_out);
                break;
            case R.id.cinema_detail_image_icon:
                View rootview4 = LayoutInflater.from(CinemaDetailActivity.this).inflate(R.layout.fragment_cinema_pop_, null);
                pop = rootview4;
                final PopupWindow popupWindow4 = new PopupWindow(rootview4);

                //设置充满父窗体
                popupWindow4.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
                popupWindow4.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
//                popupWindow4.setAnimationStyle(R.style.StyleNetChangedDialog_Animation);
                //设置布局
                activityReviewPopwindowDown = pop.findViewById(R.id.fragment_review_popwindow_down);
                vp = pop.findViewById(R.id.fragment_details_vp);
                xqV = pop.findViewById(R.id.fragment_details_v);
                plV = pop.findViewById(R.id.fragment_details_vv);
                pl = pop.findViewById(R.id.fragment_details_pinglun);
                xq = pop.findViewById(R.id.fragment_details_xiangqing);
                popupWindow4.showAtLocation(rootview4, Gravity.BOTTOM, 0, 0);

                fragments = new ArrayList<>();
                fragments.add(new CinemaDetailsXqFragment());
                fragments.add(new CinemaDetailsPlFragment());

                xq.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        vp.setCurrentItem(0);
                        ChangeBackGround(0);

//                        EventBus.getDefault().postSticky(new EvBean(cinemaId));
//                        startActivity(new Intent(CinemaDetailActivity.this,CinemaDetailsXqFragment.class));
                    }
                });
                pl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        vp.setCurrentItem(1);
                        ChangeBackGround(1);
//                        EventBus.getDefault().postSticky(new EvBean(cinemaId));
//                        startActivity(new Intent(CinemaDetailActivity.this,CinemaDetailsPlFragment.class));
                    }
                });

                vp.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
                    @Override
                    public Fragment getItem(int i) {
                        return fragments.get(i);
                    }

                    @Override
                    public int getCount() {
                        return fragments.size();
                    }
                });
                vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int i, float v, int i1) {

                    }

                    @Override
                    public void onPageSelected(int i) {
                        ChangeBackGround(i);
                    }

                    @Override
                    public void onPageScrollStateChanged(int i) {

                    }
                });

                //设置关闭popupWindow的点击事件
                activityReviewPopwindowDown.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow4.dismiss();
                    }
                });
                break;
            default:
                break;
        }
    }
    private View pop;
    @Override
    public <T extends View> T findViewById(int id) {
        if (id == R.id.fragment_details_vp && pop !=null){
            return pop.findViewById(id);
        }
        return super.findViewById(id);
    }

    private void ChangeBackGround(int index) {
        //
        xqV.setVisibility(index == 0 ? View.VISIBLE : View.GONE);
        //
        plV.setVisibility(index == 1 ? View.VISIBLE : View.GONE);
    }

    class find implements DataCall<Result<List<CinemaDetailListData>>> {
        @Override
        public void success(Result<List<CinemaDetailListData>> data) {

            if (data.getStatus().equals("0000")) {
                Log.v("影院详情", data.getResult().size() + "");
                resuleeee = data.getResult();
                int id = resuleeee.get(0).getId();

                findMovieScheduleListPresenter.requestNet(cinemaId, id);
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

            if (data.getStatus().equals("0000")) {
                if (data.getResult().size() == 0) {
                    Toast.makeText(CinemaDetailActivity.this, "此影片暂时无排期", Toast.LENGTH_SHORT).show();
                    ticketDetailsAdapter.clearList();
                    ticketDetailsAdapter.notifyDataSetChanged();
                }else{
                    ticketDetailsAdapter.clearList();
                    ticketDetailsAdapter.addAll(data.getResult());
                    ticketDetailsAdapter.notifyDataSetChanged();
                }

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
