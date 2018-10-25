package test.juyoufuli.com.myapplication.mvp.presenter

import android.app.Application
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent

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
import test.juyoufuli.com.myapplication.app.net.HttpUtil
import test.juyoufuli.com.myapplication.mvp.entity.ArticleResponse
import test.juyoufuli.com.myapplication.mvp.entity.SystemBean
import test.juyoufuli.com.myapplication.mvp.entity.SystemDataRespons
import test.juyoufuli.com.myapplication.mvp.model.contract.SystemDataContract
import test.juyoufuli.com.myapplication.mvp.ui.tab.adapter.SystemDataAdapter

/**
 * Author : ludf
 * Created Time : 2018-09-29  13:34
 * Description:
 */
@FragmentScope
class SystemDataPresenter @Inject
constructor(model: SystemDataContract.Model, rootView: SystemDataContract.View) : BasePresenter<SystemDataContract.Model, SystemDataContract.View>(model, rootView) {
    @JvmField
    @Inject
    internal var mErrorHandler: RxErrorHandler? = null
    @JvmField
    @Inject
    internal var mSystemData: MutableList<SystemBean>? = null
    @JvmField
    @Inject
    internal var mAdapter: SystemDataAdapter? = null


    fun requestSystemDataList() {

        mModel.getSystemData()
                .subscribeOn(Schedulers.io())
                .retryWhen(RetryWithDelay(3, 2))
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(object : ErrorHandleSubscriber<SystemDataRespons>(mErrorHandler!!) {

                    override fun onNext(response: SystemDataRespons) {
                        mSystemData!!.addAll(response.data)
                        mAdapter!!.notifyDataSetChanged()
                    }
                })

        //        HttpUtil.executeHttpRequest(mModel.getSystemData(), mRootView, new ErrorHandleSubscriber<List<SystemBean>>(mErrorHandler) {
        //            @Override
        //            public void onNext(List<SystemBean> systemDataResponse) {
        //
        //            }
        //        });

    }
}
