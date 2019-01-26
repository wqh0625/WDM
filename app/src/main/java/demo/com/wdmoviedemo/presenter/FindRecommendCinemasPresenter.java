package demo.com.wdmoviedemo.presenter;

import demo.com.wdmoviedemo.core.http.NetWorks;
import demo.com.wdmoviedemo.core.interfase.DataCall;
import demo.com.wdmoviedemo.core.interfase.IRequest;
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
