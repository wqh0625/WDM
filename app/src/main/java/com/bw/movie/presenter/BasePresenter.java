package com.bw.movie.presenter;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.Toast;

import com.bw.movie.R;

import com.bw.movie.bean.NearbyData;
import com.bw.movie.bean.Result;
import com.bw.movie.core.base.BaseActivity;
import com.bw.movie.core.exception.ApiException;
import com.bw.movie.core.exception.CustomException;
import com.bw.movie.core.exception.ResponseTransformer;
import com.bw.movie.core.http.NetWorks;
import com.bw.movie.core.interfase.DataCall;
import com.bw.movie.core.utils.FileUtils;
import com.bw.movie.core.utils.MyApp;
import com.bw.movie.view.LoginActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者: Wang on 2019/1/22 16:08
 * 寄语：加油！相信自己可以！！！
 */


public abstract class BasePresenter {
    private DataCall consumer;
    private boolean runing;

    public BasePresenter(DataCall consumer) {
        this.consumer = consumer;
    }

    public void requestNet(final Object... args) {
        if (runing) {
            return;
        }
        runing = true;
        if (!NetWorks.isNetworkConnected(MyApp.getContext())) {
            consumer.fail(new ApiException(CustomException.NETWORK_ERROR, "网络错误"));
            runing = false;
        } else {
            observable(args)
                    .compose(ResponseTransformer.handleResult())
                    .compose(new ObservableTransformer() {
                        @Override
                        public ObservableSource apply(Observable upstream) {
                            return upstream.subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread());
                        }
                    })
//                .subscribeOn(Schedulers.newThread())// 子线程
//                .observeOn(AndroidSchedulers.mainThread())// 主线程
                    .subscribe(new Consumer<Result>() {
                        @Override
                        public void accept(Result o) throws Exception {
                            if (consumer == null) {
                                return;
                            }
                            o.setArgs(args);
                            consumer.success(o);
                            runing = false;
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            if (consumer == null) {
                                return;
                            }
                            consumer.fail(CustomException.handleException(throwable));
                            runing = false;
                        }
                    });
        }


    }

    protected abstract Observable<Result> observable(Object... args);

    public void unBind() {
        this.consumer = null;
    }

    public boolean isRuning() {
        return runing;
    }

}
