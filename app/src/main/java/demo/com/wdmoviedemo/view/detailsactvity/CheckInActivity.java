package demo.com.wdmoviedemo.view.detailsactvity;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.RelativeSizeSpan;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.qfdqc.views.seattable.SeatTable;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.math.BigDecimal;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import demo.com.wdmoviedemo.bean.Result;
import demo.com.wdmoviedemo.core.base.BaseActivity;
import demo.com.wdmoviedemo.core.exception.ApiException;
import demo.com.wdmoviedemo.core.http.NetWorks;
import demo.com.wdmoviedemo.core.interfase.DataCall;
import demo.com.wdmoviedemo.core.interfase.IRequest;
import demo.com.wdmoviedemo.core.utils.EncryptUtil;
import demo.com.wdmoviedemo.presenter.BuyMovieTicketPresenter;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class CheckInActivity extends BaseActivity {
    private IWXAPI api;
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
    private int id;
    private BigDecimal mPriceWithCalculate;
    private int selectedTableCount = 0;
    private BuyMovieTicketPresenter buyMovieTicketPresenter;
    private int type = 0;
    private String cinameName;
    private String addre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_in);
        ButterKnife.bind(this);
        initData();
        initSeatTable();
        //初始化影院选座页面对应的影院以及影片信息
        initChooseMessage();
        //第二个参数为APPID
        api = WXAPIFactory.createWXAPI(this, "wxb3852e6a6b7d9516");
        api.registerApp("wxb3852e6a6b7d9516");

        buyMovieTicketPresenter = new BuyMovieTicketPresenter(new buy());
    }

    private void initSeatTable() {
        //设置屏幕名称
        seatView.setScreenName(screeningHall + "荧幕");
        //设置最多选中
        seatView.setMaxSelected(10);

        seatView.setSeatChecker(new SeatTable.SeatChecker() {

            @Override
            public boolean isValidSeat(int row, int column) {
                if (column == 2) {
                    return false;
                }
                return true;
            }

            @Override
            public boolean isSold(int row, int column) {
                if (row == 6 && column == 6) {
                    return true;
                }
                return false;
            }

            @Override
            public void checked(int row, int column) {
                changePriceWithSelected();
            }

            @Override
            public void unCheck(int row, int column) {
                changePriceWithUnSelected();
            }

            @Override
            public String[] checkedSeatTxt(int row, int column) {
                return null;
            }

        });
        seatView.setData(10, 15);

    }

    private void initChooseMessage() {
        mPriceWithCalculate = new BigDecimal(price);

    }

    //选中的座位计算价格
    private void changePriceWithSelected() {
        selectedTableCount++;
        String currentPrice = mPriceWithCalculate.multiply(new BigDecimal(String.valueOf(selectedTableCount))).toString();
        SpannableString spannableString = changTVsize(currentPrice);
        checkinPrices.setText(spannableString);
    }

    //取消选座时价格联动
    private void changePriceWithUnSelected() {
        selectedTableCount--;
        if (selectedTableCount == 0) {
            checkinPrices.setText("" + 0.00);
        } else {
            String currentPrice = mPriceWithCalculate.multiply(new BigDecimal(String.valueOf(selectedTableCount))).toString();
            SpannableString spannableString = changTVsize(currentPrice);
            checkinPrices.setText(spannableString);
        }

    }

    //小数点后面改变字体大小
    public static SpannableString changTVsize(String value) {
        SpannableString spannableString = new SpannableString(value);
        if (value.contains(".")) {
            spannableString.setSpan(new RelativeSizeSpan(0.6f), value.indexOf("."), value.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return spannableString;
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
        id = intent.getExtras().getInt("Id");
        cinameName = intent.getExtras().getString("cinameName");
        addre = intent.getExtras().getString("addre");
        checkinName.setText(name);
        checkinAddress.setText(address);
        checkinNames.setText(names);
        checkinScreeningHall.setText(screeningHall);
        checkinBegintime.setText(beginTime);
        checkinEndtime.setText(endTime);
        checkinPrices.setText("" + 0.00);
        checkinName.setText(cinameName);
        checkinAddress.setText(addre);
    }

    @OnClick({R.id.img_confirm, R.id.img_abandon})
    void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_confirm:
                final View popView = View.inflate(CheckInActivity.this, R.layout.activity_check_pop_pay, null);
                final PopupWindow popWindow = new PopupWindow(popView, ViewGroup.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT, true);
                popWindow.setTouchable(true);
                popWindow.setBackgroundDrawable(new BitmapDrawable());
                popWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);

                popWindow.showAtLocation(View.inflate(CheckInActivity.this, R.layout.activity_check_in, null), Gravity.BOTTOM, 0, 0);

                ImageView down = popView.findViewById(R.id.ay_pop_down);
                down.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popWindow.dismiss();
                    }
                });
                popView.findViewById(R.id.xiadan).setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // 下单
                        RadioButton zfb_Btn = popView.findViewById(R.id.zfb_Btn);
                        RadioButton wx_Btn = popView.findViewById(R.id.wxzf_Btn);
                        if (wx_Btn.isChecked()) {
                            type = 1;
                        } else if (zfb_Btn.isChecked()) {
                            type = 2;
                        }
                        Toast.makeText(CheckInActivity.this, "排期ID为：" + id + "数量" + selectedTableCount, Toast.LENGTH_SHORT).show();
                        String md = userInfoBean.getUserId() + "" + id + "" + selectedTableCount + "movie";
                        String s = EncryptUtil.MD5(md);
                        buyMovieTicketPresenter.requestNet(userInfoBean.getUserId(), userInfoBean.getSessionId(), id, selectedTableCount, s);
                    }
                });
                break;
            case R.id.img_abandon:
                finish();
                overridePendingTransition(R.anim.ac_in, R.anim.ac_out);
                break;
            default:

                break;
        }
    }

    class buy implements DataCall<Result> {
        @Override
        public void success(Result result) {
            Toast.makeText(CheckInActivity.this, "" + result.getMessage(), Toast.LENGTH_SHORT).show();
            if (result.getStatus().equals("0000")) {
                String orderId = result.getOrderId();
                if (type == 1) {
                    IRequest interfacea = NetWorks.getRequest().create(IRequest.class);
                    interfacea.pay(userInfoBean.getUserId(), userInfoBean.getSessionId(), type, orderId).subscribeOn(Schedulers.newThread())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Consumer<Result>() {
                                @Override
                                public void accept(Result payBean) throws Exception {
                                    PayReq req = new PayReq();
                                    req.appId = payBean.getAppId();
                                    req.partnerId = payBean.getPartnerId();
                                    req.prepayId = payBean.getPrepayId();
                                    req.nonceStr = payBean.getNonceStr();
                                    req.timeStamp = payBean.getTimeStamp();
                                    req.packageValue = payBean.getPackageValue();
                                    req.sign = payBean.getSign();
                                    // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
                                    //3.调用微信支付sdk支付方法
                                    api.sendReq(req);
                                }
                            });
                } else if (type == 2) {
                    Toast.makeText(CheckInActivity.this, "暂不支持支付宝支付，请谅解", Toast.LENGTH_SHORT).show();
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
        buyMovieTicketPresenter.unBind();
    }
}
