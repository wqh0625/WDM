package com.bw.movie.presenter;

import com.bw.movie.bean.Result;
import com.bw.movie.core.http.NetWorks;
import com.bw.movie.core.interfase.DataCall;
import com.bw.movie.core.interfase.IRequests;
import io.reactivex.Observable;

/**
 * date: 2019/1/29.
 * Created 王思敏
 * function:影院评论
 */
public class CommentPresenter extends BasePresenter {
    public CommentPresenter(DataCall consumer) {
        super(consumer);
    }

    @Override
    protected Observable observable(Object... args) {
        IRequests iRequests = NetWorks.getRequest().create(IRequests.class);
        return iRequests.getComment((int)args[0],(String) args[1],(int)args[2],(int)args[3],(int)args[4]);
    }
}
