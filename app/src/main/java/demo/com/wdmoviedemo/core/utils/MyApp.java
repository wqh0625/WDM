package demo.com.wdmoviedemo.core.utils;

import android.app.Application;
import android.content.Context;
import android.os.Environment;

import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;

import java.io.File;

/**
 * 作者: Wang on 2019/1/23 16:23
 * 寄语：加油！相信自己可以！！！
 */


public class MyApp extends Application {

    /**
     * context 全局唯一的上下文
     */
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        Fresco.initialize(this,ImagePipelineConfig.newBuilder(this)
                .setMainDiskCacheConfig(DiskCacheConfig.newBuilder(this)
                        .setBaseDirectoryPath(new File(Environment.getExternalStorageDirectory()+File.separator+"wdmoview"))
                        .build())
                .build());
    }

    /**
     * @return 全局唯一的上下文
     * @author: 康海涛 QQ2541849981
     * @describe: 获取全局Application的上下文
     */
    public static Context getContext() {
        return context;
    }
}
