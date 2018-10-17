package test.juyoufuli.com.myapplication.mvp.api.service

import io.reactivex.Observable
import retrofit2.http.*
import test.juyoufuli.com.myapplication.mvp.entity.*

/**
 * Author : ludf
 * Created Time : 2018-09-27  16:41
 * Description:
 */
interface MainService {
    @GET("article/list/{index}/json")
    fun getArticleList(@Path("index") index: String): Observable<ArticleResponse>
    @GET("banner/json")
    fun getBannerList(): Observable<BannerResponse>

    @FormUrlEncoded
    @POST("article/query/{index}/json")
    fun getArticleList(@Path("index") index: String,@Field("k") map:String): Observable<ArticleResponse>

    @GET("tree/json")
    fun getSystemDataList(): Observable<BaseResponse<List<SystemBean>>>

    @GET("article/list/{index}/json")
    fun getSystemDataDetailsList(@Path("index") index: String,@Path("cid")cid:String): Observable<ArticleResponse>
}
