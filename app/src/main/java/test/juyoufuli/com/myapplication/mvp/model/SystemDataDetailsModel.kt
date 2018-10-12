package test.juyoufuli.com.myapplication.mvp.model

import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.integration.IRepositoryManager
import com.jess.arms.mvp.BaseModel
import io.reactivex.Observable
import test.juyoufuli.com.myapplication.mvp.api.service.MainService
import test.juyoufuli.com.myapplication.mvp.entity.ArticleResponse
import test.juyoufuli.com.myapplication.mvp.model.contract.SystemDataDetailsContract
import javax.inject.Inject

/**
 * Author : dongfang
 * Created Time : 2018-10-1211:32
 * Description:
 */
@ActivityScope
class SystemDataDetailsModel @Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), SystemDataDetailsContract.Model {
    //huo
    override fun getSystemData(index:String,result:String): Observable<ArticleResponse> {
       return mRepositoryManager.obtainRetrofitService(MainService::class.java)
               .getSystemDataDetailsList(index,result)
    }
}