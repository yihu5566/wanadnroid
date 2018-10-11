package test.juyoufuli.com.myapplication.mvp.model

import android.telecom.Call
import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.integration.IRepositoryManager
import com.jess.arms.mvp.BaseModel
import io.reactivex.Observable
import io.reactivex.functions.Function
import test.juyoufuli.com.myapplication.mvp.api.cache.CommonCache
import test.juyoufuli.com.myapplication.mvp.api.service.SearchService
import test.juyoufuli.com.myapplication.mvp.entity.ArticleResponse
import test.juyoufuli.com.myapplication.mvp.model.contract.SearchContract
import java.util.*
import javax.inject.Inject

/**
 * Author : dongfang
 * Created Time : 2018-10-0911:06
 * Description:
 */

@ActivityScope
class SearchViewModel @Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), SearchContract.Model {
    override fun getSearchResult(index:Int,result: String): Observable<ArticleResponse> {
//       val map = HashMap<String,String>()
//        map.put("K",result)

        return mRepositoryManager.obtainRetrofitService(SearchService::class.java)
                .getArticleList(index.toString(),result)





    }


}