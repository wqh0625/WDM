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

import java.util.List;

import demo.com.wdmoviedemo.bean.Result;
import demo.com.wdmoviedemo.bean.SearchData;
import demo.com.wdmoviedemo.core.exception.ApiException;
import demo.com.wdmoviedemo.core.interfase.DataCall;
import demo.com.wdmoviedemo.presenter.SearchPresenter;

public class Film_Details_Activity extends AppCompatActivity implements View.OnClickListener {

    private ImageView image_details;
    private TextView txt_details;
    private RelativeLayout top;
    private TextView details_txt_name;
    private SimpleDraweeView details_sdv_image;
    private TextView details_txt_details;
    private TextView details_txt_prediction;
    private TextView details_txt_stills;
    private TextView details_txt_review;
    private ImageView details_image_back;
    private TextView details_txt_buy;
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
                Toast.makeText(Film_Details_Activity.this, ""+data.getMessage(), Toast.LENGTH_SHORT).show();
                details_txt_name.setText(data.getResult().getName());
                details_sdv_image.setImageURI(data.getResult().getImageUrl());
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
        image_details = (ImageView) findViewById(R.id.image_details);
        txt_details = (TextView) findViewById(R.id.txt_details);
        top = (RelativeLayout) findViewById(R.id.top);
        details_txt_name = (TextView) findViewById(R.id.details_txt_name);
        details_sdv_image = (SimpleDraweeView) findViewById(R.id.details_sdv_image);
        details_txt_details = (TextView) findViewById(R.id.details_txt_details);
        details_txt_prediction = (TextView) findViewById(R.id.details_txt_prediction);
        details_txt_stills = (TextView) findViewById(R.id.details_txt_stills);
        details_txt_review = (TextView) findViewById(R.id.details_txt_review);
        details_image_back = (ImageView) findViewById(R.id.details_image_back);
        details_txt_buy = (TextView) findViewById(R.id.details_txt_buy);
        details_txt_details.setOnClickListener(this);
        details_txt_prediction.setOnClickListener(this);
        details_txt_stills.setOnClickListener(this);
        details_txt_review.setOnClickListener(this);
        details_image_back.setOnClickListener(this);
        details_txt_buy.setOnClickListener(this);
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
