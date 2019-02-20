package com.bw.movie.view;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
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
import com.bw.movie.core.dao.DbManager;
import com.bw.movie.presenter.CancelConcernPresenter;
import com.bw.movie.presenter.LikePresenter;
import com.facebook.drawee.view.SimpleDraweeView;
import com.j256.ormlite.dao.Dao;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.umeng.analytics.MobclickAgent;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bw.movie.bean.FilmDetailsData;
import com.bw.movie.bean.Result;
import com.bw.movie.bean.ReviewData;
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
import jaydenxiao.com.expandabletextview.ExpandableTextView;
import me.jessyan.autosize.internal.CustomAdapt;

public class Film_Details_Activity extends BaseActivity implements View.OnClickListener {


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
    private ExpandableTextView activityDetailsJianjie;
    private RecyclerView activityDetailsRecyclerview;
    private TextView activityDetailsLocation;
    private TextView popwindowDetailsDirect;
    private ImageView activityDetailsBack;
    private FilmDetailsPresenter filmDetailsPresenter;

    private ImageView activityPredictionPopwindowDown;
    private RecyclerView activityPredictionPopwindowRecyclerview;
    private PredictionAdapter predictionAdapter;
    private ImageView actvityImageDetails;
    private ImageView activityStillsPopwindowDown;
    private XRecyclerView activityStillsPopwindowRecy;
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
    private ActornameAdapter actornameAdapter;
    private String starring;
    private List<String> list = new ArrayList<>();
    private LikePresenter likePresenter;
    private View view1;
    private View view2;
    private View view3;
    private View view4;
    private PopupWindow popupWindow;
    private PopupWindow popupWindow2;
    private PopupWindow popupWindow3;
    private PopupWindow popupWindow4;


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
        likePresenter = new LikePresenter(new LikeCall());

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
        actvityImageDetails = findViewById(R.id.actvity_image_details);
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
                //影片详情弹框
                view1 = View.inflate(Film_Details_Activity.this, R.layout.activity_details_popwindow, null);

                popupWindow = new PopupWindow(view1);
                //设置充满父窗体
                popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
                int height1 = getWindow().getWindowManager().getDefaultDisplay().getHeight();
                if (height1 == 1920) {
                    popupWindow.setHeight(1920 - 300);
                } else if (height1 == 1280) {
                    popupWindow.setHeight(1280 - 230);
                } else if (height1 == 2180) {
                    popupWindow.setHeight(height1 - 350);
                } else {
                    popupWindow.setHeight(height1);
                }

                popupWindow.setAnimationStyle(R.style.StyleNetChangedDialog_Animation);
                //点击外部关闭弹框
                popupWindow.setBackgroundDrawable(new ColorDrawable());
                popupWindow.setOutsideTouchable(true);
                popupWindow.setTouchable(true);
                popupWindow.showAtLocation(view1, Gravity.BOTTOM, 0, 0);
                popupWindow.showAsDropDown(detailsTxtDetails);
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

                String[] split = starring.split(",");
                for (int i = 0; i < split.length; i++) {
                    list.add(split[i]);
                }
                actornameAdapter = new ActornameAdapter(Film_Details_Activity.this, list);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                activityDetailsRecyclerview.setLayoutManager(linearLayoutManager);
                activityDetailsRecyclerview.setAdapter(actornameAdapter);

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
                view2 = View.inflate(Film_Details_Activity.this, R.layout.activity_prediction_popwindow, null);
                popupWindow2 = new PopupWindow(view2);
                //设置充满父窗体
                popupWindow2.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
                int height2 = getWindow().getWindowManager().getDefaultDisplay().getHeight();
                if (height2 == 1920) {
                    popupWindow2.setHeight(1920 - 300);
                } else if (height2 == 1280) {
                    popupWindow2.setHeight(1280 - 230);
                } else if (height2 == 2180) {
                    popupWindow2.setHeight(height2 - 350);
                } else {
                    popupWindow2.setHeight(height2);
                }

                popupWindow2.setAnimationStyle(R.style.StyleNetChangedDialog_Animation);
                //点击外部关闭弹框
                popupWindow2.setBackgroundDrawable(new ColorDrawable());
                popupWindow2.setOutsideTouchable(true);
                popupWindow2.setTouchable(true);
                popupWindow2.showAtLocation(view2, Gravity.BOTTOM, 0, 0);
                popupWindow2.showAsDropDown(detailsTxtDetails);

