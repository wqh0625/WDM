package com.bw.movie.presenter;

import com.bw.movie.bean.Result;
import com.bw.movie.core.http.NetWorks;
import com.bw.movie.core.interfase.DataCall;
import com.bw.movie.core.interfase.IRequests;

import io.reactivex.Observable;

/**
 * date: 2019/2/12.
 * Created 王思敏
 * function:新版本
 */
public class VersionsPresenter extends BasePresenter{
    public VersionsPresenter(DataCall consumer) {
        super(consumer);
    }

    @Override
    protected Observable observable(Object... args) {
        IRequests iRequests = NetWorks.getRequest().create(IRequests.class);
        return iRequests.getVersions((int)args[0],(String) args[1],(String) args[2]);
    }
}
