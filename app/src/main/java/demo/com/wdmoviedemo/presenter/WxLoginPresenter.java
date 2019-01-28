package demo.com.wdmoviedemo.presenter;

import demo.com.wdmoviedemo.bean.Result;
import demo.com.wdmoviedemo.core.http.NetWorks;
import demo.com.wdmoviedemo.core.interfase.DataCall;
import demo.com.wdmoviedemo.core.interfase.IRequests;
import io.reactivex.Observable;

/**
 * date: 2019/1/28.
 * Created 王思敏
 * function:微信登录
 */
public class WxLoginPresenter extends BasePresenter{
    public WxLoginPresenter(DataCall consumer) {
        super(consumer);
    }

    @Override
    protected Observable observable(Object... args) {
        IRequests iRequests = NetWorks.getRequest().create(IRequests.class);
        return iRequests.getWxlogin((String) args[0]);
    }
}
