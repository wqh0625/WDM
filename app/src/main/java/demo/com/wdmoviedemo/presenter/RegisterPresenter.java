package demo.com.wdmoviedemo.presenter;

import demo.com.wdmoviedemo.core.http.NetWorks;
import demo.com.wdmoviedemo.core.interfase.DataCall;
import demo.com.wdmoviedemo.core.interfase.IRequest;
import io.reactivex.Observable;

/**
 * 作者: Wang on 2019/1/22 18:37
 * 寄语：加油！相信自己可以！！！
 */


public class RegisterPresenter extends BasePresenter {
    public RegisterPresenter(DataCall consumer) {
        super(consumer);
    }

    @Override
    protected Observable observable(Object... args) {
        return NetWorks.getRequest().create(IRequest.class).register(
                (String)args[0] ,
                (String)args[1],
                (String)args[2],
                (String)args[3],
                (int)args[4],
                (String)args[5],
                (String)args[6],
                (String)args[7],
                (String)args[8],
                (String)args[9],
                (String)args[10]);
    }
}
