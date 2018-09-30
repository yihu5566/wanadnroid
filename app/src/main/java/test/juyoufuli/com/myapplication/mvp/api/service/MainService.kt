package test.juyoufuli.com.myapplication.mvp.api.service

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import test.juyoufuli.com.myapplication.mvp.entity.ArticleResponse
import test.juyoufuli.com.myapplication.mvp.entity.BannerResponse

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
}
