package com.bw.movie.view;

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
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.core.dao.DbManager;
import com.bw.movie.presenter.CancelConcernPresenter;
import com.facebook.drawee.view.SimpleDraweeView;
import com.j256.ormlite.dao.Dao;
import com.umeng.analytics.MobclickAgent;

import org.w3c.dom.Text;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bw.movie.bean.FilmDetailsData;
import com.bw.movie.bean.Result;
import com.bw.movie.bean.ReviewData;
import com.bw.movie.bean.SearchData;
import com.bw.movie.bean.ShortFilmListBean;
import com.bw.movie.bean.UserInfoBean;
import com.bw.movie.core.adapter.ActornameAdapter;
import com.bw.movie.core.adapter.PredictionAdapter;
import com.bw.movie.core.adapter.ReviewAdapter;
import com.bw.movie.core.adapter.StillsAdapter;
import com.bw.movie.core.base.BaseActivity;
import com.bw.movie.core.exception.ApiException;
import com.bw.movie.core.interfase.DataCall;
import com.bw.movie.presenter.ConcernPresenter;
import com.bw.movie.presenter.FilmDetailsPresenter;
import com.bw.movie.presenter.ReviewPresenter;
import com.bw.movie.presenter.SearchPresenter;
import com.bw.movie.view.detailsactvity.ListofCinemaActivity;