                activityPredictionPopwindowDown = view2.findViewById(R.id.activity_prediction_popwindow_down);
                activityPredictionPopwindowRecyclerview = view2.findViewById(R.id.activity_prediction_popwindow_recyclerview);


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
                view3 = View.inflate(Film_Details_Activity.this, R.layout.activity_stills_popwindow, null);
                popupWindow3 = new PopupWindow(view3);
                //设置充满父窗体
                popupWindow3.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
                int height3 = getWindow().getWindowManager().getDefaultDisplay().getHeight();
                if (height3 == 1920) {
                    popupWindow3.setHeight(1920 - 300);
                } else if (height3 == 1280) {
                    popupWindow3.setHeight(1280 - 230);
                } else if (height3 == 2180) {
                    popupWindow3.setHeight(height3 - 350);
                } else {
                    popupWindow3.setHeight(height3);
                }
                popupWindow3.setAnimationStyle(R.style.StyleNetChangedDialog_Animation);
                //点击外部关闭弹框
                popupWindow3.setBackgroundDrawable(new ColorDrawable());
                popupWindow3.setOutsideTouchable(true);
                popupWindow3.setTouchable(true);
                popupWindow3.showAtLocation(view3, Gravity.BOTTOM, 0, 0);
                popupWindow3.showAsDropDown(detailsTxtDetails);

                activityStillsPopwindowDown = view3.findViewById(R.id.activity_stills_popwindow_down);
                activityStillsPopwindowRecy = view3.findViewById(R.id.activity_stills_popwindow_recy);

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
                view4 = View.inflate(Film_Details_Activity.this, R.layout.activity_review_popwindow, null);
                popupWindow4 = new PopupWindow(view4);
                //设置充满父窗体
                popupWindow4.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
                int height4 = getWindow().getWindowManager().getDefaultDisplay().getHeight();
                if (height4 == 1920) {
                    popupWindow4.setHeight(1920 - 300);
                } else if (height4 == 1280) {
                    popupWindow4.setHeight(1280 - 230);
                } else if (height4 == 2180) {
                    popupWindow4.setHeight(height4 - 350);
                } else {
                    popupWindow4.setHeight(height4);
                }
                popupWindow4.setAnimationStyle(R.style.StyleNetChangedDialog_Animation);
                //点击外部关闭弹框
                popupWindow4.setBackgroundDrawable(new ColorDrawable());
                popupWindow4.setOutsideTouchable(true);
                popupWindow4.setTouchable(true);
                popupWindow4.showAtLocation(view4, Gravity.BOTTOM, 0, 0);
                popupWindow4.showAsDropDown(detailsTxtDetails);

                activityReviewPopwindowDown = view4.findViewById(R.id.activity_review_popwindow_down);
                activityreviewpopwindowrecy = view4.findViewById(R.id.activity_review_popwindow_recy);
                listImageBack = view4.findViewById(R.id.list_image_back);



                activityreviewpopwindowrecy.setAdapter(reviewAdapter);
                activityreviewpopwindowrecy.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
                reviewPresenter.requestNet(position, 1, 20);

                //评论点赞
                reviewAdapter.setImagePraiseOnclickListener(new ReviewAdapter.ImagePraiseOnclickListener() {
                    @Override
                    public void ImagePraiseOnclick(int i, int id, ReviewData reviewData) {
                        if (student.size() == 0) {
                            s();
                        } else {
                            if (reviewData.getIsGreat() == 1) {
                            } else {
                                likePresenter.requestNet(userInfoBean.getUserId(), userInfoBean.getSessionId(), id, i);
                            }

                        }
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

    private int mFlag = 0;
    private long mTime1, mTime2;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //返回按键监听
        if (keyCode == KeyEvent.KEYCODE_BACK && mFlag == 0) {
            mFlag = 1;
            //获取当前系统时间
            mTime1 = System.currentTimeMillis();
            if (popupWindow.isShowing() || popupWindow2.isShowing() || popupWindow3.isShowing() || popupWindow4.isShowing()) {
                popupWindow.dismiss();
                popupWindow2.dismiss();
                JZVideoPlayer.releaseAllVideos();
                Toast.makeText(this, "0.0.", Toast.LENGTH_SHORT).show();
                popupWindow3.dismiss();
                popupWindow4.dismiss();
            } else {
                finish();
                overridePendingTransition(R.anim.ac_in, R.anim.ac_out);
            }
        } else if (keyCode == KeyEvent.KEYCODE_BACK && mFlag == 1) {
            mTime2 = System.currentTimeMillis();
            if (mTime2 - mTime1 < 2500) {
                finish();
                overridePendingTransition(R.anim.ac_in, R.anim.ac_out);
            } else {
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
                starring = result.getStarring();

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

    //评论点赞
    class LikeCall implements DataCall<Result> {

        @Override
        public void success(Result data) {
            if (data.getStatus().equals("0000")) {
                int o = (int) data.getArgs()[3];
                ReviewData item = reviewAdapter.getItem(o);
                item.setIsGreat(1);
                item.setGreatNum(item.getGreatNum() + 1);
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
        likePresenter.unBind();
        JZVideoPlayer.releaseAllVideos();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (student.size() == 0) {
            searchPresenter.requestNet(0, "", position);
            filmDetailsPresenter.requestNet(0, "", position);
        } else {
            UserInfoBean userInfoBean = student.get(0);
            searchPresenter.requestNet(userInfoBean.getUserId(), userInfoBean.getSessionId(), position);
            filmDetailsPresenter.requestNet(userInfoBean.getUserId(), userInfoBean.getSessionId(), position);
        }
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


