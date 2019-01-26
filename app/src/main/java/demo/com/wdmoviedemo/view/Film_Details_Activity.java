package demo.com.wdmoviedemo.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.facebook.drawee.view.SimpleDraweeView;

import demo.com.wdmoviedemo.bean.FilmDetailsData;
import demo.com.wdmoviedemo.bean.Result;
import demo.com.wdmoviedemo.bean.SearchData;
import demo.com.wdmoviedemo.core.base.BaseActivity;
import demo.com.wdmoviedemo.core.exception.ApiException;
import demo.com.wdmoviedemo.core.interfase.DataCall;
import demo.com.wdmoviedemo.presenter.FilmDetailsPresenter;
import demo.com.wdmoviedemo.presenter.SearchPresenter;

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



    class SearchCall implements DataCall<Result<SearchData>>{

        @Override
        public void success(Result<SearchData> data) {
            if (data.getStatus().equals("0000")){
                detailsTxtName.setText(data.getResult().getName());
                detailsSdvImage.setImageURI(data.getResult().getImageUrl());
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
            case R.id.details_btn_details:
                View rootview = LayoutInflater.from(Film_Details_Activity.this).inflate(R.layout.activity_film__details, null);
                View view1 = LayoutInflater.from(Film_Details_Activity.this).inflate(R.layout.activity_details_popwindow, null, false);
                final PopupWindow popupWindow = new PopupWindow(view);
                //设置充满父窗体
                popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
                popupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
                popupWindow.setAnimationStyle(R.style.StyleNetChangedDialog_Animation);
                //设置布局
                popupWindow.setContentView(view1);
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

                popupWindow.showAtLocation(rootview, Gravity.BOTTOM, 0, 0);

                filmDetailsPresenter = new FilmDetailsPresenter(new FilmDetailsCall());
                filmDetailsPresenter.requestNet(userId,sessionId,position);

                //设置关闭popupWindow的点击事件
                activityDetailsBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });

                break;
            case R.id.details_btn_prediction:
                View rootview2 = LayoutInflater.from(Film_Details_Activity.this).inflate(R.layout.activity_film__details, null);
                View view2 = LayoutInflater.from(Film_Details_Activity.this).inflate(R.layout.activity_prediction_popwindow, null, false);
                final PopupWindow popupWindow2 = new PopupWindow(view);
                //设置充满父窗体
                popupWindow2.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
                popupWindow2.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
                popupWindow2.setAnimationStyle(R.style.StyleNetChangedDialog_Animation);
                //设置布局
                popupWindow2.setContentView(view2);
                activityPredictionPopwindowDown = view2.findViewById(R.id.activity_prediction_popwindow_down);
                activityPredictionPopwindowRecyclerview = view2.findViewById(R.id.activity_prediction_popwindow_recyclerview);
                popupWindow2.showAtLocation(rootview2, Gravity.BOTTOM, 0, 0);
                //设置关闭popupWindow的点击事件
                activityPredictionPopwindowDown.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow2.dismiss();
                    }
                });
                break;
            case R.id.details_btn_stills:
                View rootview3 = LayoutInflater.from(Film_Details_Activity.this).inflate(R.layout.activity_film__details, null);
                View view3 = LayoutInflater.from(Film_Details_Activity.this).inflate(R.layout.activity_stills_popwindow, null, false);
                final PopupWindow popupWindow3 = new PopupWindow(view);
                //设置充满父窗体
                popupWindow3.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
                popupWindow3.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
                popupWindow3.setAnimationStyle(R.style.StyleNetChangedDialog_Animation);
                //设置布局
                popupWindow3.setContentView(view3);
                popupWindow3.showAtLocation(rootview3, Gravity.BOTTOM, 0, 0);

                break;
            case R.id.details_btn_review:
                View rootview4 = LayoutInflater.from(Film_Details_Activity.this).inflate(R.layout.activity_film__details, null);
                View view4 = LayoutInflater.from(Film_Details_Activity.this).inflate(R.layout.activity_review_popwindow, null, false);
                final PopupWindow popupWindow4 = new PopupWindow(view);
                //设置充满父窗体
                popupWindow4.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
                popupWindow4.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
                popupWindow4.setAnimationStyle(R.style.StyleNetChangedDialog_Animation);
                //设置布局
                popupWindow4.setContentView(view4);
                popupWindow4.showAtLocation(rootview4, Gravity.BOTTOM, 0, 0);

                break;
            case R.id.details_image_back:
                finish();
                break;
            case R.id.details_btn_buy:
                break;

        }

    }
    class FilmDetailsCall implements DataCall<Result<FilmDetailsData>>{

        @Override
        public void success(Result<FilmDetailsData> data) {
            if (data.getStatus().equals("0000")){
                //设置popupWindow内部的数据
                Toast.makeText(Film_Details_Activity.this, ""+data.getMessage(), Toast.LENGTH_SHORT).show();
                FilmDetailsData result = data.getResult();
                activityDetailsSimpledraweeview.setImageURI(result.getImageUrl());
                popwindowDetailsDirect.setText("导演:"+result.getDirector());
                activityDetailsType.setText("类型:"+result.getMovieTypes());
                activityDetailsTime.setText("时长:"+result.getDuration());
                activityDetailsLocation.setText("产地:"+result.getPlaceOrigin());
                activityDetailsJianjie.setText("简介:"+result.getSummary());
            }
        }

        @Override
        public void fail(ApiException a) {
            Toast.makeText(Film_Details_Activity.this, "失败", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        searchPresenter.unBind();
    }
}
