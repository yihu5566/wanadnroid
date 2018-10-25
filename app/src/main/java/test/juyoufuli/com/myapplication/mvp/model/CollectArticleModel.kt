package test.juyoufuli.com.myapplication.mvp.model

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import com.jess.arms.di.scope.ActivityScope

import com.jess.arms.integration.IRepositoryManager
import com.jess.arms.mvp.BaseModel
import dagger.Module

import javax.inject.Inject

import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.annotations.NonNull
import io.reactivex.functions.Function
import io.rx_cache2.DynamicKey
import io.rx_cache2.EvictDynamicKey
import test.juyoufuli.com.myapplication.mvp.api.cache.CommonCache
import test.juyoufuli.com.myapplication.mvp.api.service.MainService
import test.juyoufuli.com.myapplication.mvp.entity.ArticleResponse
import test.juyoufuli.com.myapplication.mvp.entity.BannerResponse
import test.juyoufuli.com.myapplication.mvp.entity.BaseResponse
import test.juyoufuli.com.myapplication.mvp.entity.LoginResponse
import test.juyoufuli.com.myapplication.mvp.model.contract.CollectArticleContract
import test.juyoufuli.com.myapplication.mvp.model.contract.HomeContract
import test.juyoufuli.com.myapplication.mvp.model.contract.MainContract
import timber.log.Timber

/**
 * Author : ludf
 * Created Time : 2018-09-27  17:08
 * Description:
 */
@ActivityScope
class CollectArticleModel @Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), CollectArticleContract.Model {
    override fun cancelCollectArticle(id: String, originId: String): Observable<LoginResponse> {
        return mRepositoryManager
                .obtainRetrofitService(MainService::class.java)
                .cancelCollectArticleForMy(id, originId,id)
    }

    override fun getArticleCollect(page:String): Observable<ArticleResponse> {
        return mRepositoryManager.obtainRetrofitService(MainService::class.java)
                .getArticleCollectList(page)
    }


}
