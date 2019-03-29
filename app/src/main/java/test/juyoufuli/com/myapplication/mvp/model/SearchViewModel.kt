package test.juyoufuli.com.myapplication.mvp.model

import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.integration.IRepositoryManager
import com.jess.arms.mvp.BaseModel
import io.reactivex.Observable
import test.juyoufuli.com.myapplication.app.BaseRequest
import test.juyoufuli.com.myapplication.mvp.api.cache.CommonCache
import test.juyoufuli.com.myapplication.mvp.api.service.MainService
import test.juyoufuli.com.myapplication.mvp.entity.ArticleResponse
import test.juyoufuli.com.myapplication.mvp.entity.BaseResponse
import test.juyoufuli.com.myapplication.mvp.entity.HotWordData
import test.juyoufuli.com.myapplication.mvp.entity.HotWordResponse
import test.juyoufuli.com.myapplication.mvp.model.contract.SearchContract
import javax.inject.Inject

/**
 * Author : dongfang
 * Created Time : 2018-10-0911:06
 * Description:
 */

@ActivityScope
class SearchViewModel @Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), SearchContract.Model {
    override fun getHotWordResult(): Observable<BaseResponse<List<HotWordData>>> {
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(MainService::class.java)
                .getHotWord())
                .flatMap { listObservable ->
                    mRepositoryManager
                            .obtainCacheService(CommonCache::class.java)
                            .getHotWord(listObservable)
                            .map { listReply -> listReply.data }
                }
    }

    override fun getSearchResult(index: Int, result: String): Observable<ArticleResponse> {
        return mRepositoryManager.obtainRetrofitService(MainService::class.java)
                .getArticleList(index.toString(), result)


    }


}