package demo.com.wdmoviedemo.core.interfase;

import java.util.List;

import demo.com.wdmoviedemo.bean.CarouselData;
import demo.com.wdmoviedemo.bean.LoginData;
import demo.com.wdmoviedemo.bean.Result;
import demo.com.wdmoviedemo.bean.SearchData;
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


public interface IRequests {
   //即将上映
    @GET("movieApi/movie/v1/findComingSoonMovieList")
    Observable<Result<List<CarouselData>>> getonNext(@Query("page") int page,
                                                    @Query("count") int count);

    //即将上映
    @GET("movieApi/movie/v1/findMoviesById")
    Observable<Result<SearchData>> getmovieId(@Query("movieId") int movieId);

}
