package test.juyoufuli.com.myapplication.mvp.model

import com.jess.arms.di.scope.FragmentScope
import com.jess.arms.integration.IRepositoryManager
import com.jess.arms.mvp.BaseModel
import io.reactivex.Observable
import test.juyoufuli.com.myapplication.mvp.api.service.MainService
import test.juyoufuli.com.myapplication.mvp.entity.NavigationResponse
import test.juyoufuli.com.myapplication.mvp.contract.NavigationContract
import javax.inject.Inject

/**
 * Author : ludf
 * Created Time : 2018-09-27  17:08
 * Description:
 */
@FragmentScope
class NavigationModel @Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), NavigationContract.Model {
    override fun getNavigation(): Observable<NavigationResponse> {
        return mRepositoryManager.obtainRetrofitService(MainService::class.java)
                .getNavigation()
    }

}
