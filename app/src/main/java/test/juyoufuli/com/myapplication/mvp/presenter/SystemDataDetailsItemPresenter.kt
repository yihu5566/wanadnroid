package test.juyoufuli.com.myapplication.mvp.presenter

import android.app.Application

import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.di.scope.FragmentScope
import com.jess.arms.integration.AppManager
import com.jess.arms.mvp.BasePresenter
import com.jess.arms.utils.RxLifecycleUtils

import javax.inject.Inject

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber
import me.jessyan.rxerrorhandler.handler.RetryWithDelay
import test.juyoufuli.com.myapplication.mvp.entity.ArticleBean
import test.juyoufuli.com.myapplication.mvp.entity.ArticleResponse
import test.juyoufuli.com.myapplication.mvp.model.contract.SystemDataDetailsContract
import test.juyoufuli.com.myapplication.mvp.model.contract.SystemDataDetailsItemContract
import test.juyoufuli.com.myapplication.mvp.ui.home.adapter.ArticleAdapter
import test.juyoufuli.com.myapplication.mvp.ui.searchview.adapter.SearchAdapter

/**
 * Author : ludf
 * Created Time : 2018-09-29  13:34
 * Description:
 */
@FragmentScope
class SystemDataDetailsItemPresenter @Inject
constructor(model: SystemDataDetailsItemContract.Model, rootView: SystemDataDetailsItemContract.View) : BasePresenter<SystemDataDetailsItemContract.Model, SystemDataDetailsItemContract.View>(model, rootView) {
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

                    override fun onNext(response: ArticleResponse) {
                        mRootView.refreshData(response)

                    }
                })
    }

    override fun onDestroy() {
        super.onDestroy()

    }
}
