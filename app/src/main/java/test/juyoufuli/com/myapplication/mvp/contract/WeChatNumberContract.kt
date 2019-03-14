package test.juyoufuli.com.myapplication.mvp.contract

import android.support.v4.app.Fragment
import com.jess.arms.mvp.IModel
import com.jess.arms.mvp.IView
import io.reactivex.Observable
import test.juyoufuli.com.myapplication.mvp.entity.SystemDataRespons
import test.juyoufuli.com.myapplication.mvp.entity.WeChatNumberResponse

/**
 * @Author : dongfang
 * @Created Time : 2019-03-14  10:31
 * @Description:
 */
class WeChatNumberContract {

    interface View : IView {
        val fragment: Fragment
        fun refreshData(response: WeChatNumberResponse)
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,如是否使用缓存
    interface Model : IModel {
        fun getWeChatData(): Observable<WeChatNumberResponse>

    }
}