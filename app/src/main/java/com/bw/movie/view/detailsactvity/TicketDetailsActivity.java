package com.bw.movie.view.cinemaactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.view.detailsactvity.CheckInActivity;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.bw.movie.bean.Result;
import com.bw.movie.bean.TicketDetailsData;
import com.bw.movie.core.adapter.TicketDetailsAdapter;
import com.bw.movie.core.exception.ApiException;
import com.bw.movie.core.interfase.DataCall;
import com.bw.movie.presenter.TicketPresenter;

public class TicketDetailsActivity extends AppCompatActivity {

    @BindView(R.id.ticket_image)
    ImageView ticketImage;
    @BindView(R.id.ticket_name)
    TextView ticketName;
    @BindView(R.id.ticket_address)
    TextView ticketAddress;
    @BindView(R.id.ticket_sdv_image)
    SimpleDraweeView ticketSdvImage;
    @BindView(R.id.ticket_txt_name)
    TextView ticketTxtName;
    @BindView(R.id.ticket_txt_movieTypes)
    TextView ticketTxtMovieTypes;
    @BindView(R.id.ticket_txt_director)
    TextView ticketTxtDirector;
    @BindView(R.id.ticket_txt_duration)
    TextView ticketTxtDuration;
    @BindView(R.id.ticket_txt_placeOrigin)
    TextView ticketTxtPlaceOrigin;
    @BindView(R.id.ticket_recy)
    RecyclerView ticketRecy;
    @BindView(R.id.ticket_image_back)
    ImageView ticketImageBack;
    private int position;
    private int id;
    private String name;
    private String movieTypes;
    private String director;
    private String duration;
    private String placeOrigin;
    private String address;
    private String imageUrl;
    private TicketDetailsAdapter ticketDetailsAdapter;
    private TicketPresenter ticketPresenter;
    private String names;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_details);
        ButterKnife.bind(this);
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
        initData();
    }

    private void initData() {
        //赋值
        ticketName.setText(name);
        ticketAddress.setText(address);
        ticketSdvImage.setImageURI(imageUrl);
        ticketTxtName.setText(names);
        ticketTxtMovieTypes.setText("类型：" + movieTypes);
        ticketTxtDirector.setText("导演：" + director);
        ticketTxtDuration.setText("时长：" + duration);
        ticketTxtPlaceOrigin.setText("产地：" + placeOrigin);
        ticketDetailsAdapter = new TicketDetailsAdapter(this);
        ticketRecy.setAdapter(ticketDetailsAdapter);
        ticketRecy.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        ticketPresenter = new TicketPresenter(new TicketCall());
        ticketPresenter.requestNet(position, id);

        //接口回调传值跳转选座页
        ticketDetailsAdapter.setOnImageClickLisener(new TicketDetailsAdapter.OnImageClickLisener() {
            @Override
            public void onImageClick(String ScreeningHall, String BeginTime, String EndTime, double Price,int idddd) {
                Intent intent = new Intent(TicketDetailsActivity.this, CheckInActivity.class);
                intent.putExtra("name", name);
                intent.putExtra("address", address);
                intent.putExtra("names", names);
                intent.putExtra("ScreeningHall", ScreeningHall);
                intent.putExtra("BeginTime", BeginTime);
                intent.putExtra("EndTime", EndTime);
                intent.putExtra("Price", Price);
                intent.putExtra("Id",idddd);
                startActivity(intent);
                overridePendingTransition(R.anim.ac_in, R.anim.ac_out);
            }
        });
    }

    class TicketCall implements DataCall<Result<List<TicketDetailsData>>> {

        @Override
        public void success(Result<List<TicketDetailsData>> data) {
            if (data.getStatus().equals("0000")) {
                ticketDetailsAdapter.addAll(data.getResult());
                ticketDetailsAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void fail(ApiException a) {
        }
    }

    @OnClick({R.id.ticket_image, R.id.ticket_image_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ticket_image:

                break;
            case R.id.ticket_image_back:
                finish();
                overridePendingTransition(R.anim.ac_in, R.anim.ac_out);
                break;
            default:
                break;
        }
    }
}
