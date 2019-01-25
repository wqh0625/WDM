package demo.com.wdmoviedemo.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;

import java.util.ArrayList;
import java.util.List;

import demo.com.wdmoviedemo.view.fragment.CinemaxFragment;
import demo.com.wdmoviedemo.view.fragment.ComingFragment;
import demo.com.wdmoviedemo.view.fragment.IsHitFragment;


public class DetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView imageLocation;
    private TextView txtLocation;
    private RelativeLayout top;
    private TextView txtCinemax;
    private TextView txtIshit;
    private TextView txtComing;
    private RelativeLayout center;
    private ViewPager detailsVp;
    private ImageView imageBack;
    private List<Fragment> fragments;
    private ImageView recommendCinemSearchImage;
    private EditText recommendCinemaEdname;
    private TextView recommendCinemaTextName;
    private RelativeLayout recommendCinemaLinear;
    boolean flag = true;
    private AutoTransition transition;
    private int position;
    private int image;
    private String etName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        initView();
        initData();

    }

    private void initData() {
        fragments = new ArrayList<>();
        fragments.add(new CinemaxFragment());
        fragments.add(new IsHitFragment());
        fragments.add(new ComingFragment());
        detailsVp.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return fragments.get(i);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        });
        detailsVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                ChangeBackGround(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    private void initView() {
        Intent intent = getIntent();
        position = intent.getExtras().getInt("position");
        image = intent.getExtras().getInt("image");
        imageLocation = (ImageView) findViewById(R.id.image_location);
        txtLocation = (TextView) findViewById(R.id.txt_location);
        top = (RelativeLayout) findViewById(R.id.top);
        txtCinemax = (TextView) findViewById(R.id.txt_Cinemax);
        txtIshit = (TextView) findViewById(R.id.txt_ishit);
        txtComing = (TextView) findViewById(R.id.txt_coming);
        center = (RelativeLayout) findViewById(R.id.center);
        detailsVp = (ViewPager) findViewById(R.id.details_vp);
        imageBack = (ImageView) findViewById(R.id.image_back);
        txtCinemax.setOnClickListener(this);
        txtIshit.setOnClickListener(this);
        txtComing.setOnClickListener(this);
        imageBack.setOnClickListener(this);
        recommendCinemSearchImage = (ImageView) findViewById(R.id.recommend_cinem_search_image);
        recommendCinemSearchImage.setOnClickListener(this);
        recommendCinemaEdname = (EditText) findViewById(R.id.recommend_cinema_edname);
        recommendCinemaEdname.setOnClickListener(this);
        recommendCinemaTextName = (TextView) findViewById(R.id.recommend_cinema_textName);
        recommendCinemaTextName.setOnClickListener(this);
        recommendCinemaLinear = (RelativeLayout) findViewById(R.id.recommend_cinema_linear);
        recommendCinemaLinear.setOnClickListener(this);
        //适配器接口回调
        initPosition();
        //图片
//        initImage();
    }


    private void initPosition() {
        //接受position传递值判断显示页面
        if (position==0){
            detailsVp.setCurrentItem(0);
            ChangeBackGround(0);
        }else if (position==1){
            detailsVp.setCurrentItem(1);
            ChangeBackGround(1);
        }else if (position==2){
            detailsVp.setCurrentItem(2);
            ChangeBackGround(2);
        }
    }
    private void initImage() {
        //接受首页箭头图片传过来的值
        if (image==0){
            detailsVp.setCurrentItem(0);
            ChangeBackGround(0);
        }else if (image==1){
            detailsVp.setCurrentItem(1);
            ChangeBackGround(1);
        }else {
            detailsVp.setCurrentItem(2);
            ChangeBackGround(2);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_Cinemax:
                detailsVp.setCurrentItem(0);
                ChangeBackGround(0);
                break;
            case R.id.txt_ishit:
                detailsVp.setCurrentItem(1);
                ChangeBackGround(1);
                break;
            case R.id.txt_coming:
                detailsVp.setCurrentItem(2);
                ChangeBackGround(2);
                break;
            case R.id.image_back:
                finish();
                break;
            case R.id.recommend_cinem_search_image:
                if(flag){
                    initExpand();//点击搜索 伸展
                }else{
                    initReduce();//点击text收缩
                }
                flag  = !flag;
                break;
            case R.id.recommend_cinema_textName:
                //点击搜索,获取输入的值
                etName = recommendCinemaEdname.getText().toString().trim();
                if (TextUtils.isEmpty(etName)){
                    Toast.makeText(this, "输入内容不能为空", Toast.LENGTH_SHORT).show();
                }else {

                }

                break;
        }
    }
    /*设置伸展状态时的布局*/
    public void initExpand() {
        recommendCinemaEdname.setHint("CGV影城");
        recommendCinemaEdname.requestFocus();
        recommendCinemaEdname.setHintTextColor(Color.WHITE);
        recommendCinemaTextName.setText("搜索");
        recommendCinemaTextName.setVisibility(View.VISIBLE);
        recommendCinemaEdname.setVisibility(View.VISIBLE);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) recommendCinemaLinear.getLayoutParams();
        layoutParams.width = dip2px(210);
        layoutParams.setMargins(dip2px(0), dip2px(20), dip2px(0), dip2px(0));
        recommendCinemaLinear.setLayoutParams(layoutParams);
        recommendCinemaEdname.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                recommendCinemaEdname.setFocusable(true);
                recommendCinemaEdname.setFocusableInTouchMode(true);
                return false;
            }
        });
        //开始动画
        beginDelayedTransition(recommendCinemaLinear);

    }
    /*设置收缩状态时的布局*/
    private void initReduce() {
        recommendCinemaEdname.setCursorVisible(false);
        recommendCinemaEdname.setVisibility(View.GONE);
        recommendCinemaTextName.setVisibility(View.GONE);
        LinearLayout.LayoutParams LayoutParams = (LinearLayout.LayoutParams) recommendCinemaLinear.getLayoutParams();
        LayoutParams.width = dip2px(40);
        LayoutParams.setMargins(dip2px(0), dip2px(0), dip2px(0), dip2px(0));
        recommendCinemaLinear.setLayoutParams(LayoutParams);

        //隐藏键盘
        InputMethodManager imm = (InputMethodManager) this
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(this.getWindow()
                .getDecorView().getWindowToken(), 0);


        recommendCinemaEdname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recommendCinemaEdname.setCursorVisible(true);
            }
        });
        //开始动画
        beginDelayedTransition(recommendCinemaLinear);
    }


    private void beginDelayedTransition(ViewGroup view) {
        transition = new AutoTransition();
        transition.setDuration(500);
        TransitionManager.beginDelayedTransition(view, transition);
    }

    private int dip2px(float dpVale) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpVale * scale + 0.5f);
    }

    public void ChangeBackGround(int index) {
        //背景颜色
        txtCinemax.setBackgroundResource(index == 0 ? R.drawable.details_bgs : R.drawable.details_back);
        //字体颜色
        txtCinemax.setTextColor(index == 0 ? Color.WHITE : Color.BLACK);
        txtIshit.setBackgroundResource(index == 1 ? R.drawable.details_bgs : R.drawable.details_back);
        txtIshit.setTextColor(index == 1 ? Color.WHITE : Color.BLACK);
        txtComing.setBackgroundResource(index == 2 ? R.drawable.details_bgs : R.drawable.details_back);
        txtComing.setTextColor(index == 2 ? Color.WHITE : Color.BLACK);
    }

}
