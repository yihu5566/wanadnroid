package test.juyoufuli.com.myapplication.mvp.api.service

import io.reactivex.Observable
import retrofit2.http.*
import test.juyoufuli.com.myapplication.mvp.entity.ArticleResponse

/**
 * Author : ludf
 * Created Time : 2018-09-27  16:41
 * Description:
 */
interface SearchService {
    @FormUrlEncoded
    @POST("article/query/{index}/json")
    fun getArticleList(@Path("index") index: String,@Field("k") map:String): Observable<ArticleResponse>

}
