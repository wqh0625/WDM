package demo.com.wdmoviedemo.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.facebook.drawee.view.SimpleDraweeView;

import demo.com.wdmoviedemo.bean.Result;
import demo.com.wdmoviedemo.bean.SearchData;
import demo.com.wdmoviedemo.core.exception.ApiException;
import demo.com.wdmoviedemo.core.interfase.DataCall;
import demo.com.wdmoviedemo.presenter.SearchPresenter;

public class Film_Details_Activity extends AppCompatActivity implements View.OnClickListener {

    private ImageView imageDetails;
    private TextView txtDetails;
    private RelativeLayout top;
    private TextView detailsTxtName;
    private SimpleDraweeView detailsSdvImage;
    private TextView detailsTxtDetails;
    private TextView detailsTxtPrediction;
    private TextView detailsTxtStills;
    private TextView detailsTxtReview;
    private ImageView detailsImageBack;
    private TextView detailsTxtBuy;
    private int position;
    private SearchPresenter searchPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film__details);
        initView();
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

    private void initView() {
        Intent intent = getIntent();
        position = intent.getExtras().getInt("position");
        imageDetails = (ImageView) findViewById(R.id.image_details);
        txtDetails = (TextView) findViewById(R.id.txt_details);
        top = (RelativeLayout) findViewById(R.id.top);
        detailsTxtName = (TextView) findViewById(R.id.details_txt_name);
        detailsSdvImage = (SimpleDraweeView) findViewById(R.id.details_sdv_image);
        detailsTxtDetails = (TextView) findViewById(R.id.details_txt_details);
        detailsTxtPrediction = (TextView) findViewById(R.id.details_txt_prediction);
        detailsTxtStills = (TextView) findViewById(R.id.details_txt_stills);
        detailsTxtReview = (TextView) findViewById(R.id.details_txt_review);
        detailsImageBack = (ImageView) findViewById(R.id.details_image_back);
        detailsTxtBuy = (TextView) findViewById(R.id.details_txt_buy);
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
            case R.id.details_txt_details:

                break;
            case R.id.details_txt_prediction:

                break;
            case R.id.details_txt_stills:

                break;
            case R.id.details_txt_review:

                break;
            case R.id.details_image_back:

                finish();
                break;
            case R.id.details_txt_buy:
                break;

        }
    }
}
