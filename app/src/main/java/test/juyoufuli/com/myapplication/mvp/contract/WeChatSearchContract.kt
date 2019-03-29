package test.juyoufuli.com.myapplication.mvp.model.contract

import android.app.Activity
import com.jess.arms.mvp.IModel
import com.jess.arms.mvp.IView
import io.reactivex.Observable
import test.juyoufuli.com.myapplication.mvp.entity.ArticleResponse
import test.juyoufuli.com.myapplication.mvp.entity.HotWordResponse

/**
 * Author : ludf
 * Created Time : 2018-09-27  13:46
 * Description:
 */
class WeChatSearchContract {

    interface View : IView {
        fun getActivity(): Activity
        fun refreshList(list: ArticleResponse)
        fun refreshHotWord(response: HotWordResponse)
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,如是否使用缓存
    interface Model : IModel {

        fun getSearchResult(id: String, index: Int, result: String): Observable<ArticleResponse>

    }
}