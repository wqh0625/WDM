package demo.com.wdmoviedemo.view.detailsactvity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bw.movie.R;
import com.qfdqc.views.seattable.SeatTable;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CheckInActivity extends AppCompatActivity {

    @BindView(R.id.checkin_name)
    TextView checkinName;
    @BindView(R.id.checkin_address)
    TextView checkinAddress;
    @BindView(R.id.checkin_names)
    TextView checkinNames;
    @BindView(R.id.checkin_begintime)
    TextView checkinBegintime;
    @BindView(R.id.checkin_endtime)
    TextView checkinEndtime;
    @BindView(R.id.checkin_screeningHall)
    TextView checkinScreeningHall;
    @BindView(R.id.seat_view)
    SeatTable seatView;
    @BindView(R.id.checkin_prices)
    TextView checkinPrices;
    @BindView(R.id.img_confirm)
    ImageView imgConfirm;
    @BindView(R.id.img_abandon)
    ImageView imgAbandon;
    private String name;
    private String address;
    private String names;
    private String screeningHall;
    private String beginTime;
    private String endTime;
    private double price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_in);
        ButterKnife.bind(this);
        initData();
        initSeatTable();
    }

    private void initSeatTable() {
        
    }

    private void initData() {
        Intent intent = getIntent();
        name = intent.getExtras().getString("name");
        address = intent.getExtras().getString("address");
        names = intent.getExtras().getString("names");
        screeningHall = intent.getExtras().getString("ScreeningHall");
        beginTime = intent.getExtras().getString("BeginTime");
        endTime = intent.getExtras().getString("EndTime");
        price = intent.getExtras().getDouble("Price");
        checkinName.setText(name);
        checkinAddress.setText(address);
        checkinNames.setText(names);
        checkinScreeningHall.setText(screeningHall);
        checkinBegintime.setText(beginTime);
        checkinEndtime.setText(endTime);
        checkinPrices.setText("" + price);
    }
}
