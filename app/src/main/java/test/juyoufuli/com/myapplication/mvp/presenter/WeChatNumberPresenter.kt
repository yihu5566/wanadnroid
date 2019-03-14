package test.juyoufuli.com.myapplication.mvp.presenter

import com.jess.arms.di.scope.FragmentScope
import com.jess.arms.mvp.BasePresenter
import com.jess.arms.utils.RxLifecycleUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber
import me.jessyan.rxerrorhandler.handler.RetryWithDelay
import test.juyoufuli.com.myapplication.mvp.contract.WeChatNumberContract
import test.juyoufuli.com.myapplication.mvp.entity.WeChatNumberResponse
import javax.inject.Inject

/**
 * @Author : dongfang
 * @Created Time : 2019-03-14  10:25
 * @Description:
 */
@FragmentScope
class WeChatNumberPresenter @Inject constructor(model: WeChatNumberContract.Model, view: WeChatNumberContract.View) : BasePresenter<WeChatNumberContract.Model, WeChatNumberContract.View>(model, view) {
    @JvmField
    @Inject
    internal var mErrorHandler: RxErrorHandler? = null


    fun requestSystemDataList() {
        mModel.getWeChatData()
                .subscribeOn(Schedulers.io())
                .retryWhen(RetryWithDelay(3, 2))
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle<WeChatNumberResponse>(mRootView))
                .subscribe(object : ErrorHandleSubscriber<WeChatNumberResponse>(mErrorHandler!!) {

                    override fun onNext(response: WeChatNumberResponse) {
                        if (response != null) {
                            mRootView.refreshData(response)

                        }
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