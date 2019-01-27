package demo.com.wdmoviedemo.presenter;

import demo.com.wdmoviedemo.core.http.NetWorks;
import demo.com.wdmoviedemo.core.interfase.DataCall;
import demo.com.wdmoviedemo.core.interfase.IRequest;
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
