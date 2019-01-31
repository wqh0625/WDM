package com.bw.movie.presenter;

import com.bw.movie.bean.Result;
import com.bw.movie.core.http.NetWorks;
import com.bw.movie.core.interfase.DataCall;
import com.bw.movie.core.interfase.IRequests;
import io.reactivex.Observable;

/**
 * date: 2019/1/29.
 * Created 王思敏
 * function:待付款
 */
public class ObligationPresenter extends BasePresenter {
    public ObligationPresenter(DataCall consumer) {
        super(consumer);
    }

    @Override
    protected Observable observable(Object... args) {
        IRequests iRequests = NetWorks.getRequest().create(IRequests.class);
        return iRequests.getObligation((int)args[0],(String) args[1],(int)args[2],(int)args[3],(int)args[4]);
    }
}
