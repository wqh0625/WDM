package com.bw.movie.presenter;

import com.bw.movie.core.http.NetWorks;
import com.bw.movie.core.interfase.DataCall;
import com.bw.movie.core.interfase.IRequest;
import io.reactivex.Observable;

/**
 *
 */
public class FindMovieListByCinemaIdPresenter extends BasePresenter{
    public FindMovieListByCinemaIdPresenter(DataCall consumer) {
        super(consumer);
    }

    int page;
    @Override
    protected Observable observable(Object... args) {
        IRequest iRequest = NetWorks.getRequest().create(IRequest.class);

        return iRequest.findMovieListByCinemaId( (int) args[0] );
    }
}
