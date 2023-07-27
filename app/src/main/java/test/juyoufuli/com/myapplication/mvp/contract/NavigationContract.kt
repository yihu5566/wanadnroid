package test.juyoufuli.com.myapplication.mvp.contract

import androidx.fragment.app.Fragment
import com.jess.arms.mvp.IModel
import com.jess.arms.mvp.IView
import io.reactivex.Observable
import test.juyoufuli.com.myapplication.mvp.entity.NavigationResponse

/**
 * Author : ludf
 * Created Time : 2018-09-27  13:46
 * Description:
 */
class NavigationContract {

    interface View : IView {
        val fragment: Fragment
        fun refreshAdapterList(response: NavigationResponse)
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,如是否使用缓存
    interface Model : IModel {
        fun getNavigation(): Observable<NavigationResponse>

    }
}