package test.juyoufuli.com.myapplication.mvp.model

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent

import com.jess.arms.integration.IRepositoryManager
import com.jess.arms.mvp.BaseModel

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
import test.juyoufuli.com.myapplication.mvp.entity.BaseResponse
import test.juyoufuli.com.myapplication.mvp.model.contract.MainContract
import timber.log.Timber

/**
 * Author : ludf
 * Created Time : 2018-09-27  17:08
 * Description:
 */
class MainModel @Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), MainContract.Model {

    override fun getUsers(lastIdQueried: Int, update: Boolean): Observable<ArticleResponse> {
        //使用rxcache缓存,上拉刷新则不读取缓存,加载更多读取缓存
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(MainService::class.java)
                .getArticleList(lastIdQueried.toString() + ""))
                .flatMap { listObservable ->
                    mRepositoryManager.obtainCacheService(CommonCache::class.java)
                            .getUsers(listObservable, DynamicKey(lastIdQueried), EvictDynamicKey(update))
                            .map { listReply -> listReply.data }
                }

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    internal fun onPause() {
        Timber.d("Release Resource")
    }
}
