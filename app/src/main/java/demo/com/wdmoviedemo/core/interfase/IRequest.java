package demo.com.wdmoviedemo.core.interfase;

import java.util.List;

import demo.com.wdmoviedemo.bean.CarouselData;
import demo.com.wdmoviedemo.bean.LoginData;
import demo.com.wdmoviedemo.bean.MyMessageData;
import demo.com.wdmoviedemo.bean.NearbyData;
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
    //即将上映
    @GET("movieApi/movie/v1/findComingSoonMovieList")
    Observable<Result<List<CarouselData>>> getonNext(@Query("page") int page,
                                                     @Query("count") int count);

    // 查询我的页面movieApi/user/v1/verify/getUserInfoByUserId
    @GET("movieApi/user/v1/verify/getUserInfoByUserId")
    Observable<Result<MyMessageData>> getUserInfoByUserId(@Header("userId") int userId,
                                                          @Header("sessionId") String sessionID);

    // 修改密码
    @POST("movieApi/user/v1/verify/modifyUserPwd")
    @FormUrlEncoded
    Observable<Result> modifyUserPwd(@Header("userId") int userId,
                                     @Header("sessionId") String sessionID,
                                     @Field("oldPwd") String oldPwd,
                                     @Field("newPwd") String newPwd,
                                     @Field("newPwd2") String newPwd2);

    // http://172.17.8.100/
    @GET("movieApi/cinema/v1/findRecommendCinemas")
    Observable<Result<List<NearbyData>>> findRecommendCinemas(@Header("userId") int userId,
                                                              @Header("sessionId") String Id,
                                                              @Query("page") int page,
                                                              @Query("count") int count);
}
