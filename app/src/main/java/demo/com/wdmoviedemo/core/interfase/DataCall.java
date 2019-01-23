package demo.com.wdmoviedemo.core.interfase;

import demo.com.wdmoviedemo.core.exception.ApiException;

/**
 * 作者: Wang on 2019/1/22 16:09
 * 寄语：加油！相信自己可以！！！
 */

public interface DataCall<T> {
    void success(T data);
    void fail(ApiException a);
}
