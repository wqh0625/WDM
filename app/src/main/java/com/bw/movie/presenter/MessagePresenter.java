package com.bw.movie.presenter;

import com.bw.movie.bean.Result;
import com.bw.movie.core.http.NetWorks;
import com.bw.movie.core.interfase.DataCall;
import com.bw.movie.core.interfase.IRequests;
import io.reactivex.Observable;

/**
 * date: 2019/1/29.
 * Created 王思敏
 * function:系统消息
 */
public class MessagePresenter extends BasePresenter {

    private IRequests iRequests;

    public MessagePresenter(DataCall consumer) {
        super(consumer);
    }

    @Override
    protected Observable observable(Object... args) {
        iRequests = NetWorks.getRequest().create(IRequests.class);
        return iRequests.getMessage((int)args[0],(String) args[1],(int)args[2],(int)args[3]);
    }
}
