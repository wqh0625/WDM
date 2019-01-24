package demo.com.wdmoviedemo.view;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    private void initView() {
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
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
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
        }
    }
    public void ChangeBackGround(int index){
        //背景颜色
        txt_Cinemax.setBackgroundResource(index ==0?R.drawable.details_bgs:R.drawable.details_bg);
        //字体颜色
        txt_Cinemax.setTextColor(index ==0?Color.WHITE:Color.BLACK);
        txt_ishit.setBackgroundResource(index ==1?R.drawable.details_bgs:R.drawable.details_bg);
        txt_ishit.setTextColor(index ==1?Color.WHITE:Color.BLACK);
        txt_coming.setBackgroundResource(index ==2?R.drawable.details_bgs:R.drawable.details_bg);
        txt_coming.setTextColor(index ==2?Color.WHITE:Color.BLACK);
    }
}
