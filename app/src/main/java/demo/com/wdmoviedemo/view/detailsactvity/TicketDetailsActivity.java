package demo.com.wdmoviedemo.view.detailsactvity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bw.movie.R;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
        ticketTxtMovieTypes.setText(movieTypes);
        ticketTxtDirector.setText(director);
        ticketTxtDuration.setText(duration);
        ticketTxtPlaceOrigin.setText(placeOrigin);
    }

    @OnClick({R.id.ticket_image, R.id.ticket_image_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ticket_image:
                break;
            case R.id.ticket_image_back:
                finish();
                break;
        }
    }
}
