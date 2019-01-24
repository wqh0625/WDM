package demo.com.wdmoviedemo.core.interfase;

import java.util.List;

import demo.com.wdmoviedemo.bean.CarouselData;
import demo.com.wdmoviedemo.bean.LoginData;
import demo.com.wdmoviedemo.bean.Result;
import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 作者: Wang on 2019/1/22 18:37
 * 寄语：加油！相信自己可以！！！
 */


public interface IRequest {
    // 登录
    @POST("movieApi/user/v1/login")
    @FormUrlEncoded
    Observable<Result<LoginData>> login(@Field("phone") String phone, @Field("pwd") String pwd);
    // 注册
    @POST("movieApi/user/v1/registerUser")
    @FormUrlEncoded
    Observable<Result> register(@Field("nickName") String nickName,
                                @Field("phone") String phone,
                                @Field("pwd") String pwd,
                                @Field("pwd2") String pwd2,
                                @Field("sex") int sex,
                                @Field("birthday") String birthday,
                                @Field("imei") String imei,
                                @Field("ua") String ua,
                                @Field("screenSize") String screenSize,
                                @Field("os") String os,
                                @Field("email") String email);

    //首页轮播,热门电影
    @GET("movieApi/movie/v1/findHotMovieList")
    Observable<Result<List<CarouselData>>> getcarousel(
                                                   @Query("page")int page,
                                                   @Query("count")int count);

    //首页正在热映
    @GET("movieApi/movie/v1/findReleaseMovieList")
    Observable<Result<List<CarouselData>>> getisHit(
                                                    @Query("page")int page,
                                                    @Query("count")int count);
}
