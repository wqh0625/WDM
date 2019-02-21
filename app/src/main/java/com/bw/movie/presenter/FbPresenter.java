package com.bw.movie.presenter;

import com.bw.movie.bean.Result;
import com.bw.movie.core.http.NetWorks;
import com.bw.movie.core.interfase.DataCall;
import com.bw.movie.core.interfase.IRequests;

import io.reactivex.Observable;

/**
 * 作者: Wang on 2019/2/21 15:41
 * 寄语：加油！相信自己可以！！！
 */


public class FbPresenter extends BasePresenter {
    public FbPresenter(DataCall consumer) {
        super(consumer);
    }

    @Override
    protected Observable<Result> observable(Object... args) {
        IRequests iRequests = NetWorks.getRequest().create(IRequests.class);
        return iRequests.discuss((int)args[0],(String)args[1],(int)args[2],(String)args[3]);
    }
}
