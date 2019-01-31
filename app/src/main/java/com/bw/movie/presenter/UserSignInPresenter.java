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
public class UserSignInPresenter extends BasePresenter {
    public UserSignInPresenter(DataCall consumer) {
        super(consumer);
    }

    @Override
    protected Observable observable(Object... args) {
        IRequest iRequest = NetWorks.getRequest().create(IRequest.class);
        return iRequest.userSignIn((int)args[0],(String) args[1]);
    }
}
