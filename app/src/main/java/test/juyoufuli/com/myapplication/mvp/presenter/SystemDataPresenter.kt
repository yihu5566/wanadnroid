package test.juyoufuli.com.myapplication.mvp.presenter

import com.jess.arms.di.scope.FragmentScope
import com.jess.arms.mvp.BasePresenter
import com.jess.arms.utils.RxLifecycleUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber
import me.jessyan.rxerrorhandler.handler.RetryWithDelay
import test.juyoufuli.com.myapplication.mvp.entity.SystemBean
import test.juyoufuli.com.myapplication.mvp.entity.SystemDataRespons
import test.juyoufuli.com.myapplication.mvp.contract.SystemDataContract
import test.juyoufuli.com.myapplication.mvp.ui.tab.adapter.SystemDataAdapter
import javax.inject.Inject

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
                .compose(RxLifecycleUtils.bindToLifecycle<SystemDataRespons>(mRootView))
                .subscribe(object : ErrorHandleSubscriber<SystemDataRespons>(mErrorHandler!!) {

                    override fun onNext(response: SystemDataRespons) {
                        mRootView.refreshData(response)
                        mSystemData!!.addAll(response.data)
                        mAdapter!!.notifyDataSetChanged()
                    }
                })


    }
}