import cn.jzvd.JZVideoPlayer;

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
    private ImageView listImageBack;
    private ConcernPresenter concernPresenter;
    private int followMovie;
    private CancelConcernPresenter cancelConcernPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film__details);
        init();
        initData();
    }

    private void initData() {
        searchPresenter = new SearchPresenter(new SearchCall());
        concernPresenter = new ConcernPresenter(new ConcernCall());
        filmDetailsPresenter = new FilmDetailsPresenter(new PredictionCall());
        cancelConcernPresenter = new CancelConcernPresenter(new canCenGz());
        reviewPresenter = new ReviewPresenter(new ReviewCall());

        if (student.size() == 0) {
            searchPresenter.requestNet(0, "", position);
            filmDetailsPresenter.requestNet(0, "", position);
        } else {
            UserInfoBean userInfoBean = student.get(0);
            searchPresenter.requestNet(userInfoBean.getUserId(), userInfoBean.getSessionId(), position);
            filmDetailsPresenter.requestNet(userInfoBean.getUserId(), userInfoBean.getSessionId(), position);
        }

    }

    // 取消关注
    class canCenGz implements DataCall<Result> {
        @Override
        public void success(Result data) {
            Toast.makeText(Film_Details_Activity.this, "" + data.getMessage(), Toast.LENGTH_SHORT).show();
            if ("0000".equals(data.getStatus())) {
                actvityImageDetails.setBackgroundResource(R.drawable.com_icon_collection_default);
                followMovie = 2;
            }
        }

        @Override
        public void fail(ApiException a) {

        }
    }

    class SearchCall implements DataCall<Result<FilmDetailsData>> {
        @Override
        public void success(Result<FilmDetailsData> data) {
            if ("0000".equals(data.getStatus())) {
                name = data.getResult().getName();
                imageUrl = data.getResult().getImageUrl();
                detailsTxtName.setText(name);
                detailsSdvImage.setImageURI(imageUrl);
                int followMovie = data.getResult().getFollowMovie();
                if (followMovie == 1) {
                    actvityImageDetails.setBackgroundResource(R.drawable.icon_collection_selected);
                } else {
                    actvityImageDetails.setBackgroundResource(R.drawable.com_icon_collection_default);
                }
            }
        }

        @Override
        public void fail(ApiException a) {
        }
    }

    private void init() {
        Intent intent = getIntent();
        position = intent.getExtras().getInt("position");
        imageDetails = findViewById(R.id.image_details);
        txtDetails = findViewById(R.id.txt_details);
        actvityImageDetails = findViewById(R.id.actvity_image_details);
        top = findViewById(R.id.top);
        detailsTxtName = findViewById(R.id.details_txt_name);
        detailsSdvImage = findViewById(R.id.details_sdv_image);
        detailsTxtDetails = findViewById(R.id.details_btn_details);
        detailsTxtPrediction = findViewById(R.id.details_btn_prediction);
        detailsTxtStills = findViewById(R.id.details_btn_stills);
        detailsTxtReview = findViewById(R.id.details_btn_review);
        detailsImageBack = findViewById(R.id.details_image_back);
        detailsTxtBuy = findViewById(R.id.details_btn_buy);
        detailsTxtDetails.setOnClickListener(this);
        detailsTxtPrediction.setOnClickListener(this);
        detailsTxtStills.setOnClickListener(this);
        detailsTxtReview.setOnClickListener(this);
        detailsImageBack.setOnClickListener(this);
        actvityImageDetails.setOnClickListener(this);
        detailsTxtBuy.setOnClickListener(this);
        reviewAdapter = new ReviewAdapter(this);
        predictionAdapter = new PredictionAdapter(this);
        stillsAdapter = new StillsAdapter(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.actvity_image_details:
                try {
                    Dao<UserInfoBean, String> userDao = new DbManager(getApplicationContext()).getUserDao();
                    List<UserInfoBean> userInfoBeans = userDao.queryForAll();
                    if (followMovie == 1) {
                        // 取消关注
                        if (userInfoBeans.size() == 0) {
                            Intent intent = new Intent(this, LoginActivity.class);
                            startActivity(intent);
                            overridePendingTransition(R.anim.ac_in, R.anim.ac_out);
                        } else {
                            UserInfoBean userInfoBean = userDao.queryForAll().get(0);
                            cancelConcernPresenter.requestNet(userInfoBean.getUserId(), userInfoBean.getSessionId(), position);
                        }
                    } else {
                        //点击关注
                        if (userInfoBeans.size() == 0) {
                            Intent intent = new Intent(this, LoginActivity.class);
                            startActivity(intent);
                            overridePendingTransition(R.anim.ac_in, R.anim.ac_out);
                        } else {
                            UserInfoBean userInfoBean = userInfoBeans.get(0);
                            concernPresenter.requestNet(userInfoBean.getUserId(), userInfoBean.getSessionId(), position);
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
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
                activityDetailsSimpledraweeview = view1.findViewById(R.id.activity_details_simpledraweeviewiiii);
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

                activityDetailsRecyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
                activityDetailsSimpledraweeview.setImageURI(imageUrl);
                activityDetailsType.setText("类型：" + movieTypes);
                popwindowDetailsDirect.setText("导演:" + director);
                activityDetailsTime.setText("时长:" + duration);
                activityDetailsLocation.setText("地区:" + placeOrigin);
                activityDetailsJianjie.setText(summary);

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


                activityPredictionPopwindowRecyclerview.setAdapter(predictionAdapter);

                activityPredictionPopwindowRecyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));


                //设置关闭popupWindow的点击事件
                activityPredictionPopwindowDown.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow2.dismiss();
                        JZVideoPlayer.releaseAllVideos();
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


                activityStillsPopwindowRecy.setAdapter(stillsAdapter);
                activityStillsPopwindowRecy.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));


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
                listImageBack = view4.findViewById(R.id.list_image_back);


                popupWindow4.showAtLocation(rootview4, Gravity.BOTTOM, 0, 0);

                activityreviewpopwindowrecy.setAdapter(reviewAdapter);
                activityreviewpopwindowrecy.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
                reviewPresenter.requestNet(position, 1, 20);

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
                Intent intent = new Intent(this, ListofCinemaActivity.class);
                intent.putExtra("position", position);
                intent.putExtra("name", name);
                intent.putExtra("movieTypes", movieTypes);
                intent.putExtra("director", director);
                intent.putExtra("duration", duration);
                intent.putExtra("imageUrl", imageUrl);
                intent.putExtra("placeOrigin", placeOrigin);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
    private int mFlag=0;
    private long mTime1,mTime2;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //返回按键监听
        if (keyCode == KeyEvent.KEYCODE_BACK && mFlag == 0) {
            mFlag = 1;
            //获取当前系统时间
            mTime1 = System.currentTimeMillis();
            Toast.makeText(this, "在按一次就退出", Toast.LENGTH_SHORT).show();

        } else if (keyCode == KeyEvent.KEYCODE_BACK && mFlag == 1) {
            mTime2 = System.currentTimeMillis();
            if (mTime2 - mTime1 < 2000) {
                finish();
            } else {
                Toast.makeText(this, "在按一次就退出22222", Toast.LENGTH_SHORT).show();
            }
            mFlag = 0;
            mTime1 = 0;
            mTime2 = 0;

        }

        return true;
    }
    //预告片
    class PredictionCall implements DataCall<Result<FilmDetailsData>> {

        @Override
        public void success(Result<FilmDetailsData> data) {
            if (data.getStatus().equals("0000")) {
                //设置popupWindow内部的数据
                FilmDetailsData result = data.getResult();
                movieTypes = result.getMovieTypes();
                director = result.getDirector();
                duration = result.getDuration();
                placeOrigin = result.getPlaceOrigin();
                summary = result.getSummary();
                followMovie = result.getFollowMovie();

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
        }
    }



    //关注
    class ConcernCall implements DataCall<Result> {

        @Override
        public void success(Result data) {
            if ("0000".equals(data.getStatus())) {
                Toast.makeText(Film_Details_Activity.this, "" + data.getMessage(), Toast.LENGTH_SHORT).show();
                actvityImageDetails.setBackgroundResource(R.drawable.icon_collection_selected);
                followMovie = 1;
            }
        }

        @Override
        public void fail(ApiException a) {

        }
    }

    class ReviewCall implements DataCall<Result<List<ReviewData>>> {

        @Override
        public void success(Result<List<ReviewData>> data) {
            if ("0000".equals(data.getStatus())) {
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
        concernPresenter.unBind();
        filmDetailsPresenter.unBind();
        reviewPresenter.unBind();
        JZVideoPlayer.releaseAllVideos();
    }

    @Override
    public void onResume() {
        super.onResume();

        MobclickAgent.onPageStart("影片详情页面");
        MobclickAgent.onResume(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        JZVideoPlayer.releaseAllVideos();
        MobclickAgent.onPageEnd("影片详情页面");
        MobclickAgent.onPause(this);
    }
}
