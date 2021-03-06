package com.bw.movie.presenter;

import com.bw.movie.core.http.NetWorks;
import com.bw.movie.core.interfase.DataCall;
import com.bw.movie.core.interfase.IRequest;
import com.bw.movie.core.interfase.IRequests;
import io.reactivex.Observable;

/**
 * date: 2019/1/25.
 * Created 王思敏
 * function:
 */
public class CancelConcernPresenter extends BasePresenter {
    public CancelConcernPresenter(DataCall consumer) {
        super(consumer);
    }

    @Override
    protected Observable observable(Object... args) {
        IRequest iRequests = NetWorks.getRequest().create(IRequest.class);
        return iRequests.cancelFollowMovie((int)args[0],(String) args[1],(int)args[2]);
    }
}
