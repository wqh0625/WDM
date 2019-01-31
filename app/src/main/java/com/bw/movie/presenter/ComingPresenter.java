package com.bw.movie.presenter;

import com.bw.movie.core.http.NetWorks;
import com.bw.movie.core.interfase.DataCall;
import com.bw.movie.core.interfase.IRequest;
import io.reactivex.Observable;

/**
 * date: 2019/1/23.
 * Created 王思敏
 * function:即将上映
 */
public class ComingPresenter extends BasePresenter {
    public ComingPresenter(DataCall consumer) {
        super(consumer);
    }

    @Override
    protected Observable observable(Object... args) {
        IRequest iRequest = NetWorks.getRequest().create(IRequest.class);
        return iRequest.getonNext((int) args[0], (String) args[1],(int)args[2],(int)args[3]);
    }
}
