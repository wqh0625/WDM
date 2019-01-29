package demo.com.wdmoviedemo.presenter;

import java.io.File;

import demo.com.wdmoviedemo.bean.Result;
import demo.com.wdmoviedemo.core.http.NetWorks;
import demo.com.wdmoviedemo.core.interfase.DataCall;
import demo.com.wdmoviedemo.core.interfase.IRequest;
import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * 作者: Wang on 2019/1/28 19:19
 * 寄语：加油！相信自己可以！！！
 */


public class TopPhotoPresenter extends BasePresenter {
    public TopPhotoPresenter(DataCall consumer) {
        super(consumer);
    }

    @Override
    protected Observable<Result> observable(Object... args) {

        IRequest iRequest = NetWorks.getRequest().create(IRequest.class);
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        builder.addFormDataPart("headPath", (String)args[2]);
        File file = new File((String) args[2]);
        builder.addFormDataPart("image", file.getName(),
                RequestBody.create(MediaType.parse("multipart/octet-stream"),
                        file));


        return iRequest.headPic((int)args[0],(String) args[1],builder.build());
    }
}
