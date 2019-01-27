package demo.com.wdmoviedemo.presenter;

import demo.com.wdmoviedemo.core.http.NetWorks;
import demo.com.wdmoviedemo.core.interfase.DataCall;
import demo.com.wdmoviedemo.core.interfase.IRequest;
import io.reactivex.Observable;

/**
 * date: 2019/1/25.
 * <p>
 * function:
 */
public class FindCinemasListByMovieIdPresenter extends BasePresenter {
    public FindCinemasListByMovieIdPresenter(DataCall consumer) {
        super(consumer);
    }

    @Override
    protected Observable observable(Object... args) {
        IRequest iRequests = NetWorks.getRequest().create(IRequest.class);
        return iRequests.findMovieScheduleList((int) args[0]);
    }
}
