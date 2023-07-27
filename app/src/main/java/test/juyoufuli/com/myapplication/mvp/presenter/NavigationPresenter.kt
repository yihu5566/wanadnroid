package test.juyoufuli.com.myapplication.mvp.presenter

import com.jess.arms.di.scope.FragmentScope
import com.jess.arms.mvp.BasePresenter
import com.jess.arms.utils.RxLifecycleUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber
import me.jessyan.rxerrorhandler.handler.RetryWithDelay
import test.juyoufuli.com.myapplication.mvp.entity.NavigationResponse
import test.juyoufuli.com.myapplication.mvp.contract.NavigationContract
import javax.inject.Inject

/**
 * Author : ludf
 * Created Time : 2018-09-30  13:40
 * Description:
 */
@FragmentScope
class NavigationPresenter @Inject
constructor(model: NavigationContract.Model, rootView: NavigationContract.View) : BasePresenter<NavigationContract.Model, NavigationContract.View>(model, rootView) {
    @JvmField
    @Inject
    internal var mErrorHandler: RxErrorHandler? = null


    fun getNavigation() {
        mModel.getNavigation()
                .subscribeOn(Schedulers.io())
                .retryWhen(RetryWithDelay(3, 2))
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle<NavigationResponse>(mRootView))
                .subscribe(object : ErrorHandleSubscriber<NavigationResponse>(mErrorHandler!!) {

                    override fun onNext(response: NavigationResponse) {
                        mRootView.refreshAdapterList(response)
                    }

                })
    }
}
