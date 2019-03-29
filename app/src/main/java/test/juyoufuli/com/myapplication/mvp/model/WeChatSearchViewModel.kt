package test.juyoufuli.com.myapplication.mvp.model

import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.integration.IRepositoryManager
import com.jess.arms.mvp.BaseModel
import io.reactivex.Observable
import test.juyoufuli.com.myapplication.mvp.api.service.MainService
import test.juyoufuli.com.myapplication.mvp.entity.ArticleResponse
import test.juyoufuli.com.myapplication.mvp.model.contract.WeChatSearchContract
import javax.inject.Inject

/**
 * Author : dongfang
 * Created Time : 2018-10-0911:06
 * Description:
 */

@ActivityScope
class WeChatSearchViewModel @Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), WeChatSearchContract.Model {


    override fun getSearchResult(id: String, index: Int, result: String): Observable<ArticleResponse> {
//       val map = HashMap<String,String>()
//        map.put("K",result)

        return mRepositoryManager.obtainRetrofitService(MainService::class.java)
                .getWeChatHistoryArticle(id, index.toString(), result)


    }


}