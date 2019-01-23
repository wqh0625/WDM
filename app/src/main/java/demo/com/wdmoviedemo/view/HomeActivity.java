package demo.com.wdmoviedemo.view;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import bawei.com.wdmoviedemo.R;
import demo.com.wdmoviedemo.view.fragment.CinemaFragment;
import demo.com.wdmoviedemo.view.fragment.HomeFragment;
import demo.com.wdmoviedemo.view.fragment.MyFragment;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    private FrameLayout frag;
    private ImageView homeactivity_imagefilm;
    private ImageView homeactivity_imagecinema;
    private ImageView homeactivity_imagemy;
    private FragmentManager supportFragmentManager;
    private FragmentTransaction beginTransaction;
    private HomeFragment homeFragment;
    private CinemaFragment cinemaFragment;
    private MyFragment myFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        initEvent();

    }

    private void initEvent() {
        supportFragmentManager = getSupportFragmentManager();

        beginTransaction = supportFragmentManager
                .beginTransaction();
        //Fragment非空判断
        if(homeFragment == null){

            beginTransaction = supportFragmentManager.beginTransaction();


            homeFragment = new HomeFragment();
        }
        if(cinemaFragment == null){
            cinemaFragment = new CinemaFragment();
        }
        if(myFragment == null){
            myFragment = new MyFragment();
        }

        //添加Frgamnt
        beginTransaction.add(R.id.frag, homeFragment);
        beginTransaction.add(R.id.frag, cinemaFragment);
        beginTransaction.add(R.id.frag, myFragment);
        //显示和隐藏
        beginTransaction.hide(cinemaFragment);
        beginTransaction.hide(myFragment);
        beginTransaction.show(homeFragment);

        //提交
        beginTransaction.commit();
    }

    private void initData() {

    }

    private void initView() {
        frag = (FrameLayout) findViewById(R.id.frag);
        homeactivity_imagefilm = (ImageView) findViewById(R.id.homeactivity_imagefilm);
        homeactivity_imagecinema = (ImageView) findViewById(R.id.homeactivity_imagecinema);
        homeactivity_imagemy = (ImageView) findViewById(R.id.homeactivity_imagemy);
        homeactivity_imagefilm.setOnClickListener(this);
        homeactivity_imagecinema.setOnClickListener(this);
        homeactivity_imagemy.setOnClickListener(this);
        homeactivity_imagefilm.setImageResource(R.drawable.homes);

        AnimatorSet set = new AnimatorSet();
        ObjectAnimator o1 = ObjectAnimator.ofFloat(homeactivity_imagefilm, "scaleX", 1.17f);
        ObjectAnimator o4 = ObjectAnimator.ofFloat(homeactivity_imagefilm, "scaleY", 1.17f);
        //存入集合
        set.playTogether(o1, o4);
        //开始执行
        set.start();
    }

    @Override
    public void onClick(View view) {
        FragmentTransaction transaction = supportFragmentManager
                .beginTransaction();
        switch (view.getId()){
            case R.id.homeactivity_imagefilm:
                //切换电影页
                isimagefilm(transaction);
                break;
            case R.id.homeactivity_imagecinema:
                //切换影院页
                isimagecinema(transaction);
                break;
            case R.id.homeactivity_imagemy:
                //切换我的页面
                isimagemy(transaction);
                break;
        }
        transaction.commit();
    }

    //切换电影页
    private void isimagefilm(FragmentTransaction transaction) {
        //显示和隐藏
        transaction.hide(cinemaFragment);
        transaction.hide(myFragment);
        transaction.show(homeFragment);
        //切换图片
        homeactivity_imagefilm.setImageResource(R.drawable.homes);
        homeactivity_imagecinema.setImageResource(R.drawable.cinema);
        homeactivity_imagemy.setImageResource(R.drawable.my);
        //属性动画改变图片大小
        AnimatorSet set = new AnimatorSet();
        ObjectAnimator o1 = ObjectAnimator.ofFloat(homeactivity_imagefilm, "scaleX",1.17f);
        ObjectAnimator o2 = ObjectAnimator.ofFloat(homeactivity_imagecinema, "scaleX",1.0f );
        ObjectAnimator o3 = ObjectAnimator.ofFloat(homeactivity_imagemy, "scaleX",1.0f );

        ObjectAnimator o4 = ObjectAnimator.ofFloat(homeactivity_imagefilm,"scaleY",1.17f);
        ObjectAnimator o5 = ObjectAnimator.ofFloat(homeactivity_imagecinema,"scaleY",1.0f);
        ObjectAnimator o6 = ObjectAnimator.ofFloat(homeactivity_imagemy,"scaleY",1.0f);

        //存入集合
        set.playTogether(o1,o2,o3,o4,o5,o6);
        //开始执行
        set.start();
    }

    //切换影院页
    public  void isimagecinema(FragmentTransaction transaction) {
        //显示和隐藏
        transaction.show(cinemaFragment);
        transaction.hide(myFragment);
        transaction.hide(homeFragment);
        //切换图片
        homeactivity_imagefilm.setImageResource(R.drawable.home);
        homeactivity_imagecinema.setImageResource(R.drawable.cinemas);
        homeactivity_imagemy.setImageResource(R.drawable.my);

        //属性动画改变图片大小
        AnimatorSet set = new AnimatorSet();
        ObjectAnimator o1 = ObjectAnimator.ofFloat(homeactivity_imagefilm, "scaleX",1.0f);
        ObjectAnimator o2 = ObjectAnimator.ofFloat(homeactivity_imagecinema, "scaleX",1.17f );
        ObjectAnimator o3 = ObjectAnimator.ofFloat(homeactivity_imagemy, "scaleX",1.0f );

        ObjectAnimator o4 = ObjectAnimator.ofFloat(homeactivity_imagefilm,"scaleY",1.0f);
        ObjectAnimator o5 = ObjectAnimator.ofFloat(homeactivity_imagecinema,"scaleY",1.17f);
        ObjectAnimator o6 = ObjectAnimator.ofFloat(homeactivity_imagemy,"scaleY",1.0f);

        //存入集合
        set.playTogether(o1,o2,o3,o4,o5,o6);
        //开始执行
        set.start();

    }
    //切换我的页面
    private void isimagemy(FragmentTransaction transaction) {
        //显示和隐藏
        transaction.hide(cinemaFragment);
        transaction.show(myFragment);
        transaction.hide(homeFragment);
        //切换图片
        homeactivity_imagefilm.setImageResource(R.drawable.home);
        homeactivity_imagecinema.setImageResource(R.drawable.cinema);
        homeactivity_imagemy.setImageResource(R.drawable.mys);

        //属性动画改变图片大小
        AnimatorSet set = new AnimatorSet();
        ObjectAnimator o1 = ObjectAnimator.ofFloat(homeactivity_imagefilm, "scaleX",1.0f);
        ObjectAnimator o2 = ObjectAnimator.ofFloat(homeactivity_imagecinema, "scaleX",1.0f );
        ObjectAnimator o3 = ObjectAnimator.ofFloat(homeactivity_imagemy, "scaleX",1.17f );

        ObjectAnimator o4 = ObjectAnimator.ofFloat(homeactivity_imagefilm,"scaleY",1.0f);
        ObjectAnimator o5 = ObjectAnimator.ofFloat(homeactivity_imagecinema,"scaleY",1.0f);
        ObjectAnimator o6 = ObjectAnimator.ofFloat(homeactivity_imagemy,"scaleY",1.17f);

        //存入集合
        set.playTogether(o1,o2,o3,o4,o5,o6);
        //开始执行
        set.start();

    }
}
