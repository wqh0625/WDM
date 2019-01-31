package com.bw.movie.presenter;

import com.bw.movie.core.http.NetWorks;
import com.bw.movie.core.interfase.DataCall;
import com.bw.movie.core.interfase.IRequest;
import io.reactivex.Observable;

/**
 * date: 2019/1/25.
 * <p>
 * function:
 */
public class FindUserHomeInfoPresenter extends BasePresenter {
    public FindUserHomeInfoPresenter(DataCall consumer) {
        super(consumer);
    }

    @Override
    protected Observable observable(Object... args) {
        IRequest iRequests = NetWorks.getRequest().create(IRequest.class);
        return iRequests.findUserHomeInfo((int) args[0], (String) args[1]);
    }
}
