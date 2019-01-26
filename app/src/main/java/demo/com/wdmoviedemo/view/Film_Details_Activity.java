package demo.com.wdmoviedemo.view;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import demo.com.wdmoviedemo.bean.FilmDetailsData;
import demo.com.wdmoviedemo.bean.Result;
import demo.com.wdmoviedemo.bean.ReviewData;
import demo.com.wdmoviedemo.bean.SearchData;
import demo.com.wdmoviedemo.bean.ShortFilmListBean;
import demo.com.wdmoviedemo.core.adapter.ActornameAdapter;
import demo.com.wdmoviedemo.core.adapter.PredictionAdapter;
import demo.com.wdmoviedemo.core.adapter.ReviewAdapter;
import demo.com.wdmoviedemo.core.adapter.StillsAdapter;
import demo.com.wdmoviedemo.core.base.BaseActivity;
import demo.com.wdmoviedemo.core.exception.ApiException;
import demo.com.wdmoviedemo.core.interfase.DataCall;
import demo.com.wdmoviedemo.presenter.ConcernPresenter;
import demo.com.wdmoviedemo.presenter.FilmDetailsPresenter;
import demo.com.wdmoviedemo.presenter.ReviewPresenter;
import demo.com.wdmoviedemo.presenter.SearchPresenter;
import demo.com.wdmoviedemo.view.detailsactvity.ListofCinemaActivity;

public class Film_Details_Activity extends BaseActivity implements View.OnClickListener {

