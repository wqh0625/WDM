package com.bw.movie.presenter;

import com.bw.movie.core.http.NetWorks;
import com.bw.movie.core.interfase.DataCall;
import com.bw.movie.core.interfase.IRequest;
import io.reactivex.Observable;

/**
 *
 */
public class FindRecommendCinemasPresenter extends BasePresenter{
    public FindRecommendCinemasPresenter(DataCall consumer) {
        super(consumer);
    }

    int page;
    @Override
    protected Observable observable(Object... args) {
        IRequest iRequest = NetWorks.getRequest().create(IRequest.class);
        boolean arg = (boolean) args[2];

        if (arg) {
            page=1;
        }else{
            page++;
        }
        return iRequest.findRecommendCinemas( (int) args[0], (String) args[1],page,20);
    }
}
