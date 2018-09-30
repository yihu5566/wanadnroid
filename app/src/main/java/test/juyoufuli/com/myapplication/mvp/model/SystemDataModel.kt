package test.juyoufuli.com.myapplication.mvp.model

import com.jess.arms.di.scope.FragmentScope
import com.jess.arms.integration.IRepositoryManager
import com.jess.arms.mvp.BaseModel
import dagger.Module
import io.reactivex.Observable
import test.juyoufuli.com.myapplication.mvp.api.cache.CommonCache
import test.juyoufuli.com.myapplication.mvp.api.service.SystemDataService
import test.juyoufuli.com.myapplication.mvp.entity.SystemDataResponse
import test.juyoufuli.com.myapplication.mvp.model.contract.SystemDataContract
import javax.inject.Inject

/**
 * Author : ludf
 * Created Time : 2018-09-28  15:30
 * Description:
 */
@FragmentScope
class SystemDataModel @Inject constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), SystemDataContract.Model {

    override fun getSystemData(): Observable<SystemDataResponse> {
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(SystemDataService::class.java)
                .getSystemDataList())
                .flatMap { listObservable ->
                    mRepositoryManager
                            .obtainCacheService(CommonCache::class.java)
                            .getSystemData(listObservable)
                            .map { listReply -> listReply.data }
                }
    }
}