package test.juyoufuli.com.myapplication.mvp.model.contract

import android.support.v4.app.Fragment
import com.jess.arms.mvp.IModel
import com.jess.arms.mvp.IView
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.Observable
import test.juyoufuli.com.myapplication.mvp.entity.ArticleResponse
import test.juyoufuli.com.myapplication.mvp.entity.BannerResponse
import test.juyoufuli.com.myapplication.mvp.entity.LoginResponse
import test.juyoufuli.com.myapplication.mvp.entity.TopArticleResponse

/**
 * Author : ludf
 * Created Time : 2018-09-27  13:46
 * Description:
 */
class MainContract {

    interface View : IView {
        val fragment: Fragment
        //申请权限
        val rxPermissions: RxPermissions

        fun startLoadMore()
        fun endLoadMore()
        fun updateBanner(systemDataResponse: BannerResponse)
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,如是否使用缓存
    interface Model : IModel {
        fun getUsers(lastIdQueried: Int, update: Boolean): Observable<ArticleResponse>
        fun getBanner(): Observable<BannerResponse>
        fun collectArticle(id: String): Observable<LoginResponse>
        fun cancelCollectArticle(id: String): Observable<LoginResponse>

        fun getTopArticle(): Observable<TopArticleResponse>


    }
}