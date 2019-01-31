package com.bw.movie.presenter;

import java.util.List;

import com.bw.movie.bean.Result;
import com.bw.movie.core.http.NetWorks;
import com.bw.movie.core.interfase.DataCall;
import com.bw.movie.core.interfase.IRequest;
import io.reactivex.Observable;

/**
 * date: 2019/1/23.
 * Created by Wang
 * function:首页轮播，正在上映
 */
public class CarouselPresenter extends BasePresenter{
    public CarouselPresenter(DataCall consumer) {
        super(consumer);
    }

    @Override
    protected Observable observable(Object... args) {
        IRequest iRequest = NetWorks.getRequest().create(IRequest.class);
        return iRequest.getcarousel( (int) args[0], (String) args[1], (int) args[2], (int) args[3]);
    }
}
