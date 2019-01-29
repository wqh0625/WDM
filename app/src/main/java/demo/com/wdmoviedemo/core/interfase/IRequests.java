package demo.com.wdmoviedemo.core.interfase;

import java.util.List;

import demo.com.wdmoviedemo.bean.CarouselData;
import demo.com.wdmoviedemo.bean.FilmDetailsData;
import demo.com.wdmoviedemo.bean.ListofCinemaData;
import demo.com.wdmoviedemo.bean.LoginData;
import demo.com.wdmoviedemo.bean.MessageData;
import demo.com.wdmoviedemo.bean.ObligationData;
import demo.com.wdmoviedemo.bean.Result;
import demo.com.wdmoviedemo.bean.ReviewData;
import demo.com.wdmoviedemo.bean.SearchData;
import demo.com.wdmoviedemo.bean.TicketDetailsData;
import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 作者: Wang on 2019/1/22 18:37
 * 寄语：加油！相信自己可以！！！
 */


public interface IRequests {
    //即将上映
    @GET("movieApi/movie/v1/findComingSoonMovieList")
    Observable<Result<List<CarouselData>>> getonNext(@Query("page") int page,
                                                     @Query("count") int count);

    //电影详情
    @GET("movieApi/movie/v1/findMoviesById")
    Observable<Result<SearchData>> getmovieId(@Query("movieId") int movieId);

    //关注电影
    @GET("movieApi/movie/v1/verify/followMovie")
    Observable<Result> getConcern(@Header("userId") int userId,
                                  @Header("sessionId") String sessionId,
                                  @Query("movieId") int movieId);

    //电影详情
    @GET("movieApi/movie/v1/findMoviesDetail")
    Observable<Result<FilmDetailsData>> getFilmDetails(@Query("movieId") int movieId);

    //查询电影评论
    @GET("movieApi/movie/v1/findAllMovieComment")
    Observable<Result<List<ReviewData>>> getReciew(@Query("movieId") int movieId,
                                                   @Query("page") int page,
                                                   @Query("count") int count);

    //电影详情影院
    @GET("movieApi/movie/v1/findCinemasListByMovieId")
    Observable<Result<List<ListofCinemaData>>> getListofCinema(@Query("movieId") int movieId);


    //电影id和影院id查询电影信息
    @GET("movieApi/movie/v1/findMovieScheduleList")
    Observable<Result<List<TicketDetailsData>>> getTicketDetails(@Query("movieId") int movieId,
                                                                 @Query("cinemasId") int cinemasId);

    // 微信登录
    @POST("movieApi/user/v1/weChatBindingLogin")
    @FormUrlEncoded
    Observable<Result<LoginData>> getWxlogin(@Field("code") String code);


    //查询用户购票记录
    @GET("movieApi/user/v1/verify/findUserBuyTicketRecordList")
    Observable<Result<List<ObligationData>>> getObligation(@Header("userId") int userId,
                                                           @Header("sessionId") String sessionId,
                                                           @Query("page") int page,
                                                           @Query("count") int count,
                                                           @Query("status") int status);


    //系统消息
    @GET("movieApi/tool/v1/verify/findAllSysMsgList")
    Observable<Result<List<MessageData>>> getMessage(@Header("userId") int userId,
                                                     @Header("sessionId") String sessionId,
                                                     @Query("page") int page,
                                                     @Query("count") int count);
}
