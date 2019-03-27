package test.juyoufuli.com.myapplication.mvp.presenter

import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.mvp.BasePresenter
import com.jess.arms.utils.LogUtils
import com.jess.arms.utils.RxLifecycleUtils

import javax.inject.Inject

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber
import me.jessyan.rxerrorhandler.handler.RetryWithDelay
import test.juyoufuli.com.myapplication.mvp.entity.ArticleResponse
import test.juyoufuli.com.myapplication.mvp.entity.HotWordResponse
import test.juyoufuli.com.myapplication.mvp.model.contract.SearchContract
import test.juyoufuli.com.myapplication.mvp.model.contract.WeChatSearchContract
import test.juyoufuli.com.myapplication.mvp.ui.searchview.adapter.SearchAdapter

/**
 * Author : ludf
 * Created Time : 2018-09-30  15:58
 * Description:
 */
@ActivityScope
class WeChatSearchViewPresenter @Inject
constructor(model: WeChatSearchContract.Model, rootView: WeChatSearchContract.View) : BasePresenter<WeChatSearchContract.Model, WeChatSearchContract.View>(model, rootView) {
    @JvmField
    @Inject
    internal var mErrorHandler: RxErrorHandler? = null


    fun getSearchResult(id: String, page: Int, result: String) {
        mModel.getSearchResult(id, page, result)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle<ArticleResponse>(mRootView))
                .subscribe(object : ErrorHandleSubscriber<ArticleResponse>(mErrorHandler!!) {

                    override fun onNext(response: ArticleResponse) {
                        LogUtils.debugInfo(page.toString() + "--------------url")
                        mRootView.refreshList(response)

                    }
                })

    }


}
