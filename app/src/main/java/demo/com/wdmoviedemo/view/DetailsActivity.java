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

    private ImageView image_location;
    private TextView txt_location;
    private RelativeLayout top;
    private TextView txt_Cinemax;
    private TextView txt_ishit;
    private TextView txt_coming;
    private RelativeLayout center;
    private ViewPager details_vp;
    private ImageView image_back;
    private List<Fragment> fragments;
    private ImageView recommend_cinem_search_image;
    private EditText recommend_cinema_edname;
    private TextView recommend_cinema_textName;
    private RelativeLayout recommend_cinema_linear;
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
        details_vp.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return fragments.get(i);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        });
        details_vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
        position = intent.getIntExtra("position", 0);
//        image = intent.getIntExtra("image", 0);
        image_location = (ImageView) findViewById(R.id.image_location);
        txt_location = (TextView) findViewById(R.id.txt_location);
        top = (RelativeLayout) findViewById(R.id.top);
        txt_Cinemax = (TextView) findViewById(R.id.txt_Cinemax);
        txt_ishit = (TextView) findViewById(R.id.txt_ishit);
        txt_coming = (TextView) findViewById(R.id.txt_coming);
        center = (RelativeLayout) findViewById(R.id.center);
        details_vp = (ViewPager) findViewById(R.id.details_vp);
        image_back = (ImageView) findViewById(R.id.image_back);
        txt_Cinemax.setOnClickListener(this);
        txt_ishit.setOnClickListener(this);
        txt_coming.setOnClickListener(this);
        image_back.setOnClickListener(this);
        recommend_cinem_search_image = (ImageView) findViewById(R.id.recommend_cinem_search_image);
        recommend_cinem_search_image.setOnClickListener(this);
        recommend_cinema_edname = (EditText) findViewById(R.id.recommend_cinema_edname);
        recommend_cinema_edname.setOnClickListener(this);
        recommend_cinema_textName = (TextView) findViewById(R.id.recommend_cinema_textName);
        recommend_cinema_textName.setOnClickListener(this);
        recommend_cinema_linear = (RelativeLayout) findViewById(R.id.recommend_cinema_linear);
        recommend_cinema_linear.setOnClickListener(this);
        //适配器接口回调
        initPosition();
        //图片
//        initImage();
    }


    private void initPosition() {
        //接受position传递值判断显示页面
        if (position==0){
            details_vp.setCurrentItem(0);
            ChangeBackGround(0);
        }else if (position==1){
            details_vp.setCurrentItem(1);
            ChangeBackGround(1);
        }else if (position==2){
            details_vp.setCurrentItem(2);
            ChangeBackGround(2);
        }
    }
//    private void initImage() {
//        //接受首页箭头图片传过来的值
//        if (image==3){
//            details_vp.setCurrentItem(0);
//            ChangeBackGround(0);
//        }else if (image==4){
//            details_vp.setCurrentItem(1);
//            ChangeBackGround(1);
//        }else {
//            details_vp.setCurrentItem(2);
//            ChangeBackGround(2);
//        }
//    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_Cinemax:
                details_vp.setCurrentItem(0);
                ChangeBackGround(0);
                break;
            case R.id.txt_ishit:
                details_vp.setCurrentItem(1);
                ChangeBackGround(1);
                break;
            case R.id.txt_coming:
                details_vp.setCurrentItem(2);
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
                etName = recommend_cinema_edname.getText().toString().trim();
                if (TextUtils.isEmpty(etName)){
                    Toast.makeText(this, "输入内容不能为空", Toast.LENGTH_SHORT).show();
                }else {

                }

                break;
        }
    }
    /*设置伸展状态时的布局*/
    public void initExpand() {
        recommend_cinema_edname.setHint("CGV影城");
        recommend_cinema_edname.requestFocus();
        recommend_cinema_edname.setHintTextColor(Color.WHITE);
        recommend_cinema_textName.setText("搜索");
        recommend_cinema_textName.setVisibility(View.VISIBLE);
        recommend_cinema_edname.setVisibility(View.VISIBLE);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) recommend_cinema_linear.getLayoutParams();
        layoutParams.width = dip2px(210);
        layoutParams.setMargins(dip2px(0), dip2px(20), dip2px(0), dip2px(0));
        recommend_cinema_linear.setLayoutParams(layoutParams);
        recommend_cinema_edname.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                recommend_cinema_edname.setFocusable(true);
                recommend_cinema_edname.setFocusableInTouchMode(true);
                return false;
            }
        });
        //开始动画
        beginDelayedTransition(recommend_cinema_linear);

    }
    /*设置收缩状态时的布局*/
    private void initReduce() {
        recommend_cinema_edname.setCursorVisible(false);
        recommend_cinema_edname.setVisibility(View.GONE);
        recommend_cinema_textName.setVisibility(View.GONE);
        LinearLayout.LayoutParams LayoutParams = (LinearLayout.LayoutParams) recommend_cinema_linear.getLayoutParams();
        LayoutParams.width = dip2px(40);
        LayoutParams.setMargins(dip2px(0), dip2px(0), dip2px(0), dip2px(0));
        recommend_cinema_linear.setLayoutParams(LayoutParams);

        //隐藏键盘
        InputMethodManager imm = (InputMethodManager) this
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(this.getWindow()
                .getDecorView().getWindowToken(), 0);


        recommend_cinema_edname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recommend_cinema_edname.setCursorVisible(true);
            }
        });
        //开始动画
        beginDelayedTransition(recommend_cinema_linear);
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
        txt_Cinemax.setBackgroundResource(index == 0 ? R.drawable.details_bgs : R.drawable.details_back);
        //字体颜色
        txt_Cinemax.setTextColor(index == 0 ? Color.WHITE : Color.BLACK);
        txt_ishit.setBackgroundResource(index == 1 ? R.drawable.details_bgs : R.drawable.details_back);
        txt_ishit.setTextColor(index == 1 ? Color.WHITE : Color.BLACK);
        txt_coming.setBackgroundResource(index == 2 ? R.drawable.details_bgs : R.drawable.details_back);
        txt_coming.setTextColor(index == 2 ? Color.WHITE : Color.BLACK);
    }

}
