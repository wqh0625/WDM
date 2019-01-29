package demo.com.wdmoviedemo.presenter;

import java.util.List;

import demo.com.wdmoviedemo.bean.Result;
import demo.com.wdmoviedemo.core.http.NetWorks;
import demo.com.wdmoviedemo.core.interfase.DataCall;
import demo.com.wdmoviedemo.core.interfase.IRequest;
import io.reactivex.Observable;

/**
 * date: 2019/1/23.
 * Created by Wang
 * function:首页轮播，正在上映
 */
public class CarouselPresenter extends BasePresenter{
    public CarouselPresenter(DataCall consumer) {
        super(consumer);
    }

    @Override
    protected Observable observable(Object... args) {
        IRequest iRequest = NetWorks.getRequest().create(IRequest.class);
        return iRequest.getcarousel( (int) args[0], (String) args[1], (int) args[2], (int) args[3]);
    }
}
