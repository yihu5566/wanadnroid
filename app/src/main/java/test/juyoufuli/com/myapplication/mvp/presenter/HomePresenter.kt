package test.juyoufuli.com.myapplication.mvp.presenter

import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.mvp.BasePresenter
import com.jess.arms.utils.RxLifecycleUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber
import me.jessyan.rxerrorhandler.handler.RetryWithDelay
import test.juyoufuli.com.myapplication.mvp.entity.LoginResponse
import test.juyoufuli.com.myapplication.mvp.model.contract.HomeContract
import javax.inject.Inject

/**
 * Author : ludf
 * Created Time : 2018-09-30  13:40
 * Description:
 */
@ActivityScope
class HomePresenter @Inject
constructor(model: HomeContract.Model, rootView: HomeContract.View) : BasePresenter<HomeContract.Model, HomeContract.View>(model, rootView) {
    @JvmField
    @Inject
    internal var mErrorHandler: RxErrorHandler? = null


    fun LoginOut() {
        mModel.loginOut()
                .subscribeOn(Schedulers.io())
                .retryWhen(RetryWithDelay(3, 2))
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle<LoginResponse>(mRootView))
                .subscribe(object : ErrorHandleSubscriber<LoginResponse>(mErrorHandler!!) {

                    override fun onNext(response: LoginResponse) {
                        mRootView.loginOutSucceed()
                    }

                })
    }
}
