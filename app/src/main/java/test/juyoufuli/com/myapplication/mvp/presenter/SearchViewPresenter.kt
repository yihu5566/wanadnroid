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
import test.juyoufuli.com.myapplication.mvp.entity.ArticleResponse
import test.juyoufuli.com.myapplication.mvp.model.contract.SearchContract
import test.juyoufuli.com.myapplication.mvp.ui.searchview.adapter.SearchAdapter

/**
 * Author : ludf
 * Created Time : 2018-09-30  15:58
 * Description:
 */
@ActivityScope
class SearchViewPresenter @Inject
constructor(model: SearchContract.Model, rootView: SearchContract.View) : BasePresenter<SearchContract.Model, SearchContract.View>(model, rootView) {
    @JvmField
    @Inject
    internal var mErrorHandler: RxErrorHandler? = null
    @JvmField
    @Inject
    internal var mAdapter: SearchAdapter? = null

    fun getSearchResult(page: Int, result: String) {
        mModel.getSearchResult(page, result)
                .subscribeOn(Schedulers.io())
                //                .retryWhen(new RetryWithDelay(3, 2))
                //                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(object : ErrorHandleSubscriber<ArticleResponse>(mErrorHandler!!) {

                    override fun onNext(response: ArticleResponse) {
                        LogUtils.debugInfo(page.toString() + "--------------url")
                        mRootView.refreshList(response)

                    }
                })

    }


}
