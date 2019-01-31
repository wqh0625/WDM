package com.bw.movie.presenter;

import com.bw.movie.core.http.NetWorks;
import com.bw.movie.core.interfase.DataCall;
import com.bw.movie.core.interfase.IRequests;
import io.reactivex.Observable;

/**
 * date: 2019/1/27.
 * Created 王思敏
 * function:电影id和影院id查询电影信息
 */
public class TicketPresenter extends BasePresenter {
    public TicketPresenter(DataCall consumer) {
        super(consumer);
    }

    @Override
    protected Observable observable(Object... args) {
        IRequests iRequests = NetWorks.getRequest().create(IRequests.class);
        return iRequests.getTicketDetails((int)args[0],(int)args[1]);
    }
}
