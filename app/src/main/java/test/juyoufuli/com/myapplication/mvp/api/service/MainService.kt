package test.juyoufuli.com.myapplication.mvp.api.service

import com.we.jetpackmvvm.network.BaseResponse
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
    @POST("user/login")
    fun login(@Field("username") index: String, @Field("password") map: String): Call<LoginResponse>

    @FormUrlEncoded
    @POST("user/register")
    fun register(@FieldMap map: Map<String, String>): Call<LoginResponse>

    @GET("user/logout/json")
    fun loginOut(): Call<LoginResponse>

    @FormUrlEncoded
    @POST("lg/collect/{index}/json")
    suspend fun collectArticle(
        @Path("index") id: String,
    ): WanApiResponse<Any>

    @FormUrlEncoded
    @POST("lg/uncollect_originId/{id}/json")
    suspend fun cancelCollectArticle(
        @Path("id") id: String,
    ): WanApiResponse<Any>

    @FormUrlEncoded
    @POST("lg/uncollect/{id}/json")
    fun cancelCollectArticleForMy(
        @Path("id") id: String,
        @Field("originId") index: String,
        @Field("id") id1: String
    ): Call<LoginResponse>

    @GET("article/list/{index}/json")
    suspend fun getArticleList(@Path("index") index: Int): WanApiResponse<WanPageResponse<ArticleBean>>

    @GET("banner/json")
    suspend fun getBannerList(): WanApiResponse<List<BannerInfo>>

    @FormUrlEncoded
    @POST("article/query/{index}/json")
    fun getArticleList(
        @Path("index") index: String,
        @Field("k") map: String
    ): WanApiResponse<WanPageResponse<ArticleBean>>

    @GET("tree/json")
    suspend fun getSystemDataList(): WanApiResponse<List<SystemBean>>

    @GET("wxarticle/list/{cid}/{page}/json")
    suspend fun getSystemDataDetailsList(
        @Path("cid") cid: Int,
        @Path("page") page: Int
    ): WanApiResponse<WanPageResponse<ArticleBean>>

    @GET("lg/collect/list/{index}/json")
    fun getArticleCollectList(@Path("index") index: String): WanApiResponse<WanPageResponse<ArticleBean>>

    @GET("project/tree/json")
    suspend fun getProjectList(): WanApiResponse<List<ProjectData>>

    @GET("project/list/{index}/json")
    suspend fun getProjectDetailsList(
        @Path("index") page: Int,
        @Query("cid") cid: Int
    ): WanApiResponse<WanPageResponse<ProjectDatas>>

    @GET("navi/json")
    suspend fun getNavigation(): WanApiResponse<List<NavigationBean>>

    @GET("hotkey/json")
    fun getHotWord(): Call<BaseResponse<List<HotWordData>>>

    @GET("wxarticle/chapters/json")
    suspend fun getWeChatArticle(): WanApiResponse<List<WeChatData>>


    /**
     * 搜索公众号历史
     */
    @GET("wxarticle/list/{id}/{page}/json")
    suspend fun getWeChatHistoryArticle(
        @Path("id") id: String,
        @Path("page") page: String,
        @Query("k") k: String? = null
    ): WanApiResponse<WanPageResponse<ArticleBean>>

    /**
     * 获取置顶的文章
     */
    @GET("article/top/json")
    suspend fun getTopArticle(): WanApiResponse<List<ArticleBean>>
}
