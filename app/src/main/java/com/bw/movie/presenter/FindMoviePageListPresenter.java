package com.bw.movie.presenter;

import com.bw.movie.core.http.NetWorks;
import com.bw.movie.core.interfase.DataCall;
import com.bw.movie.core.interfase.IRequest;
import io.reactivex.Observable;

/**
 *
 */
public class FindMoviePageListPresenter extends BasePresenter{
    public FindMoviePageListPresenter(DataCall consumer) {
        super(consumer);
    }

    int page=1;
    int count = 10;
    @Override
    protected Observable observable(Object... args) {
        IRequest iRequest = NetWorks.getRequest().create(IRequest.class);
        boolean arg = (boolean) args[2];

        if (arg) {
            page=1;
        }else{
            page++;
        }
        return iRequest.findMoviePageList( (int) args[0], (String) args[1],page,count);
    }
}
