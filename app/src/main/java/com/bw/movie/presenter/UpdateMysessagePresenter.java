package com.bw.movie.presenter;

import com.bw.movie.bean.Result;
import com.bw.movie.core.http.NetWorks;
import com.bw.movie.core.interfase.DataCall;
import com.bw.movie.core.interfase.IRequest;
import com.bw.movie.bean.MyMessage;
import io.reactivex.Observable;

/**
 * 作者: Wang on 2019/1/29 20:39
 * 寄语：加油！相信自己可以！！！
 */


public class UpdateMysessagePresenter extends BasePresenter {
    public UpdateMysessagePresenter(DataCall consumer) {
        super(consumer);
    }

    @Override
    protected Observable observable(Object... args) {
        Observable<Result<MyMessage>> resultObservable = NetWorks.getRequest().create(IRequest.class).modifyUserI((int) args[0], (String) args[1], (String) args[2], (int) args[3], (String) args[4]);
        return resultObservable;
    }


}
