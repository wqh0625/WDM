package com.bw.movie.presenter;

import com.bw.movie.core.http.NetWorks;
import com.bw.movie.core.interfase.DataCall;
import com.bw.movie.core.interfase.IRequests;
import io.reactivex.Observable;

/**
 * date: 2019/1/26.
 * Created 王思敏
 * function:电影相关影院
 */
public class ListofCinemaPresenter extends BasePresenter {
    public ListofCinemaPresenter(DataCall consumer) {
        super(consumer);
    }

    @Override
    protected Observable observable(Object... args) {
        IRequests iRequests = NetWorks.getRequest().create(IRequests.class);
        return iRequests.getListofCinema((int)args[0]);
    }
}
