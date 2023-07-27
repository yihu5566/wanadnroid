package test.juyoufuli.com.myapplication.mvp.model

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.integration.IRepositoryManager
import com.jess.arms.mvp.BaseModel
import io.reactivex.Observable
import test.juyoufuli.com.myapplication.mvp.api.service.MainService
import test.juyoufuli.com.myapplication.mvp.entity.LoginResponse
import test.juyoufuli.com.myapplication.mvp.model.contract.HomeContract
import timber.log.Timber
import javax.inject.Inject

/**
 * Author : ludf
 * Created Time : 2018-09-27  17:08
 * Description:
 */
@ActivityScope
class HomeModel @Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), HomeContract.Model {
    override fun loginOut(): Observable<LoginResponse> {
        return mRepositoryManager.obtainRetrofitService(MainService::class.java)
                .loginOut()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    internal fun onPause() {
        Timber.d("Release Resource")
    }
}
