package com.bw.movie.presenter;

import com.bw.movie.core.http.NetWorks;
import com.bw.movie.core.interfase.DataCall;
import com.bw.movie.core.interfase.IRequests;
import io.reactivex.Observable;

/**
 * date: 2019/1/26.
 * Created 王思敏
 * function:查看电影详情
 */
public class ReviewPresenter extends BasePresenter {
    public ReviewPresenter(DataCall consumer) {
        super(consumer);
    }

    @Override
    protected Observable observable(Object... args) {
        IRequests iRequests = NetWorks.getRequest().create(IRequests.class);
        return iRequests.getReciew((int)args[0],(int)args[1],(int)args[2]);
    }
}
