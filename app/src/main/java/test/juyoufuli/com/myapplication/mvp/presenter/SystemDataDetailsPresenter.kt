package test.juyoufuli.com.myapplication.mvp.presenter

import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.mvp.BasePresenter
import com.jess.arms.utils.RxLifecycleUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber
import me.jessyan.rxerrorhandler.handler.RetryWithDelay
import test.juyoufuli.com.myapplication.mvp.entity.ArticleResponse
import test.juyoufuli.com.myapplication.mvp.model.contract.SystemDataDetailsContract
import javax.inject.Inject

/**
 * Author : ludf
 * Created Time : 2018-09-29  13:34
 * Description:
 */
@ActivityScope
class SystemDataDetailsPresenter @Inject
constructor(model: SystemDataDetailsContract.Model, rootView: SystemDataDetailsContract.View) : BasePresenter<SystemDataDetailsContract.Model, SystemDataDetailsContract.View>(model, rootView) {
    @JvmField
    @Inject
    internal var mErrorHandler: RxErrorHandler? = null

    fun requestSystemDataList(index: String, cid: String) {
        mModel.getSystemData(index, cid)
                .subscribeOn(Schedulers.io())
                .retryWhen(RetryWithDelay(3, 2))
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle<ArticleResponse>(mRootView))
                .subscribe(object : ErrorHandleSubscriber<ArticleResponse>(mErrorHandler!!) {

                    override fun onNext(systemDataResponse: ArticleResponse) {}
                })
    }
}
