package test.juyoufuli.com.myapplication.mvp.model

import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.integration.IRepositoryManager
import com.jess.arms.mvp.BaseModel
import io.reactivex.Observable
import test.juyoufuli.com.myapplication.mvp.api.service.MainService
import test.juyoufuli.com.myapplication.mvp.entity.LoginResponse
import test.juyoufuli.com.myapplication.mvp.model.contract.LoginContract
import java.util.*
import javax.inject.Inject

/**
 * Author : ludf
 * Created Time : 2018-09-27  17:08
 * Description:
 */
@ActivityScope
class LoginModel @Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), LoginContract.Model {
    override fun login(name: String, password: String): Observable<LoginResponse> {
        return mRepositoryManager.obtainRetrofitService(MainService::class.java)
                .login(name, password)
    }

    override fun register(name: String, password: String, repassword: String): Observable<LoginResponse> {

        var map = HashMap<String, String>()
        map["username"] = name
        map["password"] = password
        map["repassword"] = repassword

        return mRepositoryManager.obtainRetrofitService(MainService::class.java)
                .register(map)
    }
}
