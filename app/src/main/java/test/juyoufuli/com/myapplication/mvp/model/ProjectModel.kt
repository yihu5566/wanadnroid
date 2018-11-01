package test.juyoufuli.com.myapplication.mvp.model

import com.jess.arms.di.scope.FragmentScope
import com.jess.arms.integration.IRepositoryManager
import com.jess.arms.mvp.BaseModel
import io.reactivex.Observable
import test.juyoufuli.com.myapplication.mvp.api.service.MainService
import test.juyoufuli.com.myapplication.mvp.contract.ProjectContract
import test.juyoufuli.com.myapplication.mvp.entity.LoginResponse
import test.juyoufuli.com.myapplication.mvp.entity.ProjectDetailsResponse
import test.juyoufuli.com.myapplication.mvp.entity.ProjectResponse
import javax.inject.Inject

/**
 * Author : dongfang
 * Created Time : 2018-10-31  09:35
 * Description:
 */
@FragmentScope
class ProjectModel @Inject constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), ProjectContract.Model {
    override fun getProjectDetailsData(page: String, cid: String): Observable<ProjectDetailsResponse> {
        return mRepositoryManager.obtainRetrofitService(MainService::class.java)
                .getProjectDetailsList(page, cid)
    }

    override fun getProjectData(): Observable<ProjectResponse> {
        return mRepositoryManager.obtainRetrofitService(MainService::class.java)
                .getProjectList()

    }

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
}