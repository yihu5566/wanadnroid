package test.juyoufuli.com.myapplication.mvp.api.service

import retrofit2.Call
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
    fun collectArticle(@Path("index") id: String, @Field("index") index: String): Call<LoginResponse>

    @FormUrlEncoded
    @POST("lg/uncollect_originId/{id}/json")
    fun cancelCollectArticle(@Path("id") id: String, @Field("originId") index: String): Call<LoginResponse>

    @FormUrlEncoded
    @POST("lg/uncollect/{id}/json")
    fun cancelCollectArticleForMy(@Path("id") id: String, @Field("originId") index: String, @Field("id") id1: String): Call<LoginResponse>

    @GET("article/list/{index}/json")
    fun getArticleList(@Path("index") index: String): Call<ArticleResponse>

    @GET("banner/json")
    fun getBannerList(): Call<BannerResponse>

    @FormUrlEncoded
    @POST("article/query/{index}/json")
    fun getArticleList(@Path("index") index: String, @Field("k") map: String): Call<ArticleResponse>

    @GET("tree/json")
    fun getSystemDataList(): Call<SystemDataRespons>

    @GET("article/list/{index}/json")
    fun getSystemDataDetailsList(@Path("index") index: String, @Query("cid") cid: String): Call<ArticleResponse>

    @FormUrlEncoded
    @POST("user/login")
    fun login(@Field("username") index: String, @Field("password") map: String): Call<LoginResponse>

    @FormUrlEncoded
    @POST("user/register")
    fun register(@FieldMap map: Map<String, String>): Call<LoginResponse>

    @GET("user/logout/json")
    fun loginOut(): Call<LoginResponse>

    @GET("lg/collect/list/{index}/json")
    fun getArticleCollectList(@Path("index") index: String): Call<ArticleResponse>

    @GET("project/tree/json")
    fun getProjectList(): Call<ProjectResponse>

    @GET("project/list/{index}/json")
    fun getProjectDetailsList(@Path("index") page: String, @Query("cid") cid: String): Call<ProjectDetailsResponse>

    @GET("navi/json")
    fun getNavigation(): Call<NavigationResponse>

    @GET("hotkey/json")
    fun getHotWord(): Call<BaseResponse<List<HotWordData>>>

    @GET("wxarticle/chapters/json")
    fun getWeChatArticle(): Call<WeChatNumberResponse>


    /**
     * 搜索公众号历史
     */
    @GET("wxarticle/list/{id}/{page}/json")
    fun getWeChatHistoryArticle(@Path("id") id: String, @Path("page") page: String, @Query("k") k: String): Call<ArticleResponse>

    /**
     * 获取置顶的文章
     */
    @GET("article/top/json")
    fun getTopArticle(): Call<TopArticleResponse>
}
