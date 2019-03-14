package test.juyoufuli.com.myapplication.mvp.model

import com.jess.arms.di.scope.FragmentScope
import com.jess.arms.integration.IRepositoryManager
import com.jess.arms.mvp.BaseModel
import io.reactivex.Observable
import test.juyoufuli.com.myapplication.mvp.api.cache.CommonCache
import test.juyoufuli.com.myapplication.mvp.api.service.MainService
import test.juyoufuli.com.myapplication.mvp.contract.WeChatNumberContract
import test.juyoufuli.com.myapplication.mvp.entity.WeChatNumberResponse
import javax.inject.Inject

/**
 * Author : ludf
 * Created Time : 2018-09-28  15:30
 * Description:
 */
@FragmentScope
class WeChatNumberModel @Inject constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), WeChatNumberContract.Model {
    override fun getWeChatData(): Observable<WeChatNumberResponse> {
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(MainService::class.java)
                .getWeChatArticle())
                .flatMap { listObservable ->
                    mRepositoryManager
                            .obtainCacheService(CommonCache::class.java)
                            .getWeChatArticle(listObservable)
                            .map { listReply -> listReply.data }
                }

    }
}