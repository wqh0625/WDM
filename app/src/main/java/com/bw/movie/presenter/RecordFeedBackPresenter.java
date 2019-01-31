package com.bw.movie.presenter;

import com.bw.movie.core.http.NetWorks;
import com.bw.movie.core.interfase.DataCall;
import com.bw.movie.core.interfase.IRequest;
import io.reactivex.Observable;

/**
 *
 */
public class RecordFeedBackPresenter extends BasePresenter{
    public RecordFeedBackPresenter(DataCall consumer) {
        super(consumer);
    }

    @Override
    protected Observable observable(Object... args) {
        IRequest iRequest = NetWorks.getRequest().create(IRequest.class);
        return iRequest.recordFeedBack( (int) args[0], (String) args[1],(String)args[2] );
    }
}
