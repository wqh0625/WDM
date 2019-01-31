package com.bw.movie.presenter;

import com.bw.movie.bean.Result;
import com.bw.movie.core.http.NetWorks;
import com.bw.movie.core.interfase.DataCall;
import com.bw.movie.core.interfase.IRequest;
import io.reactivex.Observable;

/**
 * 作者: Wang on 2019/1/27 18:01
 * 寄语：加油！相信自己可以！！！
 */


public class BuyMovieTicketPresenter extends BasePresenter {
    public BuyMovieTicketPresenter(DataCall consumer) {
        super(consumer);
    }

    @Override
    protected Observable<Result> observable(Object...args) {
        IRequest iRequest = NetWorks.getRequest().create(IRequest.class);
        Observable<Result> resultObservable = iRequest.buyMovieTicket((int)args[0],(String)args[1],(int)args[2],(int)args[3],(String)args[4]);
        return resultObservable;
    }
}
