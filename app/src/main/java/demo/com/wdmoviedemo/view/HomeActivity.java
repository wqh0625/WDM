package demo.com.wdmoviedemo.view;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.bw.movie.R;
import com.umeng.analytics.MobclickAgent;

import java.sql.SQLException;
import java.util.List;

import demo.com.wdmoviedemo.bean.UserInfoBean;
import demo.com.wdmoviedemo.core.base.BaseActivity;
import demo.com.wdmoviedemo.core.dao.DbManager;
import demo.com.wdmoviedemo.view.fragment.CinemaFragment;
import demo.com.wdmoviedemo.view.fragment.HomeFragment;
import demo.com.wdmoviedemo.view.fragment.MyFragment;

import static demo.com.wdmoviedemo.core.utils.MyApp.getContext;

public class HomeActivity extends BaseActivity implements View.OnClickListener {
    private FrameLayout frag;
    private ImageView homeactivityImagefilm;
    private ImageView homeactivityImagecinema;
    private ImageView homeactivityImagemy;
    private FragmentManager supportFragmentManager;
    private FragmentTransaction beginTransaction;
    private HomeFragment homeFragment;
    private CinemaFragment cinemaFragment;
    private MyFragment myFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        UserInfoBean userInfoBeanaaa = null;
        List<UserInfoBean> student = this.student;
//        Toast.makeText(this, "DbSize" + student.size(), Toast.LENGTH_SHORT).show();
        if (student != null && student.size() > 0) {
            userInfoBeanaaa = student.get(0);
        }
        if (userInfoBeanaaa != null) {
            Log.v("数据库登录数据--", student.size() + " " + userInfoBeanaaa.toString());
        }

        initV();
        initData();
        initEvent();

    }

    private void initEvent() {
        supportFragmentManager = getSupportFragmentManager();

        beginTransaction = supportFragmentManager
                .beginTransaction();
        //Fragment非空判断
        if (homeFragment == null) {

            beginTransaction = supportFragmentManager.beginTransaction();


            homeFragment = new HomeFragment();
        }
        if (cinemaFragment == null) {
            cinemaFragment = new CinemaFragment();
        }
        if (myFragment == null) {
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


    private void initV() {
        frag = (FrameLayout) findViewById(R.id.frag);
        homeactivityImagefilm = (ImageView) findViewById(R.id.homeactivity_imagefilm);
        homeactivityImagecinema = (ImageView) findViewById(R.id.homeactivity_imagecinema);
        homeactivityImagemy = (ImageView) findViewById(R.id.homeactivity_imagemy);
        homeactivityImagefilm.setOnClickListener(this);
        homeactivityImagecinema.setOnClickListener(this);
        homeactivityImagemy.setOnClickListener(this);
        homeactivityImagefilm.setImageResource(R.drawable.homes);

        AnimatorSet set = new AnimatorSet();
        ObjectAnimator o1 = ObjectAnimator.ofFloat(homeactivityImagefilm, "scaleX", 1.17f);
        ObjectAnimator o4 = ObjectAnimator.ofFloat(homeactivityImagefilm, "scaleY", 1.17f);
        //存入集合
        set.playTogether(o1, o4);
        //开始执行
        set.start();
    }

    @Override
    public void onClick(View view) {
        FragmentTransaction transaction = supportFragmentManager
                .beginTransaction();
        switch (view.getId()) {
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
                default:
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
        homeactivityImagefilm.setImageResource(R.drawable.homes);
        homeactivityImagecinema.setImageResource(R.drawable.cinema);
        homeactivityImagemy.setImageResource(R.drawable.my);
        //属性动画改变图片大小
        AnimatorSet set = new AnimatorSet();
        ObjectAnimator o1 = ObjectAnimator.ofFloat(homeactivityImagefilm, "scaleX", 1.17f);
        ObjectAnimator o2 = ObjectAnimator.ofFloat(homeactivityImagecinema, "scaleX", 1.0f);
        ObjectAnimator o3 = ObjectAnimator.ofFloat(homeactivityImagemy, "scaleX", 1.0f);

        ObjectAnimator o4 = ObjectAnimator.ofFloat(homeactivityImagefilm, "scaleY", 1.17f);
        ObjectAnimator o5 = ObjectAnimator.ofFloat(homeactivityImagecinema, "scaleY", 1.0f);
        ObjectAnimator o6 = ObjectAnimator.ofFloat(homeactivityImagemy, "scaleY", 1.0f);

        //存入集合
        set.playTogether(o1, o2, o3, o4, o5, o6);
        //开始执行
        set.start();
    }

    //切换影院页
    public void isimagecinema(FragmentTransaction transaction) {
        //显示和隐藏
        transaction.show(cinemaFragment);
        transaction.hide(myFragment);
        transaction.hide(homeFragment);
        //切换图片
        homeactivityImagefilm.setImageResource(R.drawable.home);
        homeactivityImagecinema.setImageResource(R.drawable.cinemas);
        homeactivityImagemy.setImageResource(R.drawable.my);

        //属性动画改变图片大小
        AnimatorSet set = new AnimatorSet();
        ObjectAnimator o1 = ObjectAnimator.ofFloat(homeactivityImagefilm, "scaleX", 1.0f);
        ObjectAnimator o2 = ObjectAnimator.ofFloat(homeactivityImagecinema, "scaleX", 1.17f);
        ObjectAnimator o3 = ObjectAnimator.ofFloat(homeactivityImagemy, "scaleX", 1.0f);

        ObjectAnimator o4 = ObjectAnimator.ofFloat(homeactivityImagefilm, "scaleY", 1.0f);
        ObjectAnimator o5 = ObjectAnimator.ofFloat(homeactivityImagecinema, "scaleY", 1.17f);
        ObjectAnimator o6 = ObjectAnimator.ofFloat(homeactivityImagemy, "scaleY", 1.0f);

        //存入集合
        set.playTogether(o1, o2, o3, o4, o5, o6);
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
        homeactivityImagefilm.setImageResource(R.drawable.home);
        homeactivityImagecinema.setImageResource(R.drawable.cinema);
        homeactivityImagemy.setImageResource(R.drawable.mys);

        //属性动画改变图片大小
        AnimatorSet set = new AnimatorSet();
        ObjectAnimator o1 = ObjectAnimator.ofFloat(homeactivityImagefilm, "scaleX", 1.0f);
        ObjectAnimator o2 = ObjectAnimator.ofFloat(homeactivityImagecinema, "scaleX", 1.0f);
        ObjectAnimator o3 = ObjectAnimator.ofFloat(homeactivityImagemy, "scaleX", 1.17f);

        ObjectAnimator o4 = ObjectAnimator.ofFloat(homeactivityImagefilm, "scaleY", 1.0f);
        ObjectAnimator o5 = ObjectAnimator.ofFloat(homeactivityImagecinema, "scaleY", 1.0f);
        ObjectAnimator o6 = ObjectAnimator.ofFloat(homeactivityImagemy, "scaleY", 1.17f);

        //存入集合
        set.playTogether(o1, o2, o3, o4, o5, o6);
        //开始执行
        set.start();

    }

    @Override
    public void onResume() {
        super.onResume();

        MobclickAgent.onPageStart("主页面");
        MobclickAgent.onResume(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("主页面");
        MobclickAgent.onPause(this);
    }
}
