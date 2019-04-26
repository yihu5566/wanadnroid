package test.juyoufuli.com.myapplication.mvp.model

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.di.scope.FragmentScope

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
import test.juyoufuli.com.myapplication.mvp.entity.*
import test.juyoufuli.com.myapplication.mvp.model.contract.MainContract
import timber.log.Timber

/**
 * Author : ludf
 * Created Time : 2018-09-27  17:08
 * Description:
 */
@FragmentScope
class MainModel @Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), MainContract.Model {


    override fun collectArticle(id: String): Observable<LoginResponse> {
        return mRepositoryManager
                .obtainRetrofitService(MainService::class.java)
                .collectArticle(id, id)
    }

    override fun cancelCollectArticle(id: String): Observable<LoginResponse> {
        return mRepositoryManager
                .obtainRetrofitService(MainService::class.java)
                .cancelCollectArticle(id, id)
    }

    override fun getBanner(): Observable<BannerResponse> {
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(MainService::class.java)
                .getBannerList())
                .flatMap { listObservable ->
                    mRepositoryManager.obtainCacheService(CommonCache::class.java)
                            .getBannerData(listObservable)
                            .map { listReply -> listReply.data }
                }
    }

    override fun getUsers(lastIdQueried: Int, update: Boolean): Observable<ArticleResponse> {
        //使用rxcache缓存,上拉刷新则不读取缓存,加载更多读取缓存
        return mRepositoryManager
                .obtainRetrofitService(MainService::class.java)
                .getArticleList(lastIdQueried.toString() + "")

    }
    override fun getTopArticle(): Observable<TopArticleResponse> {
        return mRepositoryManager
                .obtainRetrofitService(MainService::class.java)
                .getTopArticle()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    internal fun onPause() {
        Timber.d("Release Resource")
    }
}
