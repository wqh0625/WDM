package demo.com.wdmoviedemo.core.interfase;

import java.util.List;

import demo.com.wdmoviedemo.bean.CarouselData;
import demo.com.wdmoviedemo.bean.CinemaDetailListData;
import demo.com.wdmoviedemo.bean.FindCinemaPageListData;
import demo.com.wdmoviedemo.bean.FindMoviePageListData;
import demo.com.wdmoviedemo.bean.LoginData;
import demo.com.wdmoviedemo.bean.MyMessage;
import demo.com.wdmoviedemo.bean.MyMessageData;
import demo.com.wdmoviedemo.bean.NearbyData;
import demo.com.wdmoviedemo.bean.Result;
import demo.com.wdmoviedemo.bean.Result2;
import demo.com.wdmoviedemo.bean.TicketDetailsData;
import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
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
            @Header("userId") int userId,
            @Header("sessionId") String sessionID,
            @Query("page") int page,
            @Query("count") int count);

    //首页正在热映
    @GET("movieApi/movie/v1/findReleaseMovieList")
    Observable<Result<List<CarouselData>>> getisHit(@Header("userId") int userId,
                                                    @Header("sessionId") String sessionID,
                                                    @Query("page") int page,
                                                    @Query("count") int count);

    //即将上映
    @GET("movieApi/movie/v1/findComingSoonMovieList")
    Observable<Result<List<CarouselData>>> getonNext(@Header("userId") int userId,
                                                     @Header("sessionId") String sessionID, @Query("page") int page,
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

    // http://172.17.8.100/  推荐影院
    @GET("movieApi/cinema/v1/findRecommendCinemas")
    Observable<Result<List<NearbyData>>> findRecommendCinemas(@Header("userId") int userId,
                                                              @Header("sessionId") String Id,
                                                              @Query("page") int page,
                                                              @Query("count") int count);

    // http://172.17.8.100/  附近影院
    @GET("movieApi/cinema/v1/findNearbyCinemas")
    Observable<Result<List<NearbyData>>> findNearbyCinemas(@Header("userId") int userId,
                                                           @Header("sessionId") String Id,
                                                           @Query("longitude") String longitude,
                                                           @Query("latitude") String latitude,
                                                           @Query("page") int page,
                                                           @Query("count") int count);

    //http://172.17.8.100/  意见反馈
    @POST("movieApi/tool/v1/verify/recordFeedBack")
    @FormUrlEncoded
    Observable<Result> recordFeedBack(@Header("userId") int userId,
                                      @Header("sessionId") String sessionID,
                                      @Field("content") String content);

    //查询用户关注的影院信息 http://172.17.8.100/movieApi/cinema/v1/verify/findCinemaPageList
    @GET("movieApi/cinema/v1/verify/findCinemaPageList")
    Observable<Result<List<FindCinemaPageListData>>> findCinemaPageList(@Header("userId") int userId,
                                                                        @Header("sessionId") String Id,
                                                                        @Query("page") int page,
                                                                        @Query("count") int count);

    //http://172.17.8.100/movieApi/movie/v1/verify/findMoviePageList
    @GET("movieApi/movie/v1/verify/findMoviePageList")
    Observable<Result<List<FindMoviePageListData>>> findMoviePageList(@Header("userId") int userId,
                                                                      @Header("sessionId") String Id,
                                                                      @Query("page") int page,
                                                                      @Query("count") int count);

    // 取消关注 movieApi/movie/v1/verify/cancelFollowMovie
    @GET("movieApi/movie/v1/verify/cancelFollowMovie")
    Observable<Result> cancelFollowMovie(@Header("userId") int userId,
                                         @Header("sessionId") String sessionId,
                                         @Query("movieId") int movieId);

    // 3.查询会员首页信息movieApi/user/v1/verify/findUserHomeInfo
    @GET("movieApi/user/v1/verify/findUserHomeInfo")
    Observable<Result<Result2>> findUserHomeInfo(@Header("userId") int userId,
                                                 @Header("sessionId") String sessionId);

    // http://172.17.8.100/movieApi/movie/v1/findMovieListByCinemaId
    //14.根据影院ID查询该影院当前排期的电影列表
    @GET("movieApi/movie/v1/findMovieListByCinemaId")
    Observable<Result<List<CinemaDetailListData>>> findMovieListByCinemaId(@Query("cinemaId") int id);

    //    根据电影ID和影院ID查询电影排期列表
    //movieApi/movie/v1/findCinemasListByMovieId
    @GET("movieApi/movie/v1/findCinemasListByMovieId")
    Observable<Result<List<TicketDetailsData>>> findMovieScheduleList(@Query("movieId") int movieId);

    // 购票下单 /
    @POST("movieApi/movie/v1/verify/buyMovieTicket")
    @FormUrlEncoded
    Observable<Result> buyMovieTicket(
            @Header("userId") int userId,
            @Header("sessionId") String sessionId,
            @Field("scheduleId") int scheduleId,
            @Field("amount") int amount,
            @Field("sign") String sign);

    // 支付
    @POST("movieApi/movie/v1/verify/pay")
    @FormUrlEncoded
    Observable<Result> pay(@Header("userId") int userId,
                           @Header("sessionId") String sessionId,
                           @Field("payType") int scheduleId,
                           @Field("orderId") String orderId);

    // 上传头像
    @POST("movieApi/user/v1/verify/uploadHeadPic")
    Observable<Result> headPic(@Header("userId") int userId,
                               @Header("sessionId") String sessionId,
                               @Body MultipartBody image);

    // 签到http://172.17.8.100/movieApi/user/v1/verify/userSignIn
    @GET("movieApi/user/v1/verify/userSignIn")
    Observable<Result> userSignIn(@Header("userId") int userId,
                                  @Header("sessionId") String sessionId);

    //6.关注影院
    //接口地址：http://172.17.8.100/movieApi/cinema/v1/verify/followCinema
    @GET("movieApi/cinema/v1/verify/followCinema")
    Observable<Result> followCinema(@Header("userId") int userId,
                                    @Header("sessionId") String sessionId,
                                    @Query("cinemaId") int id);

    //7.取消关注影院
    //接口地址：http://172.17.8.100/movieApi/cinema/v1/verify/cancelFollowCinema
    @GET("movieApi/cinema/v1/verify/cancelFollowCinema")
    Observable<Result> cancelFollowCinema(@Header("userId") int userId,
                                          @Header("sessionId") String sessionId,
                                          @Query("cinemaId") int id);

    // 修改个人信息
    //
    @FormUrlEncoded
    @POST("movieApi/user/v1/verify/modifyUserInfo")
    Observable<Result<MyMessage>> modifyUserI (@Header("userId") int userId,
                                               @Header("sessionId") String sessionId,
                                               @Field("nickName") String nickName,
                                               @Field("sex") int sex,
                                               @Field("email") String email);

}
