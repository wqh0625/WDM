package com.bw.movie.presenter;

import com.bw.movie.core.http.NetWorks;
import com.bw.movie.core.interfase.DataCall;
import com.bw.movie.core.interfase.IRequest;
import io.reactivex.Observable;

/**
 * date: 2019/1/23.
 * Created by Administrator
 * function:正在热映
 */
public class IsHitPresenter extends BasePresenter {
    public IsHitPresenter(DataCall consumer) {
        super(consumer);
    }

    @Override
    protected Observable observable(Object... args) {
        IRequest iRequest = NetWorks.getRequest().create(IRequest.class);
        return iRequest.getisHit( (int) args[0], (String) args[1],(int) args[2], (int) args[3]);
    }
}
