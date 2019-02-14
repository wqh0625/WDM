package com.bw.movie.presenter;

import com.bw.movie.bean.Result;
import com.bw.movie.core.http.NetWorks;
import com.bw.movie.core.interfase.DataCall;
import com.bw.movie.core.interfase.IRequests;

import io.reactivex.Observable;

/**
 * date: 2019/2/14.
 * Created 王思敏
 * function:点赞
 */
public class LikePresenter extends BasePresenter {
    public LikePresenter(DataCall consumer) {
        super(consumer);
    }

    @Override
    protected Observable observable(Object... args) {
        IRequests iRequests = NetWorks.getRequest().create(IRequests.class);
        return iRequests.getLike((int)args[0],(String) args[1],(int)args[2]);
    }
}
