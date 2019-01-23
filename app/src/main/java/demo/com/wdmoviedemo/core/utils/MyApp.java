package demo.com.wdmoviedemo.core.utils;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * 作者: Wang on 2019/1/23 16:23
 * 寄语：加油！相信自己可以！！！
 */


public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
    }

}
