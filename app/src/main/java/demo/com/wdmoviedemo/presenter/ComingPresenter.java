package demo.com.wdmoviedemo.presenter;

import java.util.List;

import demo.com.wdmoviedemo.bean.CarouselData;
import demo.com.wdmoviedemo.bean.Result;
import demo.com.wdmoviedemo.core.http.NetWorks;
import demo.com.wdmoviedemo.core.interfase.DataCall;
import demo.com.wdmoviedemo.core.interfase.IRequests;
import io.reactivex.Observable;

/**
 * date: 2019/1/23.
 * Created 王思敏
 * function:即将上映
 */
public class ComingPresenter extends BasePresenter {
    public ComingPresenter(DataCall consumer) {
        super(consumer);
    }

    @Override
    protected Observable<Result<List<CarouselData>>> observable(Object... args) {
        IRequests iRequest = NetWorks.getRequest().create(IRequests.class);
        return iRequest.getonNext((int) args[0], (int) args[1]);
    }
}
