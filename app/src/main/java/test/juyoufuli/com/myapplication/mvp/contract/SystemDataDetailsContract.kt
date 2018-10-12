package test.juyoufuli.com.myapplication.mvp.model.contract

import android.app.Activity
import android.support.v4.app.Fragment
import com.jess.arms.mvp.IModel
import com.jess.arms.mvp.IView
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.Observable
import test.juyoufuli.com.myapplication.mvp.entity.ArticleResponse
import test.juyoufuli.com.myapplication.mvp.entity.SystemDataResponse

/**
 * Author : ludf
 * Created Time : 2018-09-27  13:46
 * Description:
 */
class SystemDataDetailsContract {

    interface View : IView {
        val activity: Activity
        //申请权限
        val rxPermissions: RxPermissions
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,如是否使用缓存
    interface Model : IModel {
        fun getSystemData(index:String,result:String): Observable<ArticleResponse>
    }
}