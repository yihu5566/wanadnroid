package test.juyoufuli.com.myapplication.mvp.model.contract

import android.app.Activity
import com.jess.arms.mvp.IModel
import com.jess.arms.mvp.IView
import io.reactivex.Observable
import test.juyoufuli.com.myapplication.mvp.entity.ArticleResponse
import test.juyoufuli.com.myapplication.mvp.entity.LoginResponse

/**
 * Author : ludf
 * Created Time : 2018-09-27  13:46
 * Description:
 */
class CollectArticleContract {

    interface View : IView {
        fun getActivity(): Activity
        fun getArticleCollectSucceed(response: ArticleResponse)
        fun cancelArticleCollectSucceed(position:Int)

    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,如是否使用缓存
    interface Model : IModel {
        fun getArticleCollect(page: String): Observable<ArticleResponse>
        fun cancelCollectArticle(id: String,originId:String): Observable<LoginResponse>

    }
}