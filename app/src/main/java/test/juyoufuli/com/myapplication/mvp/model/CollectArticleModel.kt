package test.juyoufuli.com.myapplication.mvp.model

import com.jess.arms.di.scope.ActivityScope

import com.jess.arms.integration.IRepositoryManager
import com.jess.arms.mvp.BaseModel

import javax.inject.Inject

import io.reactivex.Observable
import test.juyoufuli.com.myapplication.mvp.api.service.MainService
import test.juyoufuli.com.myapplication.mvp.entity.ArticleResponse
import test.juyoufuli.com.myapplication.mvp.entity.LoginResponse
import test.juyoufuli.com.myapplication.mvp.model.contract.CollectArticleContract

/**
 * Author : ludf
 * Created Time : 2018-09-27  17:08
 * Description:
 */
@ActivityScope
class CollectArticleModel @Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), CollectArticleContract.Model {
    override fun cancelCollectArticle(id: String, originId: String): Observable<LoginResponse> {
        return mRepositoryManager
                .obtainRetrofitService(MainService::class.java)
                .cancelCollectArticleForMy(id, originId,id)
    }

    override fun getArticleCollect(page:String): Observable<ArticleResponse> {
        return mRepositoryManager.obtainRetrofitService(MainService::class.java)
                .getArticleCollectList(page)
    }


}
