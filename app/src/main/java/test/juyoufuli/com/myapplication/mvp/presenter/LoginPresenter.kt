package test.juyoufuli.com.myapplication.mvp.presenter

import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.mvp.BasePresenter
import com.jess.arms.utils.ArmsUtils
import com.jess.arms.utils.LogUtils
import com.jess.arms.utils.RxLifecycleUtils

import javax.inject.Inject

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber
import me.jessyan.rxerrorhandler.handler.RetryWithDelay
import test.juyoufuli.com.myapplication.mvp.entity.ArticleResponse
import test.juyoufuli.com.myapplication.mvp.entity.BaseResponse
import test.juyoufuli.com.myapplication.mvp.entity.LoginResponse
import test.juyoufuli.com.myapplication.mvp.model.contract.LoginContract

/**
 * Author : dongfang
 * Created Time : 2018-10-19  14:11
 * Description:
 */
@ActivityScope
class LoginPresenter @Inject
constructor(model: LoginContract.Model, rootView: LoginContract.View) : BasePresenter<LoginContract.Model, LoginContract.View>(model, rootView) {
    @JvmField
    @Inject
    internal var mErrorHandler: RxErrorHandler? = null

    fun login(name: String, password: String) {
        mModel.login(name, password)
                .subscribeOn(Schedulers.io())
                .retryWhen(RetryWithDelay(3, 2))
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(object : ErrorHandleSubscriber<LoginResponse>(mErrorHandler!!) {

                    override fun onNext(response: LoginResponse) {
                        LogUtils.debugInfo("$password--------------url")
                        mRootView.loginSucceed(response)
                    }

                    override fun onError(t: Throwable) {
                        super.onError(t)
                    }
                })

    }


    fun register(name: String, password: String, repassword: String) {
        mModel.register(name, password, repassword)
                .subscribeOn(Schedulers.io())
                .retryWhen(RetryWithDelay(3, 2))
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(object : ErrorHandleSubscriber<LoginResponse>(mErrorHandler!!) {

                    override fun onNext(response: LoginResponse) {
                        LogUtils.debugInfo("$password--------------url")
                        ArmsUtils.makeText(mRootView.getActivity(), response.errorMsg)
                    }

                    override fun onError(t: Throwable) {
                        super.onError(t)
                    }
                })

    }
}