    private ImageView imageDetails;
    private TextView txtDetails;
    private RelativeLayout top;
    private TextView detailsTxtName;
    private SimpleDraweeView detailsSdvImage;
    private Button detailsTxtDetails;
    private Button detailsTxtPrediction;
    private Button detailsTxtStills;
    private Button detailsTxtReview;
    private ImageView detailsImageBack;
    private Button detailsTxtBuy;
    private int position;
    private SearchPresenter searchPresenter;
    private SimpleDraweeView activityDetailsSimpledraweeview;
    private TextView activityDetailsRole;
    private TextView activityDetailsType;
    private TextView activityDetailsTime;
    private TextView activityDetailsDctorname;
    private TextView activityDetailsJianjie;
    private RecyclerView activityDetailsRecyclerview;
    private TextView activityDetailsLocation;
    private TextView popwindowDetailsDirect;
    private ImageView activityDetailsBack;
    private FilmDetailsPresenter filmDetailsPresenter;
    private int userId;
    private String sessionId;
    private ImageView activityPredictionPopwindowDown;
    private RecyclerView activityPredictionPopwindowRecyclerview;
    private ActornameAdapter actornameAdapter;
    private PopupWindow popupWindow;
    private PredictionAdapter predictionAdapter;
    private ImageView actvityImageDetails;
    private ImageView activityStillsPopwindowDown;
    private RecyclerView activityStillsPopwindowRecy;
    private ImageView activityReviewPopwindowDown;
    private RecyclerView activityreviewpopwindowrecy;
    private StillsAdapter stillsAdapter;
    private ReviewAdapter reviewAdapter;
    private ReviewPresenter reviewPresenter;
    private String name;
    private String movieTypes;
    private String director;
    private String duration;
    private String placeOrigin;
    private String summary;
    private String imageUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film__details);
        userId = userInfoBean.getUserId();
        sessionId = userInfoBean.getSessionId();
        init();
        initData();
    }

    private void initData() {
        searchPresenter = new SearchPresenter(new SearchCall());
        searchPresenter.requestNet(position);
    }

    class SearchCall implements DataCall<Result<FilmDetailsData>>{



        @Override
        public void success(Result<FilmDetailsData> data) {
            if (data.getStatus().equals("0000")){
                name = data.getResult().getName();
                imageUrl = data.getResult().getImageUrl();
                detailsTxtName.setText(name);
                detailsSdvImage.setImageURI(imageUrl);
                int followMovie = data.getResult().getFollowMovie();
                if (followMovie ==2){
                    actvityImageDetails.setBackgroundResource(R.drawable.com_icon_collection_default);
                }else {
                    actvityImageDetails.setBackgroundResource(R.drawable.icon_collection_selected);
                }
            }
        }

        @Override
        public void fail(ApiException a) {
            Toast.makeText(Film_Details_Activity.this, "失败", Toast.LENGTH_SHORT).show();
        }
    }

    private void init() {
        Intent intent = getIntent();
        position = intent.getExtras().getInt("position");
        imageDetails = (ImageView) findViewById(R.id.image_details);
        txtDetails = (TextView) findViewById(R.id.txt_details);
        actvityImageDetails = (ImageView) findViewById(R.id.actvity_image_details);
        top = (RelativeLayout) findViewById(R.id.top);
        detailsTxtName = (TextView) findViewById(R.id.details_txt_name);
        detailsSdvImage = (SimpleDraweeView) findViewById(R.id.details_sdv_image);
        detailsTxtDetails = (Button) findViewById(R.id.details_btn_details);
        detailsTxtPrediction = (Button) findViewById(R.id.details_btn_prediction);
        detailsTxtStills = (Button) findViewById(R.id.details_btn_stills);
        detailsTxtReview = (Button) findViewById(R.id.details_btn_review);
        detailsImageBack = (ImageView) findViewById(R.id.details_image_back);
        detailsTxtBuy = (Button) findViewById(R.id.details_btn_buy);
        detailsTxtDetails.setOnClickListener(this);
        detailsTxtPrediction.setOnClickListener(this);
        detailsTxtStills.setOnClickListener(this);
        detailsTxtReview.setOnClickListener(this);
        detailsImageBack.setOnClickListener(this);
        detailsTxtBuy.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.actvity_image_details:
                //点击关注
                if (userId==0 || sessionId==null || sessionId==""){

                    Intent intent = new Intent(this,LoginActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.ac_in, R.anim.ac_out);
                }else {
                    ConcernPresenter  concernPresenter = new ConcernPresenter(new ConcernCall());
                    concernPresenter.requestNet(position);
                }

                break;
            case R.id.details_btn_details:
                //详情
                View rootview = LayoutInflater.from(Film_Details_Activity.this).inflate(R.layout.activity_film__details, null);
                View view1 = LayoutInflater.from(Film_Details_Activity.this).inflate(R.layout.activity_details_popwindow, null);
                popupWindow = new PopupWindow(view1);
                //设置充满父窗体
                popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
                popupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
                popupWindow.setAnimationStyle(R.style.StyleNetChangedDialog_Animation);
                //设置布局
                popupWindow.setContentView(view1);
                popupWindow.showAtLocation(rootview, Gravity.BOTTOM, 0, 0);

                //获取PopupWindow获取控件
                activityDetailsSimpledraweeview = view.findViewById(R.id.activity_details_simpledraweeview);
                activityDetailsRole = view1.findViewById(R.id.activity_details_role);
                activityDetailsType = view1.findViewById(R.id.activity_details_type);
                activityDetailsTime = view1.findViewById(R.id.activity_details_time);
                activityDetailsDctorname = view1.findViewById(R.id.activity_details_actorname);
                activityDetailsJianjie = view1.findViewById(R.id.activity_details_jianjie);
                activityDetailsRecyclerview = view1.findViewById(R.id.activity_details_recyclerview);
                activityDetailsLocation = view1.findViewById(R.id.activity_details_location);
                popwindowDetailsDirect = view1.findViewById(R.id.popwindow_details_direct);
                activityDetailsBack = view1.findViewById(R.id.activity_details_back);
                //popupWindow设置与父窗体替补显示

                activityDetailsRecyclerview.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

                filmDetailsPresenter = new FilmDetailsPresenter(new FilmDetailsCall());

                filmDetailsPresenter.requestNet(position);


                //设置关闭popupWindow的点击事件
                activityDetailsBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });

                break;
            case R.id.details_btn_prediction:
                //预告片
                View rootview2 = LayoutInflater.from(Film_Details_Activity.this).inflate(R.layout.activity_film__details, null);
                View view2 = LayoutInflater.from(Film_Details_Activity.this).inflate(R.layout.activity_prediction_popwindow, null, false);
                final PopupWindow popupWindow2 = new PopupWindow(view2);
                //设置充满父窗体
                popupWindow2.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
                popupWindow2.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
                popupWindow2.setAnimationStyle(R.style.StyleNetChangedDialog_Animation);
                //设置布局
                popupWindow2.setContentView(view2);
                activityPredictionPopwindowDown = view2.findViewById(R.id.activity_prediction_popwindow_down);
                activityPredictionPopwindowRecyclerview = view2.findViewById(R.id.activity_prediction_popwindow_recyclerview);
                popupWindow2.showAtLocation(rootview2, Gravity.BOTTOM, 0, 0);

                predictionAdapter = new PredictionAdapter(this);

                activityPredictionPopwindowRecyclerview.setAdapter(predictionAdapter);

                activityPredictionPopwindowRecyclerview.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

                filmDetailsPresenter = new FilmDetailsPresenter(new FilmDetailsCall());


                filmDetailsPresenter.requestNet(position);

                //设置关闭popupWindow的点击事件
                activityPredictionPopwindowDown.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow2.dismiss();
                    }
                });
                break;
            case R.id.details_btn_stills:
                //剧照
                View rootview3 = LayoutInflater.from(Film_Details_Activity.this).inflate(R.layout.activity_film__details, null);
                View view3 = LayoutInflater.from(Film_Details_Activity.this).inflate(R.layout.activity_stills_popwindow, null, false);
                final PopupWindow popupWindow3 = new PopupWindow(view3);
                //设置充满父窗体
                popupWindow3.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
                popupWindow3.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
                popupWindow3.setAnimationStyle(R.style.StyleNetChangedDialog_Animation);
                //设置布局
                popupWindow3.setContentView(view3);

                activityStillsPopwindowDown = view3.findViewById(R.id.activity_stills_popwindow_down);
                activityStillsPopwindowRecy = view3.findViewById(R.id.activity_stills_popwindow_recy);
                popupWindow3.showAtLocation(rootview3, Gravity.BOTTOM, 0, 0);


                stillsAdapter = new StillsAdapter(this);
                activityStillsPopwindowRecy.setAdapter(stillsAdapter);
                activityStillsPopwindowRecy.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));


                //设置关闭popupWindow的点击事件
                activityStillsPopwindowDown.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow3.dismiss();
                    }
                });

                break;
            case R.id.details_btn_review:
                //影评
                View rootview4 = LayoutInflater.from(Film_Details_Activity.this).inflate(R.layout.activity_film__details, null);
                View view4 = LayoutInflater.from(Film_Details_Activity.this).inflate(R.layout.activity_review_popwindow, null, false);
                final PopupWindow popupWindow4 = new PopupWindow(view4);
                //设置充满父窗体
                popupWindow4.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
                popupWindow4.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
                popupWindow4.setAnimationStyle(R.style.StyleNetChangedDialog_Animation);
                //设置布局
                popupWindow4.setContentView(view4);
                activityReviewPopwindowDown = view4.findViewById(R.id.activity_review_popwindow_down);
                activityreviewpopwindowrecy = view4.findViewById(R.id.activity_review_popwindow_recy);

                popupWindow4.showAtLocation(rootview4, Gravity.BOTTOM, 0, 0);

                reviewAdapter = new ReviewAdapter(this);
                activityreviewpopwindowrecy.setAdapter(reviewAdapter);
                activityreviewpopwindowrecy.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
                reviewPresenter = new ReviewPresenter(new ReviewCall());
                reviewPresenter.requestNet(position,1,20);

                //设置关闭popupWindow的点击事件
                activityReviewPopwindowDown.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow4.dismiss();
                    }
                });

                break;
            case R.id.details_image_back:
                //返回
                finish();
                overridePendingTransition(R.anim.ac_in, R.anim.ac_out);
                break;
            case R.id.details_btn_buy:
                //购票
                Intent intent = new Intent(this,ListofCinemaActivity.class);
                intent.putExtra("position",position);
                intent.putExtra("name",name);
                intent.putExtra("movieTypes",movieTypes);
                intent.putExtra("director",director);
                intent.putExtra("duration",duration);
                intent.putExtra("imageUrl",imageUrl);
                intent.putExtra("placeOrigin",placeOrigin);
                startActivity(intent);
                break;
            default:
                break;

        }

    }
    //详情
    class FilmDetailsCall implements DataCall<Result<FilmDetailsData>>{



        @Override
        public void success(Result<FilmDetailsData> data) {
            if (data.getStatus().equals("0000")){
                //设置popupWindow内部的数据
                Toast.makeText(Film_Details_Activity.this, ""+data.getMessage(), Toast.LENGTH_SHORT).show();
                FilmDetailsData result = data.getResult();
                //activityDetailsSimpledraweeview.setImageURI(result.getImageUrl());
                movieTypes = result.getMovieTypes();
                director = result.getDirector();
                duration = result.getDuration();
                placeOrigin = result.getPlaceOrigin();
                summary = result.getSummary();
                activityDetailsType.setText("类型:"+movieTypes );
                popwindowDetailsDirect.setText("导演:"+ director);
                activityDetailsTime.setText("时长:"+duration );
                activityDetailsLocation.setText("产地:"+ placeOrigin);
                activityDetailsJianjie.setText("简介:"+ summary);

                //预告
                List<ShortFilmListBean> shortFilmList = result.getShortFilmList();
                predictionAdapter.addAll(shortFilmList);
                predictionAdapter.notifyDataSetChanged();

                //剧照
                List<String> posterList = result.getPosterList();
                stillsAdapter.addAll(posterList);
                stillsAdapter.notifyDataSetChanged();

            }
        }

        @Override
        public void fail(ApiException a) {
            Toast.makeText(Film_Details_Activity.this, "失败", Toast.LENGTH_SHORT).show();
        }
    }
    //关注
    class ConcernCall implements DataCall<Result>{

        @Override
        public void success(Result data) {
            if (data.getStatus().equals("0000")){
                Toast.makeText(Film_Details_Activity.this, "关注成功", Toast.LENGTH_SHORT).show();
                actvityImageDetails.setBackgroundResource(R.drawable.icon_collection_selected);
            }
        }

        @Override
        public void fail(ApiException a) {
            Toast.makeText(Film_Details_Activity.this, "关注失败", Toast.LENGTH_SHORT).show();
            actvityImageDetails.setBackgroundResource(R.drawable.com_icon_collection_default);
        }
    }

    class ReviewCall implements DataCall<Result<List<ReviewData>>>{

        @Override
        public void success(Result<List<ReviewData>> data) {
            if (data.getStatus().equals("0000")){
                reviewAdapter.addAll(data.getResult());
                reviewAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void fail(ApiException a) {
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        searchPresenter.unBind();
    }
}
