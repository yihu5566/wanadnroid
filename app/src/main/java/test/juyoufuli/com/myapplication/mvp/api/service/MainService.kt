package test.juyoufuli.com.myapplication.mvp.api.service

import io.reactivex.Observable
import okhttp3.RequestBody
import retrofit2.http.*
import test.juyoufuli.com.myapplication.mvp.entity.*

/**
 * Author : ludf
 * Created Time : 2018-09-27  16:41
 * Description:
 */
interface MainService {


    @FormUrlEncoded
    @POST("lg/collect/{index}/json")
    fun collectArticle(@Path("index") id: String, @Field("index") index: String): Observable<LoginResponse>

    @FormUrlEncoded
    @POST("lg/uncollect_originId/{id}/json")
    fun cancelCollectArticle(@Path("id") id: String, @Field("originId") index: String): Observable<LoginResponse>

    @FormUrlEncoded
    @POST("lg/uncollect/{id}/json")
    fun cancelCollectArticleForMy(@Path("id") id: String, @Field("originId") index: String, @Field("id") id1: String): Observable<LoginResponse>

    @GET("article/list/{index}/json")
    fun getArticleList(@Path("index") index: String): Observable<ArticleResponse>

    @GET("banner/json")
    fun getBannerList(): Observable<BannerResponse>

    @FormUrlEncoded
    @POST("article/query/{index}/json")
    fun getArticleList(@Path("index") index: String, @Field("k") map: String): Observable<ArticleResponse>

    @GET("tree/json")
    fun getSystemDataList(): Observable<SystemDataRespons>

    @GET("article/list/{index}/json")
    fun getSystemDataDetailsList(@Path("index") index: String, @Query("cid") cid: String): Observable<ArticleResponse>

    @FormUrlEncoded
    @POST("user/login")
    fun login(@Field("username") index: String, @Field("password") map: String): Observable<LoginResponse>

    @FormUrlEncoded
    @POST("user/register")
    fun register(@FieldMap map: Map<String, String>): Observable<LoginResponse>

    @GET("user/logout/json")
    fun loginOut(): Observable<LoginResponse>

    @GET("lg/collect/list/{index}/json")
    fun getArticleCollectList(@Path("index") index: String): Observable<ArticleResponse>

    @GET("project/tree/json")
    fun getProjectList(): Observable<ProjectResponse>

    @GET("project/list/{index}/json")
    fun getProjectDetailsList(@Path("index") page: String, @Query("cid") cid: String): Observable<ProjectDetailsResponse>

    @GET("navi/json")
    fun getNavigation(): Observable<NavigationResponse>
}
